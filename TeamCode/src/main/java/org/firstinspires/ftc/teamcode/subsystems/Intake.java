package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/** Intake rollers. See specs/intake.md. */
public class Intake implements Subsystem {
    public enum State { STOPPED, INTAKING, SPITTING }

    public static final double INTAKE_POWER = 1.0;
    public static final double SPIT_POWER = -0.6;
    private static final double TRIGGER_THRESHOLD = 0.5;

    private DcMotor motor;
    private State state = State.STOPPED;

    @Override
    public void init(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotor.class, "intake");
        // If the rollers spin the wrong way, change FORWARD to REVERSE.
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /** Pick the state from the operator's triggers. Spitting wins if both are held. */
    public void setTriggers(double intakeTrigger, double spitTrigger) {
        state = chooseState(intakeTrigger, spitTrigger);
    }

    static State chooseState(double intakeTrigger, double spitTrigger) {
        if (spitTrigger > TRIGGER_THRESHOLD) return State.SPITTING;
        if (intakeTrigger > TRIGGER_THRESHOLD) return State.INTAKING;
        return State.STOPPED;
    }

    @Override
    public void update() {
        switch (state) {
            case INTAKING:
                motor.setPower(INTAKE_POWER);
                break;
            case SPITTING:
                motor.setPower(SPIT_POWER);
                break;
            default:
                motor.setPower(0);
                break;
        }
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    public State state() {
        return state;
    }
}
