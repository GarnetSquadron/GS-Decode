package org.firstinspires.ftc.teamcode.OpModes.teleops;

import static org.firstinspires.ftc.teamcode.logo.Logo.logo;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.HardwareControls.KickStand;
import org.firstinspires.ftc.teamcode.OpModes.CurrentMonitor;
import org.firstinspires.ftc.teamcode.OpModes.SettingSelectorOpMode;
import org.firstinspires.ftc.teamcode.PurelyCalculators.TrajectoryMath;
import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.HashMap;

import kotlin.Pair;


@TeleOp(name = "Main teleop \uD83E\uDD54")
public class MainTeleop extends SettingSelectorOpMode
{
    double driverAngle = 0;
    VoltageSensor voltageSensor;
    KickStand stand;
    boolean adjustingConstants = false;
    Pose startingPose;
    double turretPos;
    public static Follower follower;
    public DcMotorEx lf,rf,lb,rb,intakeMotor;
    public double loopStartTime = 0;
    Bot bot;
    double[] targetGoalPos;
    double distance = 100;
    double vel = 400;
    boolean headlessDriveOn;
    String intakeButtonName,launchButtonName,aimButtonName,stopLaunchName;
    static HashMap<String,String> selections = new HashMap<String, String>(){{put("personal config","Nathan");put("position","tiny triangle");}};

    BetterControllerClass Gpad;

    CurrentMonitor currentMonitor;

    public MainTeleop()
    {
        //settings
        super(new Pair[]{
                new Pair(
                        new String[]{"yes","no"},"remember old position?"
                ),
                new Pair(
                        new String[]{"red","blue"},"color"
                ),
                new Pair(
                        new String[]{"goal","tiny triangle","testing"},"position"
                ),
                new Pair(
                        new String[]{"on","off"},"telemetry"
                ),
                new Pair(
                        new String[]{"James","Nathan","Charlie","DJ"}, "personal config"
                ),
        }, selections
        );
    }

    @Override
    public void init()
    {

        voltageSensor = hardwareMap.voltageSensor.get("Control Hub");

    }
    @Override
    public void init_loop(){
        telemetry.addLine("REMEMBER TO POINT THE TURRET STRAIGHT");
        telemetry.addLine();
        super.init_loop();
    }
    public void handleSettings(){
        //controls:

        switch (selections.get("personal config")){
            case "James":
                intakeButtonName = "left_bumper";
                launchButtonName = "right_trigger";
                aimButtonName = "right_bumper";
                stopLaunchName = "left_trigger";
                headlessDriveOn = false;
                break;
            case "Nathan":
                intakeButtonName = "right_bumper";
                launchButtonName = "right_trigger";
                aimButtonName = "left_trigger";
                stopLaunchName = "left_bumper";
                headlessDriveOn = true;
                break;
            case "Charlie":
                intakeButtonName = "right_bumper";
                launchButtonName = "right_trigger";
                aimButtonName = "left_trigger";
                stopLaunchName = "left_bumper";
                headlessDriveOn = true;
                break;
            case "DJ":
                intakeButtonName = "left_bumper";
                //idk I think it would be cool to put the aiming and the launching on one hand
                launchButtonName = "right_trigger";
                aimButtonName = "right_bumper";
                stopLaunchName = "left_trigger";
                headlessDriveOn = true;
                break;
            default:
                intakeButtonName = "right_bumper";
                launchButtonName = "left_bumper";
                aimButtonName = "left_trigger";
                stopLaunchName = "right_trigger";
                headlessDriveOn = false;
        }


        switch (selections.get("color"))
        {
            case "red":
            {
                targetGoalPos = FieldDimensions.goalVectorRed;
                switch (selections.get("position"))
                {
                    case "goal":
                        startingPose = FieldDimensions.botTouchingRedGoal;
                        break;
                    case "tiny triangle":
                        startingPose = FieldDimensions.botOnTinyTriangleRedSide;
                        break;
                    case "testing":
//                        follower.setStartingPose(new Pose(FieldDimensions.goalPositionRed[0], targetGoalPos[1]-70, -Math.PI/2));
                        startingPose = new Pose(72, 72, Math.PI);
                        break;
                }
                break;
            }
            case "blue":
            {
                targetGoalPos = FieldDimensions.goalVectorBlue;
                switch (selections.get("position"))
                {
                    case "goal":
                        startingPose = FieldDimensions.botTouchingBlueGoal;
                        break;
                    case "tiny triangle":
                        startingPose = FieldDimensions.botOnTinyTriangleBlueSide;
                        break;
                    case "testing":
                        startingPose = new Pose(targetGoalPos[0], 0, 0);
                        break;
                }
                driverAngle = Math.PI;
                break;
            }

        }
        turretPos = 0;
        if(selections.get("remember old position?")=="yes"){
            startingPose = Bot.currentPos[0];
            turretPos = Bot.currentTurretPosition;
            if(Bot.redSide){
                targetGoalPos = FieldDimensions.goalVectorRed;
            }else{
                targetGoalPos = FieldDimensions.goalVectorBlue;

            }
        }
    }
    @Override
    public void start(){
        super.start();
        selections = super.selections;// now we update the static field


        handleSettings();


        stand = new KickStand(hardwareMap);


        bot = new Bot(hardwareMap,targetGoalPos,turretPos);


        Gpad = new BetterControllerClass(gamepad1);

        //set up follower
        follower = bot.follower;
        follower.setStartingPose(startingPose);
//        PanelsConfigurables.INSTANCE.refreshClass(this);

        //follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
//        follower.setStartingPose(new Pose(FieldDimensions.goalPositionRed[0], 0, 0));
        bot.intake.closeGate();
        bot.intake.unKick();
        //bot.intake.openGate();
        currentMonitor = new CurrentMonitor(hardwareMap,bot);
        follower.startTeleopDrive();
    }
    public void move(double yInput,double xInput,double turnInput){
        double forwardForce = modifyInput(0.05,yInput);
        double strafeForce = modifyInput(0.05,xInput);
        follower.setTeleOpDrive(forwardForce, strafeForce, turnInput, !headlessDriveOn);
    }

    /**
     * modifies the input so that it is easier to go slow I think
     * @return
     */
    public double modifyInput(double min,double input){
        return (1-min)*input+min*Math.signum(input);
    }
    @Override
    public void loop(){
        follower.update();

        follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, !headlessDriveOn,driverAngle);
//        move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        Gpad.update();

        //I wanted to find a better way, but this seems like the best option for organizing the button inputs
        //==============================INPUTS====================================\\
        boolean intakeToggle = Gpad.getCurrentValue(intakeButtonName);
        boolean initSpinUpFlywheelInput = Gpad.getRisingEdge(launchButtonName);
        boolean spinUpFlywheelInput = Gpad.getCurrentValue(launchButtonName);
        boolean releaseTheBallsInput = Gpad.getFallingEdge(launchButtonName);
        boolean stopTheBallsInput = Gpad.getRisingEdge(stopLaunchName);
//        boolean turretZeroInput = gamepad1.x;
        boolean autoAimOn = Gpad.getCurrentValue(aimButtonName);
        boolean resetTurretPID = Gpad.getRisingEdge(aimButtonName);
        boolean extendKickStand = (Gpad.getCurrentValue("a") && Gpad.getCurrentValue("x") && !stand.extended);
        boolean resetVision = Gpad.getCurrentValue("y");

        distance=Math.hypot(targetGoalPos[0]-follower.getPose().getX(), targetGoalPos[1]-follower.getPose().getY());
        double minAngleVelSquared = TrajectoryMath.getVelSquared(distance, Math.toRadians(RobotDimensions.Hood.minAngle));
        double maxAngleVelSquared = TrajectoryMath.getVelSquared(distance, Math.toRadians(RobotDimensions.Hood.maxAngle));
        double[] velBounds = TrajectoryMath.getVelBoundsFromVelSquaredBounds(minAngleVelSquared,maxAngleVelSquared,distance);


        bot.velMap.increment(bot.getDistance(),Gpad.getIncrement("dpad_up","dpad_down",1));
        bot.angleMap.increment(bot.getDistance(),Gpad.getIncrement("dpad_right","dpad_left",1));

        adjustingConstants = Gpad.getToggleValue("b");
        if(Gpad.getRisingEdge("x")&&adjustingConstants){bot.oldPutConstant(bot.getDistance(),bot.launcher.flywheelToBallSpeedRatio,TrajectoryMath.ratio,bot.launcher.ratio);}

        //servoPos = gamepad1.left_trigger*20+30;

        //==============================OUTPUTS===================================\\

        //welcome to the now slightly contained mess

        if(bot.launchHandler.launchPhase== Bot.LaunchPhase.NULL){
            if(initSpinUpFlywheelInput){
                bot.launcher.resetPID();
            }
            if (spinUpFlywheelInput)
            {
                bot.spinFlywheelToTunedSpeed();
            }
            if (releaseTheBallsInput)
            {
                bot.launchHandler.initLaunch();
            }
        }
        else {
            if (stopTheBallsInput) {
                bot.launchHandler.stopLaunch();
            }
        }

        if(bot.launchHandler.launchPhase== Bot.LaunchPhase.NULL){
            if (intakeToggle)
            {
                bot.intake.setPower(1);
            }
            else bot.intake.stop();
        }

//        if(turretZeroInput){
//            bot.turret.zero();
//        }

        if(resetTurretPID){
            bot.turret.resetPID();
        }
        if(autoAimOn){
            bot.aimTurret();
        } else {
            bot.turret.setPower(0);
        }

        double launchAngle = bot.launcher.getAngle();

        bot.updateSpeedMeasure(follower.getPose().getAsVector());
        Bot.LaunchPhase launchPhase = bot.update();
//        if(!adjustingConstants){
        bot.updateConstants();
//        }

        //the time is 1700. this seems to work well but still might want to be tuned
        if (extendKickStand) {
            stand.extendStand(1700);
        }

        if (resetVision) {
            bot.resetCam.resetPos();
        }

//        telemetry.addData("starting iteration", releaseTheBallsInput);

        //========================TELEMETRY===========================\\

        //                     brace your eyes

        if(selections.get("telemetry")=="on"){


            telemetry.addData("left stick x",gamepad1.left_stick_x);
            telemetry.addData("left stick y",gamepad1.left_stick_y);
            telemetry.addData("right stick x",gamepad1.right_stick_x);
//            telemetry.addData("loop time",bot.launcher.launcherPIDF.times[0]-bot.launcher.launcherPIDF.times[1]);
            telemetry.addData("voltage sensor",voltageSensor.getVoltage());
//            if(bot.adjustingConstants){
            telemetry.addData("position",follower.getPose());
            telemetry.addData("turret position",bot.getTurretPos());
            telemetry.addData("intake power",bot.intake.getPower());
                telemetry.addData("loop time", TIME.getTime() - loopStartTime);
                loopStartTime = TIME.getTime();
                telemetry.addLine();
//            telemetry.addData("ljoystick y",gamepad1.left_stick_y);
//            telemetry.addData("ljoystick x",gamepad1.left_stick_x);
//            telemetry.addData("rjoystick x",gamepad1.right_stick_x);
                telemetry.addData("launch phase", launchPhase);
                telemetry.addData("intake power",bot.intake.getPower());
//            telemetry.addData("phase duration",bot.launchHandler.getElapsedTime());
//            telemetry.addData("gate is open",bot.intake.gateIsOpen());
                telemetry.addLine();
                telemetry.addData("radPerSec to VelRatio", bot.launcher.flywheelToBallSpeedRatio);
                telemetry.addData("velocity ratio", bot.launcher.ratio);
                telemetry.addData("height ratio", TrajectoryMath.ratio);
                telemetry.addData("distance",distance);
                if(gamepad1.a){
                    telemetry.addLine(bot.getConstantList());
                }else{
                    telemetry.addLine(bot.getConstantString());
                }
            telemetry.addLine("current distance");
            telemetry.addLine(bot.getConstantString());
//            telemetry.addLine("constant list");
//            telemetry.addLine(bot.getConstantList());

//            }
//            telemetry.addLine();
//            telemetry.addData("target ratio",bot.launcher.ratio);
//            telemetry.addData("target speed",bot.targetSpeed);
//            telemetry.addData("thing it should be",bot.launcher.betweenVel(velBounds[0],velBounds[1]));
//            telemetry.addData("actual speed", bot.launcher.getExitVel());
//            telemetry.addData("target flywheel speed",bot.targetSpeed*bot.launcher.flywheelToBallSpeedRatio);
//            telemetry.addData("actual flywheel speed",bot.launcher.getFlywheelEncoder().getVelocity());
//            telemetry.addData("target power",bot.targetSpeed/bot.launcher.getMaxPossibleExitVel());
//            telemetry.addLine();
//            telemetry.addData("velSquared for min angle", minAngleVelSquared);
//            telemetry.addData("velSquared for max angle", maxAngleVelSquared);
//            telemetry.addData("real max", Math.sqrt(maxAngleVelSquared));
//            telemetry.addData("minimum speed", velBounds[0]);
//            telemetry.addData("maximum speed", velBounds[1]);
//            telemetry.addData("average",(velBounds[0]+velBounds[1])/2);
//            telemetry.addLine();
            telemetry.addData("distance", distance);
//            telemetry.addData("in/sec scale", bot.launcher.getMaxPossibleExitVel());
//            telemetry.addData("rad/sec scale", bot.launcher.maxPossibleAngVel);
//            telemetry.addData("calculated power",(velBounds[0]+velBounds[1])/(2*bot.launcher.getMaxPossibleExitVel()));
//            telemetry.addData("actual power 1",bot.launcher.motor1.getPower());
//            telemetry.addData("actual power 2",bot.launcher.motor2.getPower());
            telemetry.addData("launchAngle", launchAngle);
//            telemetry.addLine();
//            double[] angles = TrajectoryMath.getAngles(vel,distance);
//            telemetry.addData("length", angles.length);

//            for (int i = 0; i < angles.length; i++)
//            {
//                telemetry.addData("angle " + String.valueOf(i), Math.toDegrees(angles[i]));
//            }
//
//            double deltaX = targetGoalPos[0] - follower.getPose().getX();
//            double deltaY = targetGoalPos[1] - follower.getPose().getY();
//            double tan = Math.atan(deltaY / deltaX);
//
//
//
//            currentMonitor.updateData();
//            currentMonitor.addTelemetry();
//
//
//            telemetry.addData("target goal pos x", targetGoalPos[0]);
//            telemetry.addData("target goal pos y", targetGoalPos[1]);
//            telemetry.addData("position", follower.getPose());
        }
        else{
            telemetry.addLine(logo);
            telemetry.addLine("press "+intakeButtonName+" to intake");
            telemetry.addLine("hold "+aimButtonName+" to aim the turret");
            telemetry.addLine("hold "+launchButtonName+" to spin up the flywheel");
            telemetry.addLine("and release it to release the balls");
            telemetry.addLine("if the turret isnt aiming right, try turning");
//            for(int i = 0; i<logo.length;i++){
//                telemetry.addLine(logo[i]);
//            }
        }
        //telemetry.addData("is auto clear",telemetry.isAutoClear());
        telemetry.updateSection();
        telemetry.updateSection("TURRET");
//        telemetry.updateSection("LAUNCHER");
//        telemetry.updateSection("PIDF");
//        telemetry.updateSection("BOT");
//        telemetry.updateSection("TURRET");
//        telemetry.updateSection();
//        telemetry.updateSection();
        telemetry.display();
        telemetry.clearAll();

    }
    @Override
    public void stop(){
        bot.intake.unKick();
        bot.servoController.pwmDisable();
    }
}
