# Spec: Auto T1 - Preload tip
Status: draft

## Goal
Tip the HIVE with the 4 preloaded POLLEN, then park. Target 28 points (3 + 20 + 5).

## Behavior
1. Spin up the shooter while driving `START -> SHOOT`.
2. Read `hive.upCell()`. Aim at it.
3. Fire 3 POLLEN. The 3 staged NECTAR plus 3 POLLEN should tip it.
4. Wait for `hive.safeToShoot()` (about 3.5 s tip), aim at the new up CELL.
5. Fire the 4th POLLEN.
6. Follow `SHOOT -> LOADING_ZONE_PARK`.

Role SCORE_SECOND: if the tracker says the partner already tipped it, fire all 4 into the new CELL.
Partner with 4 more POLLEN plus ours makes the second tip likely.

## Rules it must respect
G402, G417 (no shots at a tipping HIVE's CELL sides).

## Acceptance
- 8/10 runs tip the HIVE and park by 30 s.
- Both roles tested with a partner robot or a human stand-in.

## Test log
| Date | Result | Change |
