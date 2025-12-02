package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.bylazar.configurables.PanelsConfigurables;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.GamepadClasses.ToggleGamepad;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;



@TeleOp(name = "Main teleop")
public class MainTeleop extends OpMode
{
    public static Follower follower;
    Intake intake;
    Launcher launcher;
    double velScale = 0.6;
    double servoPos = 0.5;
    double launcherPower = 0.6;

    ToggleGamepad Gpad;

    @Override
    public void init()
    {

        intake = new Intake(hardwareMap);
        launcher = new Launcher(hardwareMap);

        Gpad = new ToggleGamepad(gamepad1);

        //set up follower
        follower = CompConstants.createFollower(hardwareMap);
        PanelsConfigurables.INSTANCE.refreshClass(this);

        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        follower.startTeleopDrive();
        telemetry.addData("follower",follower);
    }

    public void loop(){
        follower.update();

        follower.setTeleOpDrive(-gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y), Math.abs(gamepad1.left_stick_x)*gamepad1.left_stick_x, Math.abs(gamepad1.right_stick_x)*gamepad1.right_stick_x, true);
        Gpad.update();
        //intake
        if(gamepad1.right_trigger==1){
            intake.setPower(-1);
        } else {
            intake.setPower(Gpad.getToggleValue("right_bumper") ?1:0);
        }
        if (gamepad1.b){intake.openGate();}else intake.closeGate();
        //launcher
        /*
        launcher.setPower(Gpad.getToggleValue("left_bumper")?-launcherPower:0);
        if(gamepad1.left_trigger==1){
            launcher.zeroTurret();
        }
        if(gamepad1.dpad_down){
            telemetry.addData("target",launcher.aimTurret(FieldDimensions.goalPositionBlue, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading()));
        } else {
            launcher.setTurretPower(0);
        }
        double distance = Math.hypot(FieldDimensions.goalPositionBlue[0]-follower.getPose().getX(), FieldDimensions.goalPositionBlue[1]-follower.getPose().getY());
        if (Gpad.getToggleValue("x")){
            launcher.aimServo(distance,launcher.getFlywheelEncoder().getVelocity()*0.05);//I think that the flywheel has about a 5cm radius.
        }
        else{
            servoPos = ExtraMath.Clamp(servoPos,50,30);
            launcher.setAngle(servoPos);
        }
        if (gamepad1.aWasPressed()){
            servoPos+=3;
        }
        if (gamepad1.bWasPressed()){
            servoPos-=3;
        }
        if(Gpad.getToggleValue("y")){
            launcherPower = 0.7;
        }
        else
            launcherPower = 0.5;
        */
        if(gamepad1.a){
            intake.kickBall();
        } else{
            intake.unKick();
        }
        telemetry.addData("left gate position",intake.getGatePositions()[0]);
        telemetry.addData("right gate position",intake.getGatePositions()[1]);
        telemetry.addData("left stick x",gamepad1.left_stick_x);
        telemetry.addData("left stick y",gamepad1.left_stick_y);
        telemetry.addData("right stick x",gamepad1.right_stick_x);
        telemetry.addData("right stick y",gamepad1.right_stick_y);
        telemetry.addData("rotation",launcher.getTurretEncoder().getPos());
        telemetry.addData("ticks",launcher.getTurretEncoder().getTicks());
        telemetry.addData("CPR",launcher.getTurretEncoder().getCPR());
        telemetry.addData("scale",launcher.getTurretEncoder().getScale());
        telemetry.addData("motor type", launcher.getMotorType());
        telemetry.addLine("---------Position-------");
        telemetry.addData("position",follower.getPose());
        telemetry.addData("goal x",FieldDimensions.goalPositionBlue[0]);
        telemetry.addData("goal y",FieldDimensions.goalPositionBlue[1]);
//        telemetry.addData("distance", distance);
        telemetry.addData("velScale",velScale);
        telemetry.addData("servoPos", servoPos);
        telemetry.addData("launcherPower", launcherPower);
        telemetry.update();

    }
}
