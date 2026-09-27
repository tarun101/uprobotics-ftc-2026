package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Every mechanism is a non-blocking state machine with this shape.
 * The same class runs in autonomous and TeleOp. See AGENTS.md.
 */
public interface Subsystem {
    void init(HardwareMap hardwareMap);

    /** Called once per loop, after follower.update(). Never blocks. */
    void update();

    /** True while the subsystem is doing something autos should wait for. */
    boolean isBusy();
}
