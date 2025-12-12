package org.firstinspires.ftc.teamcode.OpModes.teleops;

import static org.firstinspires.ftc.teamcode.PurelyCalculators.AngleFinder.g;
import static org.firstinspires.ftc.teamcode.PurelyCalculators.AngleFinder.getAngles;
import static org.firstinspires.ftc.teamcode.PurelyCalculators.AngleFinder.targetHeight;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.SettingSelectorOpMode;
import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;

import java.util.function.DoubleUnaryOperator;

import kotlin.Pair;


@TeleOp(name = "Main teleop")
public class MainTeleop extends SettingSelectorOpMode
{
    public static Follower follower;
    Bot bot;
    double rotation = 0;
    double servoPos = 0.5;
    double launcherPower = 0.6;
    double[] targetGoalPos;
    double distance = 100;
    double vel = 400;
    double velScale = 311;
    double velToDistRatio = 4;
    double a = 386.09*386.09/4;
    double launchAngle = 40;
    //double b = 386.09*targetHeight-vel*vel;
    double c = distance*distance+targetHeight*targetHeight;
    DoubleUnaryOperator distToVel =  (dist)-> {
        double tsquare = 2 * (dist*Math.tan(Math.toRadians(launchAngle))- targetHeight)/g;
        return Math.sqrt(
                a*tsquare+c/tsquare+g*targetHeight
        );
    };//Math.sqrt(g*(AngleFinder.targetHeight+0.1+Math.sqrt((AngleFinder.targetHeight+0.1)*(AngleFinder.targetHeight+0.1)+dist*dist)))+20;//(dist*dist/10) * velToDistRatio;
    GoBildaPinpointDriver pinpointDriver;
    IMU imu;

    BetterControllerClass Gpad;

    public MainTeleop()
    {
        super(new Pair[]{
                new Pair(
                        new String[]{"red","blue"},"color"
                ),
                new Pair(
                        new String[]{"goal","tiny triangle","testing pos"},"position"
                )
        });
    }

    @Override
    public void init()
    {
        bot = new Bot(hardwareMap);

        Gpad = new BetterControllerClass(gamepad1);

        //set up follower
        follower = CompConstants.createFollower(hardwareMap);
//        PanelsConfigurables.INSTANCE.refreshClass(this);

        //follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        bot.intake.closeGate();


        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();
    }
    @Override
    public void init_loop(){
        telemetry.addLine("REMEMBER TO POINT THE TURRET STRAIGHT");
        telemetry.addLine();
        super.init_loop();
    }
    @Override
    public void start(){
        super.start();
        {
            if (selections.get("color") == "red")
            {
                targetGoalPos = FieldDimensions.goalPositionRed;
                if (selections.get("position") == "goal")
                {
                    follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
                }
                else if(selections.get("position") == "tiny triangle")
                {
                    follower.setStartingPose(FieldDimensions.botOnTinyTriangleRedSide);
                }
                else{
                    follower.setStartingPose(new Pose(FieldDimensions.goalPositionRed[0],0,0));
                }
            }
            else
            {
                targetGoalPos = FieldDimensions.goalPositionBlue;
                if (selections.get("position") == "goal")
                {
                    follower.setStartingPose(FieldDimensions.botTouchingBlueGoal);
                }
                else
                {
                    follower.setStartingPose(FieldDimensions.botOnTinyTriangleBlueSide);
                }
            }
        }
        follower.startTeleopDrive();
    }

    public void loop(){
        follower.update();

        follower.setTeleOpDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, Math.abs(gamepad1.right_stick_x)*gamepad1.right_stick_x, false);
        Gpad.update();

        //I wanted to find a better way, but this seems like the best option for organizing the button inputs
        //==============================INPUTS====================================\\
        //boolean kickInput = Gpad.getCurrentValue("right_trigger");
        boolean intakeToggle = Gpad.getCurrentValue("right_bumper");
//        boolean launcherToggle = Gpad.getToggleValue("left_bumper");
        boolean spinUpFlywheelInput = Gpad.getCurrentValue("left_bumper");
        boolean releaseTheBallsInput = Gpad.getFallingEdge("left_bumper");
        boolean turretZeroInput = gamepad1.x;
        boolean autoAimOn = Gpad.getCurrentValue("right_trigger");
        //boolean openGateInput = gamepad1.a;
        //boolean extraSpinUpInput = Gpad.getCurrentValue("left_trigger");
        boolean autoHood = Gpad.getCurrentValue("left_trigger");

        distance=/*gamepad1.dpadUpWasPressed()?1:(gamepad1.dpadDownWasPressed()?-1:0);*/ Math.hypot(targetGoalPos[0]-follower.getPose().getX(), targetGoalPos[1]-follower.getPose().getY());
        vel = distToVel.applyAsDouble(distance);
        velScale+=gamepad1.dpadRightWasPressed()?1:(gamepad1.dpadLeftWasPressed()?-1:0); //Math.hypot(FieldDimensions.goalPositionBlue[0]-follower.getPose().getX(), FieldDimensions.goalPositionBlue[1]-follower.getPose().getY());
        launchAngle+=gamepad1.aWasPressed()?1:(gamepad1.yWasPressed()?-1:0);

        double[] angles = getAngles(vel,distance);
        //servoPos = gamepad1.left_trigger*20+30;

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
            bot.launchHandler.initLaunch(vel/velScale);
        }
        if(spinUpFlywheelInput){
            telemetry.addData("speed",bot.launcher.spinUpFlywheel(vel/velScale));
            bot.intake.closeGate();
        }else{
            if(intakeToggle){
                bot.intake.setPower(1);
            }else bot.intake.stop();
//            if(openGateInput){
//                bot.intake.openGate();
//            }
//            else bot.intake.closeGate();
//            if(extraSpinUpInput){
//                bot.launcher.spinUpFlywheel(1);
//            }else bot.launcher.setPower(0);
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
            rotation = bot.turret.aimTowardsGoal(FieldDimensions.goalPositionRed, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading() /*Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw())*/);

        } else {
            bot.turret.setPower(0);
        }
        //bot.launcher.setAngle(servoPos);
//        if(openGateInput){
//            intake.openGate();
//        }
//        else {
//            intake.closeGate();
//        }

        //bot.launcher.aimServo(distance,vel);
        bot.launcher.setAngle(launchAngle);
        //manualOrAutoAimHood(gamepad1.b,distance);
        //toggleLaunchPower(Gpad.getToggleValue("gamepad1.rightTrigger"));// there are too many buttons in use rn so I am taking this away for now
        launcherPower = 0.9;

//        if(kickInput){
//            intake.kickBall();
//        } else{
//            intake.unKick();
//        }
        telemetry.addData("time since start",bot.update());
//        telemetry.addData("starting iteration", releaseTheBallsInput);

        //========================TELEMETRY===========================\\
        telemetry.addData("distance",distance);
        telemetry.addData("velocity",vel);
        telemetry.addData("velScale",velScale);
        telemetry.addData("power",vel/velScale);
        telemetry.addData("length",angles.length);
        for(int i=0;i<angles.length;i++){
            telemetry.addData("angle "+String.valueOf(i),Math.toDegrees(angles[i]));
        }
        //telemetry.addData("acual power", bot.launcher.g);
        telemetry.addData("targetGoalX",targetGoalPos[0]);
        telemetry.addData("targetGoalY",targetGoalPos[1]);
//
        double deltaX = FieldDimensions.goalPositionRed[0]-follower.getPose().getX();
        double deltaY = FieldDimensions.goalPositionRed[1]-follower.getPose().getY();
        double tan = Math.atan(deltaY/deltaX);
        telemetry.addLine();
        telemetry.addData("bot heading",  follower.getPose().getHeading());
        telemetry.addData("tan",  tan);
        telemetry.addLine("radians:");
        telemetry.addData("input angle",  tan-follower.getPose().getHeading()+Math.PI);
        telemetry.addData("supposed output angle", bot.turret.getRotation(tan-follower.getPose().getHeading()+Math.PI));
        //telemetry.addData("actual output angle", bot.turret.turretRot.getTargetPosition());
        telemetry.addLine();
        telemetry.addLine("degrees:");
        telemetry.addData("input angle",  Math.toDegrees(tan-follower.getPose().getHeading()+180));
        telemetry.addData("supposed output angle",Math.toDegrees(bot.turret.getRotation(tan-follower.getPose().getHeading()+Math.PI)));
        //telemetry.addData("actual output angle", Math.toDegrees(bot.turret.turretRot.getTargetPosition()));
        telemetry.addLine();
        telemetry.addData("real angle", rotation);
        telemetry.addData("deltaX", deltaX);
        telemetry.addData("deltaY", deltaY);
        telemetry.addData("deltaY/deltaX",deltaY/deltaX);
        telemetry.addData("atangent", tan);
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
