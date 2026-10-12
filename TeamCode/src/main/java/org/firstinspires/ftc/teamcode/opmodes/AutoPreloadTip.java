package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.api.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.field.Alliance;
import org.firstinspires.ftc.teamcode.field.FieldPoses;
import org.firstinspires.ftc.teamcode.field.PoseStore;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

/**
 * Auto T1: shoot 3 preloads to tip the HIVE, wait for the tip, shoot the 4th, park.
 * See specs/auto-T1-preload-tip.md. First version waits a fixed time for the tip;
 * the HIVE tracker (specs/hive-tracker.md) replaces that wait later.
 */
@Autonomous(name = "T1 Preload Tip", group = "UPR")
public class AutoPreloadTip extends OpMode {
    private enum Step { WAIT_DELAY, DRIVE_TO_SHOOT, FIRE_THREE, WAIT_FOR_TIP, FIRE_LAST, DRIVE_TO_PARK, DONE }

    private static final double DRIVE_TIMEOUT_S = 6.0;
    private static final double FIRE_TIMEOUT_S = 4.0;
    private static final double TIP_WAIT_S = 3.5;
    private static final double PARK_AT_S = 26.0;

    private Follower follower;
    private final Shooter shooter = new Shooter();
    private FieldPoses poses;
    private Alliance alliance = Alliance.RED;
    private int delaySeconds = 0;
    private boolean lastUp, lastDown, fired;

    private Step step = Step.WAIT_DELAY;
    private final ElapsedTime stepTimer = new ElapsedTime();
    private final ElapsedTime matchTimer = new ElapsedTime();

    @Override
    public void init() {
        shooter.init(hardwareMap);
        follower = Constants.create(hardwareMap);
    }

    @Override
    public void init_loop() {
        if (gamepad1.x) alliance = Alliance.RED;
        if (gamepad1.b) alliance = Alliance.BLUE;
        if (gamepad1.dpad_up && !lastUp) delaySeconds = Math.min(10, delaySeconds + 1);
        if (gamepad1.dpad_down && !lastDown) delaySeconds = Math.max(0, delaySeconds - 1);
        lastUp = gamepad1.dpad_up;
        lastDown = gamepad1.dpad_down;

        if (follower == null) telemetry.addLine("Set up pedro/Constants.create() first.");
        telemetry.addData("Alliance (X red / B blue)", alliance);
        telemetry.addData("Delay s (dpad up/down)", delaySeconds);
    }

    @Override
    public void start() {
        if (follower == null) return;
        poses = new FieldPoses(alliance);
        follower.setPose(poses.startNear());
        matchTimer.reset();
        setStep(Step.WAIT_DELAY);
    }

    @Override
    public void loop() {
        if (follower == null) return;
        follower.update();
        shooter.update();

        // Safety net: whatever is happening, go park at 26 s.
        if (matchTimer.seconds() > PARK_AT_S && step != Step.DRIVE_TO_PARK && step != Step.DONE) {
            shooter.cancel();
            follower.follow(parkPath());
            setStep(Step.DRIVE_TO_PARK);
        }

        switch (step) {
            case WAIT_DELAY:
                if (stepTimer.seconds() >= delaySeconds) {
                    shooter.setHoldSpeed(true);          // spin up while driving
                    follower.follow(shootPath());
                    setStep(Step.DRIVE_TO_SHOOT);
                }
                break;
            case DRIVE_TO_SHOOT:
                if (!follower.isBusy() || stepTimer.seconds() > DRIVE_TIMEOUT_S) {
                    follower.hold(poses.shoot());
                    setStep(Step.FIRE_THREE);
                }
                break;
            case FIRE_THREE:
                if (!fired) { shooter.fire(3); fired = true; }
                if ((fired && !shooter.isBusy()) || stepTimer.seconds() > FIRE_TIMEOUT_S) setStep(Step.WAIT_FOR_TIP);
                break;
            case WAIT_FOR_TIP:
                if (stepTimer.seconds() > TIP_WAIT_S) setStep(Step.FIRE_LAST);
                break;
            case FIRE_LAST:
                if (!fired) { shooter.fire(1); fired = true; }
                if ((fired && !shooter.isBusy()) || stepTimer.seconds() > FIRE_TIMEOUT_S) {
                    shooter.setHoldSpeed(false);
                    follower.follow(parkPath());
                    setStep(Step.DRIVE_TO_PARK);
                }
                break;
            case DRIVE_TO_PARK:
                if (!follower.isBusy() || stepTimer.seconds() > DRIVE_TIMEOUT_S) {
                    follower.hold(poses.loadingZonePark());
                    setStep(Step.DONE);
                }
                break;
            case DONE:
                break;
        }

        PoseStore.save(follower.pose(), alliance);
        telemetry.addData("Step", step);
        telemetry.addData("Shooter", shooter.state());
        telemetry.addData("Shots", shooter.shotsFired());
        telemetry.addData("Time", "%.1f", matchTimer.seconds());
        telemetry.addData("Pose", follower.pose());
    }

    private Path shootPath() {
        return Paths.line(poses.startNear(), poses.shoot()).linear(poses.startNear(), poses.shoot());
    }

    private Path parkPath() {
        return Paths.line(follower.pose(), poses.loadingZonePark()).linear(follower.pose(), poses.loadingZonePark());
    }

    private void setStep(Step next) {
        step = next;
        fired = false;
        stepTimer.reset();
    }
}
