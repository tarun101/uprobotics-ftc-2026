# Spec: Auto T3 - Vision pickup
Status: draft (build after T2 is reliable)

## Goal
Collect balls spilled by a tip, using the camera, for extra tips.

## Behavior
1. After each volley, look for POLLEN (yellow) and our NECTAR (red or blue) blobs on our half.
2. Convert the best blob to a field point using `follower.pose()` and camera geometry.
3. Build a path at runtime: `Paths.line(follower.pose(), target).facingPoint(target)`.
4. Intake on arrival. Return to SHOOT and fire.
5. Ignore blobs on the opponent half, under the HIVE, and opponent NECTAR.
6. Same 26 s park guard.

## Rules it must respect
G402 (stay on our half), G409 (do not wait under the HIVE), G411 (no hoarding).

## Acceptance
- 5/10 runs collect and score at least 3 extra balls.
- Never targets a ball on the opponent half.

## Test log
| Date | Result | Change |
