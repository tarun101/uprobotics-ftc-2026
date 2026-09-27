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

/** Auto T0: leave the wall and park in the LOADING ZONE. See specs/auto-T0-leave-park.md. */
@Autonomous(name = "T0 Leave + Park", group = "UPR")
public class AutoLeavePark extends OpMode {
    private enum Step { WAIT_DELAY, DRIVE_TO_PARK, DONE }

    private static final double STEP_TIMEOUT_S = 8.0;

    private Follower follower;
    private FieldPoses poses;
    private Alliance alliance = Alliance.RED;
    private int delaySeconds = 0;
    private boolean lastUp, lastDown;

    private Step step = Step.WAIT_DELAY;
    private final ElapsedTime stepTimer = new ElapsedTime();

    @Override
    public void init() {
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
        setStep(Step.WAIT_DELAY);
    }

    @Override
    public void loop() {
        if (follower == null) return;
        follower.update();

        switch (step) {
            case WAIT_DELAY:
                if (stepTimer.seconds() >= delaySeconds) {
                    follower.follow(parkPath());
                    setStep(Step.DRIVE_TO_PARK);
                }
                break;
            case DRIVE_TO_PARK:
                if (!follower.isBusy() || stepTimer.seconds() > STEP_TIMEOUT_S) {
                    follower.hold(poses.loadingZonePark());
                    setStep(Step.DONE);
                }
                break;
            case DONE:
                break;
        }

        PoseStore.save(follower.pose(), alliance);
        telemetry.addData("Step", step);
        telemetry.addData("Pose", follower.pose());
    }

    private Path parkPath() {
        return Paths.line(poses.startNear(), poses.loadingZonePark())
                .linear(poses.startNear(), poses.loadingZonePark());
    }

    private void setStep(Step next) {
        step = next;
        stepTimer.reset();
    }
}
