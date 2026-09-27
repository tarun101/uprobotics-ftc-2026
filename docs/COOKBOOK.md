# Cookbook: from zero to code on your robot

For new UP Robotics FTC programmers. No coding experience needed.
Follow the steps in order. Each step says what "done" looks like.

---

## Part 1: Get your team's copy (fork)

A **fork** is your team's own copy of this repo on GitHub. You can change it without breaking anyone else's.

1. Make a free GitHub account at https://github.com/signup (one per student is best).
2. Pick one person to own the team copy. They open https://github.com/tarun101/uprobotics-ftc-2026.
3. Click **Fork** (top right). Name it after your team, e.g. `foxbots-2026`. Click **Create fork**.
4. In your fork: **Settings → Collaborators → Add people**. Add your teammates.
5. Open the **Actions** tab. Click **I understand my workflows, go ahead and enable them**.

Done when: your fork exists and teammates can see it.

**Getting updates later:** when coaches add shared code, open your fork and click **Sync fork → Update branch**.

---

## Part 2: Set up the computer (once per laptop)

1. Install **Android Studio** (Narwhal 3 Feature Drop or newer): https://developer.android.com/studio
2. Install **GitHub Desktop**: https://desktop.github.com. Sign in with GitHub.
3. In GitHub Desktop: **File → Clone repository**. Pick your team's fork. Clone it.
4. In Android Studio: **Open**, then pick the cloned folder. Click **Trust**.
5. Wait for "Gradle sync" to finish (bottom bar). The first time can take 10 minutes.
6. Click **Build → Make Project**.

Done when: the bottom bar says **BUILD SUCCESSFUL**.

**AI helper (optional but recommended):** install Claude Code (https://claude.com/claude-code) or Codex.
Open a terminal in the project folder and start it there. It reads `AGENTS.md` automatically.

---

## Part 3: Tell the code about your robot (once per robot)

1. Build the drivetrain and plug in the Pinpoint odometry computer.
2. On the Driver Hub: **⋮ → Configure Robot → New**. Scan. Name every device.
   Use the names from the table in `AGENTS.md` (`leftFront`, `rightRear`, `pinpoint`, ...).
   Names must match exactly, including capital letters.
3. Save the configuration and activate it.
4. Fill in the table in `AGENTS.md` with your ports and names.
5. Set up Pedro: follow https://pedropathing.com/docs to fill in `pedro/Constants.java`.
6. Tune Pedro with the tuners in `pedro/procedures/` (localizer first, then the rest).

Done when: the Pedro **Line Test** drives forward and back and stops on target.

Tip for the AI: "Read AGENTS.md. Help me fill in pedro/Constants.java for a mecanum drive with a goBILDA Pinpoint. Here are my motor names and pod offsets: ..."

---

## Part 4: Write your first spec

A **spec** is a plan in plain words. You write what the robot should do. The AI writes the code.

1. Copy `specs/intake.md` (our worked example). Read it. Every spec has the same parts:
   - **Goal**: one or two sentences.
   - **Hardware**: the exact device names.
   - **Behavior**: what happens for every button and situation.
   - **Rules it must respect**: game rules that apply.
   - **Acceptance**: how you'll know it works, with numbers.
   - **Test log**: filled in after testing.
2. Change it to match your robot. Is your intake a motor or a servo? What port? Which buttons?
3. Ask a teammate to read it. Could they build it from the spec alone? If not, add detail.
4. Change `Status: example` to `Status: approved`.

A good spec answers "what happens when...?" before anyone asks.
Bad: "Intake picks up balls." Good: "Hold right trigger: motor power +1.0. Let go: power 0."

---

## Part 5: Turn the spec into code with AI

In the AI tool, inside the project folder:

**Step 1: ask for a plan, not code.**
```
Read AGENTS.md and specs/intake.md. Propose a plan only:
which files you will create or change, the states, and the hardware names.
Do not write code yet.
```
Check the plan. Does it use the name `intake`? A class in `subsystems/`? The `Drive` TeleOp?
If something's wrong, say so. Then:

**Step 2: build it.**
```
Plan approved. Implement it in small steps. Build after each step.
```

**Step 3: check its work.** You should see:
- A new file `subsystems/Intake.java` that implements `Subsystem`.
- `opmodes/DriveTeleOp.java` now creates the intake, calls `intake.update()` every loop, and reads the triggers.
- **Build → Make Project** says **BUILD SUCCESSFUL**.

Ask the AI to explain any line you don't understand. That's how you learn Java.

---

## Part 6: Save and share (commit and push)

1. In GitHub Desktop, look at the changed files on the left.
2. Write a summary, e.g. `Add intake (specs/intake.md)`. Click **Commit to main**.
3. Click **Push origin**.
4. On GitHub, open your fork's **Actions** tab. Wait for a green check.
   A red X means the code doesn't compile. Paste the error into the AI and ask it to fix it.

For bigger changes, use a branch and a pull request so a teammate reviews it first.

---

## Part 7: Put it on the robot (deploy)

1. Turn on the robot. The Control Hub makes its own Wi-Fi network.
2. Connect the laptop to that Wi-Fi. The name and password are on the hub label,
   or were set in the REV Hardware Client.
3. In Android Studio, open **Terminal** (bottom) and run:
   ```
   adb connect 192.168.43.1:5555
   ```
   It should say `connected`.
4. At the top of Android Studio, pick **TeamCode** and the **REV Robotics Control Hub** device.
5. Click the green **Run ▶** button. Wait for "Install successfully finished".
6. On the Driver Hub, pick the **TeleOp** list and choose **Drive**. Press **INIT**, then **▶**.

Done when: holding gamepad 2's right trigger spins the intake.

**Safety:** robot on blocks (wheels off the ground) for the first run. Someone's hand near the stop button.

---

## Part 8: Test and write it down

1. Run each **Acceptance** check in the spec. Count results, e.g. "4 of 5 tries picked up 4 balls".
2. Fill in the spec's **Test log** table: date, result, what you changed.
3. Something wrong? Change the spec first, then ask the AI to update the code.
4. Commit and push again.

That's the loop for every feature: **spec → plan → code → build → deploy → test → update spec**.

---

## When things go wrong

| Problem | Fix |
|---|---|
| Gradle sync fails | Check the internet. **File → Sync Project with Gradle Files**. |
| `adb connect` fails | Check you're on the robot's Wi-Fi. Restart the robot. |
| "Hardware device not found" on the Driver Hub | A name in the configuration doesn't match the code. Check capitals. |
| Motor spins the wrong way | Tell the AI which way it spins; it will reverse it in code. |
| Robot drives the wrong way in autonomous | Pedro isn't tuned, or `FieldPoses` numbers are wrong. |
| Red X in GitHub Actions | Open the failed run, copy the error, ask the AI to fix it. |

Rules questions: https://ftc.uprobotics.tech
