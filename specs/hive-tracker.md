# Spec: HIVE tracker
Status: draft

## Goal
Know which of our CELLS faces up, and when it is about to tip.

## Behavior
- State: UP_AUDIENCE, UP_FAR, TIPPING, UNKNOWN.
- Starting state: the CELL that points away from its FLOWER faces up (10.3.1). Confirm at init with AprilTags.
- AprilTag check: our CELLS carry red 30-33 (far) and 34-37 (audience), blue 38-41 (audience) and 42-45 (far).
  The upward CELL's tags sit higher; compare tag height or pitch between the two clusters.
- Shot counting: estimate fill from shots fired since the last tip, weighted (NECTAR ~1.6x POLLEN).
- On a detected flip: go TIPPING for 3.5 s, then to the new UP state. Reset the count.
- Expose `upCell()`, `aimTarget()`, `safeToShoot()` (false while TIPPING or UNKNOWN), `tipCount()`.
- Teleop: rumble gamepad 1 on each tip. Show state and tip count on telemetry.
- Tag detections also feed pose relocalization (see teleop.md).

## Rules it must respect
10.5.1: do not launch at the downward CELL while tipping.

## Acceptance
- Correct up CELL at init in 10/10 setups, both alliances.
- Detects 9/10 tips within 1 s of the damper landing.

## Test log
| Date | Result | Change |
