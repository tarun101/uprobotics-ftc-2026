# Spec: Auto framework
Status: draft

## Goal
One OpMode runs every auto tier, for both alliances and start positions.

## Init menu (gamepad 1, before START)
- Alliance: red / blue (X / B).
- Start: NEAR / FAR (dpad left / right).
- Tier: T0-T3 (dpad up / down).
- Role: TIP_FIRST / SCORE_SECOND (Y). Agree with the partner in queue.
- Start delay: 0-10 s in 1 s steps (bumpers).
- Telemetry shows every choice and the HIVE tracker's up CELL.

## Structure
- `enum Step`, a `switch` in `update()`, and `setStep(step)` that resets a step timer.
- Loop: `follower.update()`, `shooter.update()`, `intake.update()`, `hive.update()`, then `update()`.
- Drive steps: `follower.follow(pathX())`, advance when `!follower.isBusy()` or the step timeout ends.
- Action steps: start the action once (a `started` flag), advance when `!subsystem.isBusy()` or timeout.
- Start intake or spin-up during the drive, not after it.
- Hold position while shooting: `follower.hold(SHOOT)`.
- Park guard: at 26.0 s, cancel everything and follow the park path.
- At the end, save `follower.pose()` to a static `PoseStore` for teleop.

## Rules it must respect
G401 no input after START. G402 stay on our half. G304 legal start.

## Acceptance
- Every tier runs for red and blue, NEAR and FAR, from the same code.
- A stuck path (robot blocked) never stops the park at 26 s.

## Test log
| Date | Result | Change |
