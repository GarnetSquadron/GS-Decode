package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;


@TeleOp(name = "Main teleop")
public class MainTeleop extends OpMode
{
    public static Follower follower;
    Bot bot;
    double velScale = 0.6;
    double rotation = 0;
    double servoPos = 0.5;
    double launcherPower = 0.6;
    GoBildaPinpointDriver pinpointDriver;
    IMU imu;

    BetterControllerClass Gpad;

    @Override
    public void init()
    {
        bot = new Bot(hardwareMap);

        Gpad = new BetterControllerClass(gamepad1);

        //set up follower
        follower = CompConstants.createFollower(hardwareMap);
//        PanelsConfigurables.INSTANCE.refreshClass(this);

        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        follower.startTeleopDrive();
        telemetry.addData("follower",follower);
        pinpointDriver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpointDriver.setPosition(new Pose2D(DistanceUnit.METER,0,0, AngleUnit.RADIANS,0));
        bot.intake.closeGate();


        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();
    }

    public void loop(){
        follower.update();

        follower.setTeleOpDrive(-gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y), Math.abs(gamepad1.left_stick_x)*gamepad1.left_stick_x, Math.abs(gamepad1.right_stick_x)*gamepad1.right_stick_x, false);
        Gpad.update();

        //I wanted to find a better way, but this seems like the best option for organizing the button inputs
        //==============================INPUTS====================================\\
        boolean kickInput = Gpad.getCurrentValue("right_trigger");
        boolean intakeToggle = Gpad.getToggleValue("right_bumper");
//        boolean launcherToggle = Gpad.getToggleValue("left_bumper");
        boolean spinUpFlywheelInput = Gpad.getCurrentValue("left_bumper");
        boolean releaseTheBallsInput = Gpad.getFallingEdge("left_bumper");
        boolean turretZeroInput = gamepad1.x;
        boolean autoAimOn = Gpad.getToggleValue("b");
        boolean openGateInput = gamepad1.a;

        servoPos = gamepad1.left_trigger*20+30;

        //==============================OUTPUTS===================================\\

        //welcome to the now slightly contained mess

        //intake
//        if(gamepad1.x){
//            intake.setPower(-1);
//        } else {
        //intake.setPower(intakeToggle ?1:0);
//        }
        //if (gamepad1.b){intake.loadBall();};

//        if (gamepad1.x){
//            intake.prepareForIntaking();
//        } else intake.unprepareIntake();
        //launcher

        //launcher.setPower(launcherToggle?-launcherPower:0);
        if(releaseTheBallsInput){
            bot.launchHandler.initLaunch();
        }
        if(spinUpFlywheelInput){
            telemetry.addData("speed",bot.launcher.spinUpFlywheel(0.5));
            bot.intake.closeGate();
        }else{
            if(intakeToggle){
                bot.intake.setPower(1);
            }else bot.intake.stop();
        }
//        if(launcherToggle){
//            telemetry.addData("spinningup",bot.launcher.spinUpFlywheel());
//            if(launcher.spinUpFlywheel()){
//
//                intake.shoot3();
//            }
//        } else{launcher.setPower(0);}
        if(turretZeroInput){
            bot.turret.zero();
        }
        if(autoAimOn){
            //(rotation - turretRange[0])%(Math.PI)+ turretRange[0]%(Math.PI)-Math.PI
            rotation = bot.turret.aimTowardsGoal(FieldDimensions.goalPositionBlue, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading() /*Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw())*/);

        } else {
            bot.turret.setPower(0);
        }
        bot.launcher.setAngle(servoPos);
//        if(openGateInput){
//            intake.openGate();
//        }
//        else {
//            intake.closeGate();
//        }
        double distance = Math.hypot(FieldDimensions.goalPositionBlue[0]-follower.getPose().getX(), FieldDimensions.goalPositionBlue[1]-follower.getPose().getY());

        //manualOrAutoAimHood(gamepad1.b,distance);
        //toggleLaunchPower(Gpad.getToggleValue("gamepad1.rightTrigger"));// there are too many buttons in use rn so I am taking this away for now
        launcherPower = 0.9;

//        if(kickInput){
//            intake.kickBall();
//        } else{
//            intake.unKick();
//        }
        telemetry.addData("time since start",bot.update());
        telemetry.addData("starting iteration", releaseTheBallsInput);

        //========================TELEMETRY===========================\\
        telemetry.addLine();
        telemetry.addLine("radians:");
        telemetry.addData("input angle",  -Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw()));
        telemetry.addData("supposed output angle", bot.turret.getRotation(-Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw())));
        telemetry.addData("actual output angle", bot.turret.turretRot.getTargetPosition());
        telemetry.addLine();
        telemetry.addLine("degrees:");
        telemetry.addData("input angle",  -imu.getRobotYawPitchRollAngles().getYaw());
        telemetry.addData("supposed output angle",Math.toDegrees(bot.turret.getRotation(-Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw()))));
        telemetry.addData("actual output angle", Math.toDegrees(bot.turret.turretRot.getTargetPosition()));
        telemetry.addLine();




//        telemetry.addData("left gate position",intake.getGatePositions()[0]);
//        telemetry.addData("right gate position",intake.getGatePositions()[1]);
//        telemetry.addData("left stick x",gamepad1.left_stick_x);
//        telemetry.addData("left stick y",gamepad1.left_stick_y);
//        telemetry.addData("right stick x",gamepad1.right_stick_x);
//        telemetry.addData("right stick y",gamepad1.right_stick_y);
//        telemetry.addData("rotation",bot.turret.getEncoder().getPos());
//        telemetry.addData("target", bot.turret.turretRot.getTargetPosition());
//        //telemetry.addData("mode", rotation);
//        telemetry.addData("(rotation - Math.toRadians(25))%(Math.PI)",( Math.toRadians(-imu.getRobotYawPitchRollAngles().getYaw()) - Math.toRadians(25))%(Math.PI));
//        telemetry.addData("(-1.25)%(Math.PI)",(-1.25)%(Math.PI));
//        telemetry.addData("(rotation - Math.toRadians(25))",( Math.toRadians(-imu.getRobotYawPitchRollAngles().getYaw()) - Math.toRadians(25)));
//        telemetry.addData("Math.toRadians(25)%(Math.PI)-Math.PI", Math.toRadians(25)%(Math.PI)-Math.PI);
//        telemetry.addData("turretRange[0]+(turretRange[0]-rotation+ExtraMath.Tau)*rangeSize/(ExtraMath.Tau-rangeSize)", Math.toRadians(-75)+(Math.toRadians(-75)-rotation+ ExtraMath.Tau)*Math.toRadians(75*2)/(ExtraMath.Tau-Math.toRadians(75*2)));
//        telemetry.addData("Math.toRadians(75*2)/(ExtraMath.Tau-Math.toRadians(75*2))", Math.toRadians(75*2)/(ExtraMath.Tau-Math.toRadians(75*2)));
//        telemetry.addData("position rot", Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw()));
//        telemetry.addData("ticks",bot.turret.getEncoder().getTicks());
//        telemetry.addData("CPR",bot.turret.getEncoder().getCPR());
//        telemetry.addData("scale",bot.turret.getEncoder().getScale());
//        telemetry.addData("motor type", bot.turret.getMotorType());
//        telemetry.addLine("---------Position-------");
        telemetry.addData("position",follower.getPose());//TODO: check this, I think that the position is not getting updated, its possible the pinpoint isn't connected well or something
//        telemetry.addData("goal x",FieldDimensions.goalPositionBlue[0]);
//        telemetry.addData("goal y",FieldDimensions.goalPositionBlue[1]);
//        telemetry.addData("distance", distance);
//        telemetry.addData("velScale",velScale);
//        telemetry.addData("TOGGLER",Gpad.getToggleValue("left_bumper"));
//        pinpointDriver.update();
//        telemetry.addData("pinpointPos", pinpointDriver.getPosition());

//        telemetry.addData("servoPos but better", (servoPos-Math.toRadians(30))*0.5/ Math.toRadians(20));
//        telemetry.addData("launcherPower", launcherPower);
//        telemetry.addData("lbump toggle",Gpad.getToggleValue("left_bumper"));
//        //telemetry.addData("hoodPos",launcher.getHoodPos());
//        telemetry.addData("lbump val",Gpad.getCurrentValue("left_bumper"));
//        telemetry.addData("lbump redge",Gpad.getRisingEdge("left_bumper"));
        //telemetry.addData("lbump changed",Gpad.getCurrentValue("left_bumper"));
        telemetry.update();

    }
}
