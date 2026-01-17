package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.bylazar.configurables.PanelsConfigurables;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;

@TeleOp(name = "Library Class")
public class LibraryClass extends OpMode {

    public static Follower follower;

    double speedScale = 0.6;   // default speed
    double slowSpeed  = 0.3;   // slow mode
    double fastSpeed  = 0.8;   // fast mode

    @Override
    public void init() {

        // Set up follower (same as your main teleop)
        follower = CompConstants.createFollower(hardwareMap);
        PanelsConfigurables.INSTANCE.refreshClass(this);

        // Starting position on the field
        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        follower.startTeleopDrive();

        telemetry.addLine("Student Drive Template Initialized");
        telemetry.addLine("Edit the STUDENT CODE section in loop() only.");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Update path follower / odometry
        follower.update();

        // === ========================= ===
        // ===     STUDENT CODE START    ===
        // === ========================= ===

        // These are the ONLY 3 values students need to set:
        double forward = 0.0;   // + forward, - backward
        double strafe  = 0.0;   // + right,   - left (for mecanum)
        double turn    = 0.0;   // + turn right, - turn left

        // --- Example 1: basic forward/back only (no turn) ---
        // forward = -gamepad1.left_stick_y; // up on stick = forward

        // --- Example 2: forward + turning, no strafe (arcade drive) ---
        forward = -gamepad1.left_stick_y;   // move robot forward/back
        turn    = gamepad1.right_stick_x;   // turn robot left/right

        // Students can change this to try their own control schemes.
        // e.g., tank drive, or triggers for forward/back, etc.



        // --- Example speed switch using a button ---
        // Hold right bumper for SLOW mode, otherwise FAST.
        if (gamepad1.right_bumper) {
            speedScale = slowSpeed;
        } else {
            speedScale = fastSpeed;
        }

        // === ======================= ===
        // ===     STUDENT CODE END    ===
        // === ======================= ===

        // Scale and curve inputs a bit for smoother control
        double f = forward * Math.abs(forward) * speedScale;
        double s = strafe  * Math.abs(strafe)  * speedScale;
        double t = turn    * Math.abs(turn)    * speedScale;

        // Send drive commands to your follower (same style as main teleop)
        follower.setTeleOpDrive(f, s, t, true);

        // --- Minimal telemetry for students ---
        telemetry.addData("Forward", forward);
        telemetry.addData("Strafe",  strafe);
        telemetry.addData("Turn",    turn);
        telemetry.addData("SpeedScale", speedScale);
        telemetry.addData("Pose", follower.getPose());
        telemetry.update();
    }
}
