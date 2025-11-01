package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.bylazar.configurables.PanelsConfigurables;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;

///import ViperSlidesSubSystem;

@TeleOp(name = "Main teleop")
public class MainTeleop extends OpMode
{
    //ViperSlidesSubSystem viperSlidesSubSystem = new ViperSlidesSubSystem();
    public static Follower follower;
    Intake intake;
    Launcher launcher;

    @Override
    public void init()
    {

        intake = new Intake(hardwareMap);
        launcher=new Launcher(hardwareMap);

        //set up follower
        follower = CompConstants.createFollower(hardwareMap);
        PanelsConfigurables.INSTANCE.refreshClass(this);

        follower.setStartingPose(new Pose(-50,50,0));
        follower.startTeleopDrive();
        telemetry.addData("follower",follower);
    }

    public void loop(){
        follower.update();
        follower.setTeleOpDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true);
        if(gamepad1.right_bumper){
            intake.setPower(1);
        }
        else if(gamepad1.a){
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }
        if(gamepad1.left_bumper){
            launcher.setPower(-0.7);
        } else{
            launcher.setPower(0);
        }
        if(gamepad1.b){
            launcher.zeroTurret();
        }
        if(gamepad1.y){
            telemetry.addData("target",launcher.aimTurret(FieldDimensions.goalPositionBlue, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading()));
        } else {
            launcher.setTurretPower(0);
        }
        telemetry.addData("rotation",launcher.getTurretEncoder().getPos());
        telemetry.addData("ticks",launcher.getTurretEncoder().getTicks());
        telemetry.addData("CPR",launcher.getTurretEncoder().getCPR());
        telemetry.addData("scale",launcher.getTurretEncoder().getScale());
        telemetry.addData("motor type", launcher.getMotorType());
        telemetry.addLine("---------Position-------");
        telemetry.addData("position",follower.getPose());
        telemetry.addData("goal x",FieldDimensions.goalPositionBlue[0]);
        telemetry.addData("goal y",FieldDimensions.goalPositionBlue[1]);
        telemetry.update();

    }
}
