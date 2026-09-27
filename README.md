# UP Robotics FTC 2026-27 (BIOBUZZ)

Robot code for UP Robotics FTC teams. Built on the [Pedro Pathing Quickstart](https://github.com/Pedro-Pathing/Quickstart) (FTC SDK v12.0, Pedro Pathing 3.0.1).

## Start here
1. Read `AGENTS.md`: how we write code with AI, and the rules it follows.
2. Read `specs/README.md`: every feature starts as a spec.
3. Set up Pedro: fill in `pedro/Constants.java`, then tune with the tuners in `pedro/procedures/` ([Pedro docs](https://pedropathing.com/docs)).
4. Measure field poses into `specs/field-poses.md` and `field/FieldPoses.java`.
5. Run `T0 Leave + Park`, then build up the autos in `specs/`.

## Setup
- Android Studio (Narwhal 3 Feature Drop or later). Open this folder as a project.
- Deploy over Wi-Fi to the Control Hub with `adb connect 192.168.43.1:5555`.
- Every push is compiled by GitHub Actions (see the Actions tab).

## Each team
Work on your own branch (for example `infinity-foxes/main`, `foxbots/main`). Share common code through pull requests to `main`.

## Rules questions
Use the [FTC Helper](https://ftc.uprobotics.tech), then read the linked rule in the Competition Manual.

The original FTC SDK README is in `doc/SDK-README.md`.
