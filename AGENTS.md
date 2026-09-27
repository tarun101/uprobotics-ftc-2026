# AGENTS.md: UP Robotics FTC code (BIOBUZZ 2026-27)

Shared repo for all UP Robotics FTC teams. Based on Pedro Pathing Quickstart (FTC SDK v12.0).

## Stack (pinned; check before writing code)
- FTC SDK v12.0 (FtcRobotController). Android Studio only.
- Pedro Pathing **3.0.1**: `com.pedropathing:revhub:3.0.1`, `com.pedropathing:tuning:1.0.1`.
  Repo `maven { url 'https://repo.dairy.foundation/releases/' }`.
- Localizer: goBILDA Pinpoint (edit if different).
- Panels for telemetry and tuning.
- If `build.dependencies.gradle` shows another Pedro version, stop and ask.

## Pedro 3 API (do not use 1.x/2.x names)
- Paths: `Paths.line(a, b)`, `Paths.curve(a, ctrl.., b)`, `Paths.path(p1, p2)`.
- Heading: `.constant(h)`, `.linear(a, b)`, `.tangent()`, `.reverseTangent()`, `.facingPoint(p)`.
- Follow: `follower.follow(path)`, `follower.isBusy()`, `follower.hold(pose)`, `follower.pose()`, `follower.setPose(p)`.
- Poses: `PoseFactory.degrees()`; blue = `.mirrorX(70.75)` or `.mirrorY(70.75)` (field is 141.5 in). Verify axis on the visualizer.
- TeleOp: `ManualDrive.fieldCentric(...)`, `ManualDrive.headingLock(...)`, `ManualDrive.driveOrHold(...)`.
- Not valid in 3.x: `pathBuilder()`, `BezierLine`, `BezierCurve`, `setLinearHeadingInterpolation`, `startTeleopDrive`.
- Docs: https://pedropathing.com/docs. Source: github.com/Pedro-Pathing/PedroPathing (tag v3.0.1).
- Return paths from methods; do not store them as fields (Pedro docs recommendation).

## Workflow (spec-driven)
1. A student writes or edits a spec in `specs/`. No code without a spec.
2. Agent proposes a plan: classes, states, hardware names. A student approves it.
3. Agent implements in small steps. Each step must pass `./gradlew assembleDebug`.
4. Agent adds JUnit tests for pure logic (state machines, pose math, lookup tables).
5. Students test on the robot and record results in the spec's `## Test log`.
6. Update the spec when the robot teaches you something. The spec is the source of truth.

## Code layout (`TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`)
- `pedro/Constants.java`: `Constants.create(hardwareMap)` builds the Follower. Returns null until set up.
- `pedro/procedures/`: Pedro tuners and tests (from Quickstart).
- `field/`: `Alliance`, `FieldPoses` (red poses, blue mirrored), `PoseStore` (auto to TeleOp).
- `subsystems/`: one class per mechanism, implementing `Subsystem`.
- `opmodes/`: autos and TeleOps. Start from `AutoLeavePark` and `DriveTeleOp`.

## Build
- Local: `./gradlew assembleDebug` (needs Android Studio's JDK and SDK).
- GitHub Actions runs the same build on every push and PR. A red check means it does not compile.

## Code rules
- One class per subsystem under `subsystems/`. Each has `init(hardwareMap)`, `update()`, `isBusy()`.
- Subsystems are state machines. The same class runs in auto and teleop.
- No `sleep()` or blocking loops. Every state has a timeout.
- Loop order: `follower.update()`, subsystem `update()`s, then OpMode logic.
- All poses live in `field/FieldPoses.java`, red only. Blue is mirrored by the PoseFactory.
- All tuning numbers live in `Constants.java` or a subsystem config. Never inline gains.
- Hardware names come from the table below. Never invent one.
- Keep hardware behind small interfaces so logic can be unit-tested off-robot.
- Telemetry every loop: OpMode state, pose, subsystem states, loop time.

## Hardware map (students fill in)
| Name | Type | Port | Direction/notes |
|---|---|---|---|
| leftFront | DcMotorEx | | |
| leftRear | DcMotorEx | | |
| rightFront | DcMotorEx | | |
| rightRear | DcMotorEx | | |
| pinpoint | GoBildaPinpoint | I2C | pod offsets in Constants |
| (shooter, intake, gate, camera...) | | | |

## Game rules that code must respect
See `specs/00-game-facts.md`. Most important:
- AUTO paths stay on our half: columns A-C red, D-F blue (G402).
- Never push or ram the HIVE (G417). Only launch into the upward CELL.
- No NECTAR into a FLOWER before 60 s remain (G410).
- Remove only POLLEN, only from the FLOWER bottom (G418).

## Rules questions
Use the FTC Helper at https://ftc.uprobotics.tech, then open the linked manual rule. Do not answer rules from memory.
