package org.firstinspires.ftc.teamcode.field;

import com.pedropathing.math.Pose;

/** Carries the robot's pose and alliance from autonomous into TeleOp. */
public final class PoseStore {
    private static Pose pose = null;
    private static Alliance alliance = Alliance.RED;

    private PoseStore() {}

    public static void save(Pose p, Alliance a) {
        pose = p;
        alliance = a;
    }

    /** Last pose saved by an OpMode, or null if none this power cycle. */
    public static Pose pose() { return pose; }

    public static Alliance alliance() { return alliance; }
}
