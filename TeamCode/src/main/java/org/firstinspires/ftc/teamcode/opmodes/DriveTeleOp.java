package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.field.Alliance;
import org.firstinspires.ftc.teamcode.field.PoseStore;
import org.firstinspires.ftc.teamcode.pedro.Constants;

/** Field-centric drive that holds position when the sticks are idle. See specs/teleop.md. */
@TeleOp(name = "Drive", group = "UPR")
public class DriveTeleOp extends OpMode {
    private static final double SLOW_SCALE = 0.4;

    private Follower follower;
    private Alliance alliance;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        alliance = PoseStore.alliance();
        if (follower == null) return;
        Pose saved = PoseStore.pose();
        follower.setPose(saved != null ? saved : Pose.zero());
    }

    @Override
    public void init_loop() {
        if (follower == null) telemetry.addLine("Set up pedro/Constants.create() first.");
        telemetry.addData("Alliance", alliance);
        telemetry.addData("Start pose", PoseStore.pose() != null ? "from auto" : "zero (no auto run)");
    }

    @Override
    public void loop() {
        if (follower == null) return;
        follower.update();

        double scale = gamepad1.left_trigger > 0.5 ? SLOW_SCALE : 1.0;
        double forward = -gamepad1.left_stick_y * scale;
        double strafe = -gamepad1.left_stick_x * scale;
        double turn = -gamepad1.right_stick_x * scale;

        // TODO(specs/teleop.md): verify offsets so "stick forward" points away from our driver wall.
        double offset = alliance == Alliance.RED ? 0.0 : Math.PI;
        DrivePowers powers = ManualDrive.fieldCentric(forward, strafe, turn, follower.pose().heading(), offset);
        ManualDrive.driveOrHold(follower, powers);

        if (gamepad1.back) follower.setHeading(0);

        telemetry.addData("Pose", follower.pose());
        telemetry.addData("Mode", follower.mode());
    }
}
