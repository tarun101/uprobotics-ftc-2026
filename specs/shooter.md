# Spec: Shooter
Status: draft

## Goal
Launch POLLEN and NECTAR into the upward CELL. Same class in auto and teleop.

## Behavior
States: IDLE, SPIN_UP, FEED, RECOVER.
- `fire(n)`: request n shots. IDLE with n > 0 goes to SPIN_UP.
- SPIN_UP: set flywheel velocity from the distance table. Go to FEED when within tolerance or after 1.0 s timeout.
- FEED: open gate or run feeder for one ball. Count the shot. Go to RECOVER.
- RECOVER: close gate. If shots remain, go to SPIN_UP; else IDLE.
- `isBusy()` is true in every state except IDLE.
- `hold()`: idle speed between cycles, to cut spin-up time.
- `cancel()`: stop feeding, go to IDLE.
- Ball type (POLLEN or NECTAR) selects a separate velocity table. Detect with a colour sensor if fitted.
- Report each shot to `HiveTracker.shotFired(type)`.

## Distance table (fill from testing)
| Distance to CELL (in) | POLLEN ticks/s | NECTAR ticks/s |
|---|---|---|
| 36 | TBD | TBD |
| 60 | TBD | TBD |
| 84 | TBD | TBD |
Interpolate linearly between rows. JUnit-test the interpolation.

## Rules it must respect
G417: never aim at CELL sides or the opponent HIVE. G409: never catch falling balls.

## Acceptance
- 9/10 POLLEN land in the CELL from SHOOT.
- 4-ball volley in under 2.0 s once at speed.

## Test log
| Date | Result | Change |
