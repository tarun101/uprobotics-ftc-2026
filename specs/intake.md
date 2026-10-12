# Spec: Intake
Status: built (example; use this as your first spec)
Owner: <your name>

## Goal
Pick up balls when the operator holds a trigger. Spit one out when they hold the other trigger.

## Hardware
- One motor named `intake` in the robot configuration (Control Hub motor port 0).
- Positive power pulls balls in. If it spins the wrong way, reverse it in code.

## Behavior
States: STOPPED, INTAKING, SPITTING.
- Gamepad 2 right trigger held (more than halfway): INTAKING, motor power +1.0.
- Gamepad 2 left trigger held (more than halfway): SPITTING, motor power -0.6.
- Both held: SPITTING wins.
- Neither held: STOPPED, power 0.
- Runs in the `Drive` TeleOp.
- Show the state on the Driver Hub screen.

## Rules it must respect
- Never hold more than 4 balls at once (possession limit; confirm the rule number).

## Acceptance
- Holding right trigger picks up 4 balls in under 3 seconds, 4 out of 5 tries.
- Left trigger spits out one ball at a time.
- Letting go stops the motor right away.

## Out of scope
- Counting balls. Colour sensing. Use in autonomous.

## Test log
| Date | Result | Change |
|---|---|---|
