# BIOBUZZ facts for code

Source: Competition Manual TU02 (Sep 24, 2026) unless marked. Recheck after each Thursday Team Update.
Official Q&A opens Sep 28, 2026. Manual: https://ftc-resources.firstinspires.org/ftc/game/cm-html/BIOBUZZ%20Competition%20Manual%20-%20TU02.htm

## Match
- AUTO 30 s, 8 s transition, TELEOP 2:00 (10.4).
- Tips finished before TELEOP starts count as AUTO (10.5.B).

## Points (Table 10-2)
| Action | AUTO | TELEOP |
|---|---|---|
| LEAVE: not touching the perimeter wall | 3 | - |
| PARK: at least partly in own LOADING ZONE | 5 | 5 (match end) |
| HIVE TIP | 20 | 20 |
| Ball left in upward CELL at end | - | 2 each |
| FLOWER bottom NECTAR bonus | - | 5 |
| Ball in an owned FLOWER | - | 2 each |
| Ball in own-colour GARDEN | - | 1 each |

RP at normal events (Table 10-3): SWARM 16 LEAVE+PARK points; POLLINATOR 1 at 4 tips; POLLINATOR 2 at 7 tips; win 3.
SWARM example: both robots LEAVE (6) and both AUTO PARK (10) = 16.
Tiebreakers after RP and match points: average tips, then average AUTO points (13.6.3).

## Field
- 144 x 144 in, 36 tiles of 24 in (9.2). Pedro uses 141.5 in.
- Red side = columns A-C, blue side = D-F (G304, G402).
- HIVE Structure at centre. Each alliance HIVE has two CELLS about 18.8 in apart (9.6).
- CELL opening about 20 x 14 in, 12 in deep. HIVE bottom 30.6 in above tiles (TU01).
- Only one CELL faces up. It flips after each tip.
- AprilTags (36h11, 3.25 in) under each CELL, facing down (9.9):
  red far side 30-33, red audience side 34-37, blue audience side 38-41, blue far side 42-45.
- 4 FLOWERS on the walls. Top opening 4 in wide, 21.5 in high. POLLEN comes out the bottom (9.7).
- LOADING ZONE about 23 x 11 in, next to own ALLIANCE AREA (9.3).
- GARDEN about 23 x 2 in, in opposite corners (9.3).

## Setup (10.3.1, G304)
- Each robot starts touching 4 POLLEN, touching the wall, on its own side.
- Not in the LOADING ZONE. Not touching a FLOWER. Motionless after init.
- 4 POLLEN in each FLOWER. 4 POLLEN in each GARDEN, in a line from the corner nearest the ALLIANCE AREA.
- 3 NECTAR of each colour start in that colour's upward CELL. 5 more wait in each ALLIANCE AREA.

## Tipping (field walkthrough video and Event Field Setup Guide tests)
- About 8 POLLEN tip a CELL, or 5 NECTAR, or 3 NECTAR + 3 POLLEN.
- Calibration: 3 NECTAR + 2 POLLEN must not tip; 3 NECTAR + 3 POLLEN must tip.
- So 3 preloaded POLLEN should tip the first CELL.
- A tip takes about 3-4 s (FUN Robotics, opinion). Launching at a tipping HIVE can stop the tip (10.5.1).

## Rules code must respect
- G401: no driver input during AUTO.
- G402: stay on own half in AUTO.
- G409: do not catch balls falling from a tipping HIVE. No waiting under the HIVE.
- G410: NECTAR into FLOWERS only with 60 s or less left.
- G411: no hoarding balls.
- G417: only launching into the upward CELL may move the HIVE. No ramming, no shots at CELL sides.
- G418: add balls only through the FLOWER top; remove only POLLEN, only from the bottom.
- Possession limit of 4 balls: stated by FUN Robotics; confirm the rule number before relying on it.
