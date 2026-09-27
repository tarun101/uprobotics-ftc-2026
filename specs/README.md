# Specs

One file per mechanism, OpMode or routine. Keep each under one page.

## Order to build
1. `field-poses.md` (measure first)
2. `shooter.md`, `hive-tracker.md`
3. `auto-common.md`, then `auto-T0` to `auto-T3`
4. `teleop.md`

## Template
```markdown
# Spec: <name>
Status: draft | approved | built | tested
Owner: <student>

## Goal
One or two sentences.

## Behavior
States, transitions, buttons, poses. Numbers where known; TBD where not.

## Rules it must respect
Rule IDs from the manual.

## Acceptance
Measurable checks on the robot, e.g. "8/10 runs finish by 28 s".

## Out of scope

## Test log
| Date | Result | Change |
```
