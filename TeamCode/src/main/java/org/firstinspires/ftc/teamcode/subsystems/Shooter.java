package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Flywheel shooter with a gate servo. See specs/shooter.md.
 * First version: one fixed speed. The distance table comes later.
 */
public class Shooter implements Subsystem {
    public enum State { IDLE, SPIN_UP, FEED, RECOVER }

    // TODO(specs/shooter.md): tune these on the robot.
    public static double TARGET_VELOCITY = 1800;   // encoder ticks per second
    public static double VELOCITY_TOLERANCE = 60;
    public static double SPIN_UP_TIMEOUT_S = 1.0;
    public static double FEED_TIME_S = 0.25;
    public static double RECOVER_TIME_S = 0.25;
    public static double GATE_OPEN = 0.6;
    public static double GATE_CLOSED = 0.2;

    private DcMotorEx flywheel;
    private Servo gate;
    private State state = State.IDLE;
    private int shotsLeft = 0;
    private int shotsFired = 0;
    private boolean holdSpeed = false;
    private final ElapsedTime timer = new ElapsedTime();

    @Override
    public void init(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotorEx.class, "shooter");
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setPosition(GATE_CLOSED);
    }

    /** Ask for n shots. Ignored if already shooting. */
    public void fire(int n) {
        if (state != State.IDLE || n <= 0) return;
        shotsLeft = n;
        setState(State.SPIN_UP);
    }

    /** Keep the wheel at speed between volleys, so the next one starts faster. */
    public void setHoldSpeed(boolean hold) {
        holdSpeed = hold;
    }

    public void cancel() {
        shotsLeft = 0;
        setState(State.IDLE);
    }

    @Override
    public void update() {
        switch (state) {
            case IDLE:
                gate.setPosition(GATE_CLOSED);
                flywheel.setVelocity(holdSpeed ? TARGET_VELOCITY : 0);
                break;
            case SPIN_UP:
                gate.setPosition(GATE_CLOSED);
                flywheel.setVelocity(TARGET_VELOCITY);
                if (atSpeed() || timer.seconds() > SPIN_UP_TIMEOUT_S) setState(State.FEED);
                break;
            case FEED:
                gate.setPosition(GATE_OPEN);
                if (timer.seconds() > FEED_TIME_S) {
                    shotsLeft--;
                    shotsFired++;
                    setState(State.RECOVER);
                }
                break;
            case RECOVER:
                gate.setPosition(GATE_CLOSED);
                if (timer.seconds() > RECOVER_TIME_S) setState(shotsLeft > 0 ? State.SPIN_UP : State.IDLE);
                break;
        }
    }

    @Override
    public boolean isBusy() {
        return state != State.IDLE;
    }

    public boolean atSpeed() {
        return Math.abs(flywheel.getVelocity() - TARGET_VELOCITY) < VELOCITY_TOLERANCE;
    }

    public State state() { return state; }

    public int shotsFired() { return shotsFired; }

    private void setState(State next) {
        state = next;
        timer.reset();
    }
}
