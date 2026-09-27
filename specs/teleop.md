# Spec: TeleOp
Status: draft

## Goal
Fast field-centric driving, with the robot doing the aiming.

## Start
- Read the pose saved by auto from `PoseStore`. If none, use a known start pose from a menu.
- Alliance comes from the auto or a menu. Field-centric forward points away from our driver wall.

## Driving (gamepad 1)
- Left stick: translate, field-centric (`ManualDrive.fieldCentric`).
- Right stick X: turn. When aim mode is on, `ManualDrive.headingLock` turns toward `hive.aimTarget()`.
- `ManualDrive.driveOrHold` so the robot holds position against pushes when sticks are idle.
- Left trigger: slow mode 40%.
- Back: reset heading (only if the pose is lost).

## Assist macros (gamepad 1; any stick input cancels)
- A: drive to SHOOT.
- X: drive to the LOADING ZONE (NECTAR pickup, final park).
- Y: drive to the nearest FLOWER top (only enabled with 60 s or less left).
Macros build a path from `follower.pose()` at press time.

## Mechanisms (gamepad 2)
- Right trigger: intake. Left trigger: reverse (spit out one ball).
- Right bumper: aim mode on/off. A: fire all held. B: fire one.
- Shooter idles at speed between cycles.

## Awareness
- Rumble on each tip, at 60 s left (FLOWERS and NECTAR open), and at 12 s left (park).
- Telemetry: up CELL, tip count, balls held, match time, pose.
- Relocalize from HIVE AprilTags when the robot is still and a tag is clear. Reject jumps over 6 in.

## Rules it must respect
G410 (no NECTAR into FLOWERS early), G417, G418, G409, G419-G420 (defense without damage).

## Acceptance
- Driver completes a shoot cycle from mid-field in under 6 s.
- Aim mode hits 8/10 from SHOOT while the robot is being pushed lightly.
- After 3 hard hits, pose error stays under 3 in with relocalization.

## Test log
| Date | Result | Change |
