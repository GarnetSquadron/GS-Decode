package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.HardwareControls.Intake;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;



@TeleOp(name = "MoveToPosTesting")
public class MoveToPosTesting extends OpMode
{
    public static Follower follower;
    Intake intake;
    Launcher launcher;
    BetterControllerClass Gpad;

    @Override
    public void init()
    {
        intake = new Intake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        Gpad = new BetterControllerClass(gamepad1);

        follower = CompConstants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(130, 110));
        follower.startTeleopDrive();
        telemetry.addData("follower",follower);
    }

    public void loop(){
        follower.update();
        follower.setTeleOpDrive(-gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y), Math.abs(gamepad1.left_stick_x)*gamepad1.left_stick_x, Math.abs(gamepad1.right_stick_x)*gamepad1.right_stick_x, true);
        Gpad.update();

        if(gamepad1.x){
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }

//        if(gamepad1.right_bumper){
//            launcher.setTurretPower(1);
//        } else {
//            launcher.setTurretPower(0);
//        }

        if (gamepad1.left_bumper){
            BezierLine ShotOnRed = new BezierLine(
                    follower.getPose(),
                    new Pose(65, 16)
            );
            Path path = new Path(ShotOnRed);
            follower.followPath(path);
        }

        telemetry.update();
    }
}

