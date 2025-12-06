package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.GamepadClasses.BetterControllerClass;
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

    BetterControllerClass Gpad;

    @Override
    public void init()
    {

        intake = new Intake(hardwareMap);
        launcher = new Launcher(hardwareMap);

        Gpad = new BetterControllerClass(gamepad1);

        //set up follower
        follower = CompConstants.createFollower(hardwareMap);
//        PanelsConfigurables.INSTANCE.refreshClass(this);

        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        follower.startTeleopDrive();
        telemetry.addData("follower",follower);
    }

    public void loop(){
        follower.update();

        follower.setTeleOpDrive(-gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y), Math.abs(gamepad1.left_stick_x)*gamepad1.left_stick_x, Math.abs(gamepad1.right_stick_x)*gamepad1.right_stick_x, true);
        Gpad.update();
        //intake
        if(gamepad1.x){
            intake.setPower(-1);
        } else {
            intake.setPower(Gpad.getToggleValue("right_bumper") ?1:0);
        }
        //if (gamepad1.b){intake.loadBall();};

//        if (gamepad1.x){
//            intake.prepareForIntaking();
//        } else intake.unprepareIntake();
        //launcher

        launcher.setPower(Gpad.getToggleValue("left_bumper")?-launcherPower:0);
//        if(gamepad1.left_trigger==1){
//            launcher.zeroTurret();
//        }
        if(gamepad1.b){
            telemetry.addData("target",launcher.aimTurret(FieldDimensions.goalPositionBlue, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading()));
        } else {
            launcher.setTurretPower(0);
        }
        if(gamepad1.y){
            launcher.zeroTurret();
        }
//        if (Gpad.getRisingEdge("y")){
//            servoPos+=10;
//        }
//        if (Gpad.getRisingEdge("a")){
//            servoPos-=10;
//        }
        servoPos = gamepad1.left_trigger*20+30;
        double distance = Math.hypot(FieldDimensions.goalPositionBlue[0]-follower.getPose().getX(), FieldDimensions.goalPositionBlue[1]-follower.getPose().getY());
        manualOrAutoAimHood(gamepad1.a,distance);
        toggleLaunchPower(Gpad.getToggleValue("gamepad1.rightTrigger"));

//        if(gamepad1.a){
//            intake.kickBall();
//        } else{
//            intake.unKick();
//        }
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
        telemetry.addData("servoPos but better", (servoPos-Math.toRadians(30))*0.5/ Math.toRadians(20));
        telemetry.addData("launcherPower", launcherPower);
        telemetry.addData("lbump toggle",Gpad.getToggleValue("left_bumper"));
        //telemetry.addData("hoodPos",launcher.getHoodPos());
        telemetry.addData("lbump val",Gpad.getCurrentValue("left_bumper"));
        telemetry.addData("lbump redge",Gpad.getRisingEdge("left_bumper"));
        //telemetry.addData("lbump changed",Gpad.getCurrentValue("left_bumper"));
        telemetry.update();

    }
    public void toggleLaunchPower(boolean input){
        launcherPower = input?1:0.7;
    }
    public void manualOrAutoAimHood(boolean input,double distance){
        if (input){
            launcher.aimServo(distance,launcher.getFlywheelEncoder().getVelocity()*0.05);//I think that the flywheel has about a 5cm radius.
        }
        else{
            servoPos = ExtraMath.Clamp(servoPos,50,30);
            launcher.setAngle(Math.toRadians(servoPos));
        }
    }
}
