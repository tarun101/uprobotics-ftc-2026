# Spec: Auto T2 - Garden run
Status: draft

## Goal
Add the 4 GARDEN POLLEN for a second tip. Only if the GARDEN is on our half; check the field CAD.

## Behavior
1. Run T1 steps 1-5.
2. Follow `SHOOT -> GARDEN_ENTRY`, curve with `.tangent()` or `.constant()`. Start the intake on the way.
3. Follow `GARDEN_ENTRY -> GARDEN_EXIT` slowly along the line until 4 are held or the step timeout ends.
4. Return to SHOOT, spinning up on the way. Wait for `safeToShoot()`, fire all.
5. Park. Skip steps 2-4 if the clock passes 20 s.

Alternative source: POLLEN from a FLOWER bottom on our half (`FLOWER_A_BOTTOM`), removing only POLLEN (G418).
Decide GARDEN vs FLOWER from cycle-time tests.

## Acceptance
- 6/10 runs make 2 tips and park.
- Never crosses the A-C / D-F line.

## Test log
| Date | Result | Change |
