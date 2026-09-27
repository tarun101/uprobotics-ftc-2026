# Spec: Auto T0 - Leave and park
Status: built

## Goal
Guaranteed 8 points and our half of the SWARM RP. Fallback when anything breaks.

## Behavior
1. Wait the start delay.
2. `Paths.line(START_NEAR, LOADING_ZONE_PARK)` with `.constant(heading)`. Leaving the wall scores LEAVE.
3. Hold in the LOADING ZONE.

## Acceptance
- 10/10 runs: off the wall, then partly inside the LOADING ZONE by 30 s.
- Works with a 10 s delay.

## Test log
| Date | Result | Change |
