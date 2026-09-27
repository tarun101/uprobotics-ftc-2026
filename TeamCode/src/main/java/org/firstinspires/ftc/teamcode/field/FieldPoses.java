package org.firstinspires.ftc.teamcode.field;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

/**
 * Every field pose, written once for RED. BLUE is mirrored by the PoseFactory.
 * All numbers are placeholders until measured. See specs/field-poses.md.
 */
public class FieldPoses {
    /** Pedro's field is 141.5 in square; this is its centre line. */
    public static final double FIELD_CENTER = 70.75;

    private final PoseFactory f;

    public FieldPoses(Alliance alliance) {
        PoseFactory red = PoseFactory.degrees();
        // TODO(specs/field-poses.md): confirm mirrorX vs mirrorY on the visualizer and a real field.
        f = alliance == Alliance.RED ? red : red.mirrorX(FIELD_CENTER);
    }

    // TODO: measure every pose below (inches, degrees). Placeholders only.
    public Pose startNear() { return f.of(9, 60, 0); }
    public Pose startFar() { return f.of(9, 84, 0); }
    public Pose shoot() { return f.of(40, 70, 0); }
    public Pose loadingZonePark() { return f.of(12, 30, 0); }
    public Pose gardenEntry() { return f.of(10, 10, 0); }
    public Pose gardenExit() { return f.of(30, 10, 0); }
}
