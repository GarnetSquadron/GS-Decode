package org.firstinspires.ftc.teamcode.OpModes.teleops;

import static org.firstinspires.ftc.teamcode.PurelyCalculators.AngleFinder.g;
import static org.firstinspires.ftc.teamcode.PurelyCalculators.AngleFinder.targetHeight;
import static org.firstinspires.ftc.teamcode.logo.Logo.logo;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.SettingSelectorOpMode;
import org.firstinspires.ftc.teamcode.PurelyCalculators.AngleFinder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.ExtraMath;
import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import kotlin.Pair;


@TeleOp(name = "Main teleop")
public class MainTeleop extends SettingSelectorOpMode
{
    VoltageSensor voltageSensor;
    private static final Logger log = LoggerFactory.getLogger(MainTeleop.class);
    public static Follower follower;
    public DcMotorEx lf,rf,lb,rb,intakeMotor;
    public double loopStartTime = 0;
    Bot bot;
    double rotation = 0;
    double servoPos = 0.5;
    double launcherPower = 0.6;
    double[] targetGoalPos;
    double distance = 100;
    double vel = 400;
    boolean inRange = true;
    //so far it seems that the inches per second match up pretty well with the rad per second. what a miracle
    double velToDistRatio = 4;
    double a = 386.09*386.09/4;
    //double launchAngle = 40;
    boolean headlessDriveOn;
    String intakeButtonName,launchButtonName,aimButtonName,stopLaunchName;
    static HashMap<String,String> selections = new HashMap<String, String>(){{put("personal config","Nathan");put("position","testing");}};

    double startWheelAngle;
    //double b = 386.09*targetHeight-vel*vel;

    ColorSensor sensor;
    double totalMaxCurrent = 0,lfMax = 0,rfMax = 0, lbMax = 0,rbMax = 0,intakeMax = 0, launcherMax1 = 0,launcherMax2 = 0;

    /**
     * gets the velocity given an angle
     * @param dist distance between robot and goal in inches
     * @param launchAngle angle of the initial velocity vector that the ball comes out of the launcher with with respect to the horizontal
     * @return
     */
    public double getVelSquared(double dist, double launchAngle) {
        double c = dist*dist+targetHeight*targetHeight;
        double tSquare = 2 * (dist*Math.tan(launchAngle)- targetHeight)/g;
        return a*tSquare+c/tSquare+g*targetHeight;
    };
    public double getMinVelSquared(double x,double y){
        return g*(y+Math.sqrt(y*y+x*x));
    }
    public double[] getVelBoundsFromVelSquaredBounds(double minAngleVelSquared,double maxAngleVelSquared){
        //if(maxAngleVelSquared<0){ maxAngleVelSquared = bot.launcher.getMaxPossibleExitVel();}
        if(minAngleVelSquared<0){ minAngleVelSquared = getMinVelSquared(distance,targetHeight);}
        //make sure max isn't too fast
        double maxAngleVel = (maxAngleVelSquared<0)?bot.launcher.getMaxPossibleExitVel():Math.min(Math.sqrt(maxAngleVelSquared), bot.launcher.getMaxPossibleExitVel());
        double minAngleVel = Math.min(Math.sqrt(minAngleVelSquared), bot.launcher.getMaxPossibleExitVel());
        if(minAngleVel<maxAngleVel){// return the bounds such that the smaller one is the first one
            return new double[]{minAngleVel,maxAngleVel};
        }else{
            return new double[]{maxAngleVel,minAngleVel};
        }
    }
    //DoubleUnaryOperator distToVel =  (dist)-> Math.sqrt(distToVelSquared.applyAsDouble(dist));//Math.sqrt(g*(AngleFinder.targetHeight+0.1+Math.sqrt((AngleFinder.targetHeight+0.1)*(AngleFinder.targetHeight+0.1)+dist*dist)))+20;//(dist*dist/10) * velToDistRatio;
    GoBildaPinpointDriver pinpointDriver;
    IMU imu;

    BetterControllerClass Gpad;

    public MainTeleop()
    {
        //settings
        super(new Pair[]{
                new Pair(
                        new String[]{"red","blue"},"color"
                ),
                new Pair(
                        new String[]{"goal","tiny triangle","testing"},"position"
                ),
                new Pair(
                        new String[]{"James","Nathan","Charlie","DJ"}, "personal config"
                ),
                new Pair(
                        new String[]{"on","off"},"telemetry"
                )
        }, selections
        );
    }

    @Override
    public void init()
    {

        voltageSensor = hardwareMap.voltageSensor.get("Control Hub");
        bot = new Bot(hardwareMap);

        Gpad = new BetterControllerClass(gamepad1);

        //set up follower
        follower = CompConstants.createFollower(hardwareMap);
//        PanelsConfigurables.INSTANCE.refreshClass(this);

        //follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
//        follower.setStartingPose(new Pose(FieldDimensions.goalPositionRed[0], 0, 0));
        bot.intake.closeGate();
        bot.intake.unKick();
        //bot.intake.openGate();


        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();
        //sensor = hardwareMap.get(ColorSensor.class, "sensor");
        lf = hardwareMap.get(DcMotorEx.class,"lf");
        rf = hardwareMap.get(DcMotorEx.class,"rf");
        lb = hardwareMap.get(DcMotorEx.class,"lb");
        rb = hardwareMap.get(DcMotorEx.class,"rb");
        intakeMotor = hardwareMap.get(DcMotorEx.class,"intakeMotor");
        //FlywheelMotor1 = hardwareMap.get(DcMotorEx.class, "launcherMotor1");
        //FlywheelMotor2 = hardwareMap.get( DcMotorEx.class, "launcherMotor2");

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
        selections = super.selections;// now we update the static field

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
                intakeButtonName = "left_bumper";
                launchButtonName = "right_trigger";
                aimButtonName = "left_trigger";
                stopLaunchName = "right_bumper";
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
                targetGoalPos = FieldDimensions.goalPositionRed;
                switch (selections.get("position"))
                {
                    case "goal":
                        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
                        break;
                    case "tiny triangle":
                        follower.setStartingPose(FieldDimensions.botOnTinyTriangleBlueSide);
                        break;
                    case "testing":
//                        follower.setStartingPose(new Pose(FieldDimensions.goalPositionRed[0], targetGoalPos[1]-70, -Math.PI/2));
                        follower.setStartingPose(new Pose(81, 104, Math.PI));
                        break;
                }
                break;
            }
            case "blue":
            {
                targetGoalPos = FieldDimensions.goalPositionBlue;
                switch (selections.get("position"))
                {
                    case "goal":
                        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
                        break;
                    case "tiny triangle":
                        follower.setStartingPose(FieldDimensions.botOnTinyTriangleBlueSide);
                        break;
                    case "testing":
                        follower.setStartingPose(new Pose(targetGoalPos[0], 0, 0));
                        break;
                }
                break;
            }

        }
        startWheelAngle = Math.toDegrees(bot.launcher.getFlywheelEncoder().getPos());

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

        follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, !headlessDriveOn);
//        move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        Gpad.update();

        //I wanted to find a better way, but this seems like the best option for organizing the button inputs
        //==============================INPUTS====================================\\
        boolean intakeToggle = Gpad.getCurrentValue(intakeButtonName);
        boolean spinUpFlywheelInput = Gpad.getCurrentValue(launchButtonName);
        boolean releaseTheBallsInput = Gpad.getFallingEdge(launchButtonName);
        boolean stopTheBallsInput = Gpad.getRisingEdge(stopLaunchName);
        boolean turretZeroInput = gamepad1.x;
        boolean autoAimOn = Gpad.getCurrentValue(aimButtonName);

        distance=Math.hypot(targetGoalPos[0]-follower.getPose().getX(), targetGoalPos[1]-follower.getPose().getY());
        double minAngleVelSquared = getVelSquared(distance, Math.toRadians(RobotDimensions.Hood.minAngle));
        double maxAngleVelSquared = getVelSquared(distance, Math.toRadians(RobotDimensions.Hood.maxAngle));
        double[] velBounds = getVelBoundsFromVelSquaredBounds(minAngleVelSquared,maxAngleVelSquared);
        bot.launcher.flywheelToBallSpeedRatio +=gamepad1.dpadRightWasPressed()?0.1:(gamepad1.dpadLeftWasPressed()?-0.1:0); //Math.hypot(FieldDimensions.goalPositionBlue[0]-follower.getPose().getX(), FieldDimensions.goalPositionBlue[1]-follower.getPose().getY());
        bot.launcher.maxPossibleAngVel +=gamepad1.dpadUpWasPressed()?1:(gamepad1.dpadDownWasPressed()?-1:0); //Math.hypot(FieldDimensions.goalPositionBlue[0]-follower.getPose().getX(), FieldDimensions.goalPositionBlue[1]-follower.getPose().getY());
        bot.launcher.ratio+=gamepad1.aWasPressed()?0.1:(gamepad1.yWasPressed()?-0.1:0);

        //servoPos = gamepad1.left_trigger*20+30;

        //==============================OUTPUTS===================================\\

        //welcome to the now slightly contained mess

        if(bot.launchHandler.launchPhase== Bot.LaunchPhase.NULL){
            if (releaseTheBallsInput)
            {
                bot.launchHandler.initLaunch();
            }

            if (spinUpFlywheelInput)
            {
                telemetry.addData("speed", bot.launcher.spinFlyWheelWithinRange(velBounds[0], velBounds[1]));
                //bot.intake.closeGate();
            }
        }
        else {
            if(stopTheBallsInput){
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

        if(turretZeroInput){
            bot.turret.zero();
        }

        if(autoAimOn){
            rotation = bot.turret.aimTowardsGoal(targetGoalPos, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading() /*Math.toRadians(imu.getRobotYawPitchRollAngles().getYaw())*/);

        } else {
            bot.turret.setPower(0);
        }

        // this should be the initial speed of the ball as exits the launcher
        vel = bot.launcher.getExitVel();

        //we get the servo position based on the velocity we see, not the velocity we want
        double launchAngle = bot.launcher.aimServo(distance, vel);
        launcherPower = 0.9;

        Bot.LaunchPhase launchPhase = bot.update(velBounds[0],velBounds[1]);

//        telemetry.addData("starting iteration", releaseTheBallsInput);

        //========================TELEMETRY===========================\\
        if(selections.get("telemetry")=="on"){


//            telemetry.addData("left stick x",gamepad1.left_stick_x);
//            telemetry.addData("left stick y",gamepad1.left_stick_y);
//            telemetry.addData("right stick x",gamepad1.right_stick_x);
            telemetry.addData("voltage sensor",voltageSensor.getVoltage());
            telemetry.addData("loop time",TIME.getTime()-loopStartTime);
            loopStartTime = TIME.getTime();
            telemetry.addLine();

            telemetry.addData("paused",bot.launchHandler.isPausedToSpinUp);
            if (bot.launchHandler.isPausedToSpinUp){
                telemetry.addLine("paused");
            }
            telemetry.addData("ljoystick y",gamepad1.left_stick_y);
            telemetry.addData("ljoystick x",gamepad1.left_stick_x);
            telemetry.addData("rjoystick x",gamepad1.right_stick_x);
            telemetry.addData("launch phase",launchPhase);
            telemetry.addData("phase duration",bot.launchHandler.getElapsedTime());
            telemetry.addData("gate is open",bot.intake.gateIsOpen());
            telemetry.addData("radPerSec to VelRatio",bot.launcher.flywheelToBallSpeedRatio);
            telemetry.addLine();
            telemetry.addData("target ratio",bot.launcher.ratio);
            telemetry.addData("target speed",bot.targetSpeed);
            telemetry.addData("thing it should be",bot.launcher.betweenVel(velBounds[0],velBounds[1]));
            telemetry.addData("actual speed", bot.launcher.getExitVel());
            telemetry.addData("target flywheel speed",bot.targetSpeed*bot.launcher.flywheelToBallSpeedRatio);
            telemetry.addData("target power",bot.targetSpeed/bot.launcher.getMaxPossibleExitVel());

//            telemetry.addData("position", selections.get("position"));
//            telemetry.addData("color", selections.get("color"));
//            telemetry.addData("pos",follower.getPose());
//            telemetry.addData("time squared for 30 deg", 2 * (distance*Math.tan(Math.toRadians(RobotDimensions.Hood.minAngle)) - targetHeight)/g);
//            telemetry.addData("distance",distance);
//            telemetry.addData("height",targetHeight);
//            telemetry.addData("tangent of 30",Math.tan(Math.toRadians(RobotDimensions.Hood.minAngle)));
//            telemetry.addData("inches for 30 deg", distance*Math.tan(Math.toRadians(RobotDimensions.Hood.minAngle)) - targetHeight);
//            telemetry.addData("theoretical minimum possible vel",getMinVelSquared(distance,targetHeight));
            telemetry.addLine();
//            telemetry.addData("launching balls", bot.launchHandler.launchingBalls);
//            telemetry.addData("releasing them", bot.launchHandler.releaseBalls);
            telemetry.addData("rad/sec", vel);
            telemetry.addData("RPM", (vel / ExtraMath.Tau) * 60);
            telemetry.addData("velSquared for min angle", minAngleVelSquared);
            telemetry.addData("velSquared for max angle", maxAngleVelSquared);
            telemetry.addData("real max", Math.sqrt(maxAngleVelSquared));
            telemetry.addData("minimum speed", velBounds[0]);
            telemetry.addData("maximum speed", velBounds[1]);
            telemetry.addData("average",(velBounds[0]+velBounds[1])/2);
            //telemetry.addData("in range",velBounds[0] < getFlywheelEncoder().getVelocity() && getFlywheelEncoder().getVelocity() > velBounds[1]);
            telemetry.addLine();
            telemetry.addData("distance", distance);
            telemetry.addData("in/sec scale", bot.launcher.getMaxPossibleExitVel());
            telemetry.addData("rad/sec scale", bot.launcher.maxPossibleAngVel);
            telemetry.addData("calculated power",(velBounds[0]+velBounds[1])/(2*bot.launcher.getMaxPossibleExitVel()));
            telemetry.addData("actual power 1",bot.launcher.motor1.getPower());
            telemetry.addData("actual power 2",bot.launcher.motor2.getPower());
            telemetry.addData("launchAngle", launchAngle);
            telemetry.addLine();

            double[] angles = AngleFinder.getAngles(vel,distance);
            telemetry.addData("length", angles.length);

            for (int i = 0; i < angles.length; i++)
            {
                telemetry.addData("angle " + String.valueOf(i), Math.toDegrees(angles[i]));
            }
//            telemetry.addData("targetGoalX", targetGoalPos[0]);
//            telemetry.addData("targetGoalY", targetGoalPos[1]);
//
            double deltaX = targetGoalPos[0] - follower.getPose().getX();
            double deltaY = targetGoalPos[1] - follower.getPose().getY();
            double tan = Math.atan(deltaY / deltaX);


//            telemetry.addLine();
//            telemetry.addData("bot heading", follower.getPose().getHeading());
//            telemetry.addData("tan", tan);
//            telemetry.addLine("radians:");
//            telemetry.addData("input angle", tan - follower.getPose().getHeading() + Math.PI);
//            telemetry.addData("supposed output angle", bot.turret.getRotation(tan - follower.getPose().getHeading() + Math.PI));
//            //telemetry.addData("actual output angle", bot.turret.turretRot.getTargetPosition());
//            telemetry.addLine();
//            telemetry.addLine("degrees:");
//            telemetry.addData("input angle", Math.toDegrees(tan - follower.getPose().getHeading()) + 180);
//            telemetry.addData("modded input", Math.toDegrees(bot.turret.angleMod(tan - follower.getPose().getHeading() + Math.PI)));
//            telemetry.addData("supposed output angle", Math.toDegrees(bot.turret.getRotation(tan - follower.getPose().getHeading() + Math.PI)));
//            //telemetry.addData("actual output angle", Math.toDegrees(bot.turret.turretRot.getTargetPosition()));
//            telemetry.addLine();
//            telemetry.addData("real angle", rotation);
//            telemetry.addData("deltaX", deltaX);
//            telemetry.addData("deltaY", deltaY);
//            telemetry.addData("deltaY/deltaX", deltaY / deltaX);
//            telemetry.addData("atangent", tan);
//            telemetry.addLine();


            double lfCurrent = lf.getCurrent(CurrentUnit.MILLIAMPS);
            double rfCurrent = rf.getCurrent(CurrentUnit.MILLIAMPS);
            double lbCurrent = lb.getCurrent(CurrentUnit.MILLIAMPS);
            double rbCurrent = rb.getCurrent(CurrentUnit.MILLIAMPS);
            double intakeCurrent = intakeMotor.getCurrent(CurrentUnit.MILLIAMPS);
            double launcher1Current = bot.launcher.motor1.getCurrentMilliamps();
            double launcher2Current = bot.launcher.motor2.getCurrentMilliamps();
            double totalCurrent = lfCurrent+rfCurrent+lbCurrent+rbCurrent+intakeCurrent+launcher1Current+launcher2Current;
            totalMaxCurrent = Math.max(totalMaxCurrent,totalCurrent);
            lfMax = Math.max(lfCurrent,lfMax);
            rfMax = Math.max(rfCurrent,rfMax);
            lbMax = Math.max(lbCurrent,lbMax);
            rbMax = Math.max(rbCurrent,rbMax);
            intakeMax = Math.max(intakeCurrent,intakeMax);
            launcherMax1 = Math.max(launcher1Current, launcherMax1);
            launcherMax2 = Math.max(launcher2Current, launcherMax2);

            telemetry.addLine("------------current draw in milliamps----------");
            telemetry.addData("lf", lfCurrent);
            telemetry.addData("rf", rfCurrent);
            telemetry.addData("lb", lbCurrent);
            telemetry.addData("rb", rbCurrent);
            telemetry.addData("intake", intakeCurrent);
            telemetry.addData("launcherMotor1", launcher1Current);
            telemetry.addData("launcherMotor2", launcher2Current);
            telemetry.addData("total current",totalCurrent);
            telemetry.addData("max total current reached", totalMaxCurrent);
            telemetry.addData("max lf",lfMax);
            telemetry.addData("max rf",rfMax);
            telemetry.addData("max lb",lbMax);
            telemetry.addData("max rb",rbMax);
            telemetry.addData("max intake",intakeMax);
            telemetry.addData("max launcher motor 1",launcherMax1);
            telemetry.addData("max launcher motor 2",launcherMax2);

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
            telemetry.addData("target goal pos x", targetGoalPos[0]);
            telemetry.addData("target goal pos y", targetGoalPos[1]);
            telemetry.addData("position", follower.getPose());//TODO: check this, I think that the position is not getting updated, its possible the pinpoint isn't connected well or something
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
        telemetry.update();
        telemetry.clear();

    }
    @Override
    public void stop(){
        bot.intake.unKick();
        bot.servoController.pwmDisable();
    }
}
