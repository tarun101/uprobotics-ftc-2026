# Spec: Field poses
Status: draft

## Goal
One table of red poses. Blue is mirrored in code. Every auto and teleop macro uses these names.

## Method
1. Open the official field CAD (Playing Field Resources page) or measure a practice field.
2. Pick the Pedro origin and axes. Confirm with the Pedro visualizer and a localization test.
3. Record poses in inches and degrees. Heading = direction the robot's front faces.
4. Check mirroring: run the red start pose through the blue PoseFactory; confirm on a real field.

## Poses (red; TBD = measure)
| Name | x | y | heading | Notes |
|---|---|---|---|---|
| START_NEAR | TBD | TBD | TBD | Touching wall, near LOADING ZONE, not in it |
| START_FAR | TBD | TBD | TBD | Second start option |
| SHOOT | TBD | TBD | TBD | One spot that can hit both red CELLS |
| AIM_CELL_AUDIENCE | - | - | TBD | Heading or turret angle for the audience-side CELL |
| AIM_CELL_FAR | - | - | TBD | Heading or turret angle for the far-side CELL |
| GARDEN_ENTRY | TBD | TBD | TBD | Start of the 4-POLLEN line |
| GARDEN_EXIT | TBD | TBD | TBD | End of the line |
| FLOWER_A_BOTTOM | TBD | TBD | TBD | POLLEN retrieval, our half |
| LOADING_ZONE_PARK | TBD | TBD | TBD | Partly inside the zone |
| CELL_AUDIENCE_TARGET | TBD | TBD | - | 3D target point for aiming |
| CELL_FAR_TARGET | TBD | TBD | - | 3D target point for aiming |

## Rules it must respect
G304 start rules. G402: all AUTO poses on our half.

## Acceptance
- Robot placed at each start pose reads within 1 in and 2 degrees after init.
- Mirrored blue poses match a real blue setup within 1 in.

## Test log
| Date | Result | Change |
