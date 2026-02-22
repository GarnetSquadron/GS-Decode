package org.firstinspires.ftc.teamcode.HardwareControls;

import static org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions.Hood.turretOffset;
import static org.firstinspires.ftc.teamcode.HardwareControls.Launcher.flywheelToBallSpeedRatio;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.Telemetry.SectTelemetryAdder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.InterpolationStuff.StepApproximation;
import org.firstinspires.ftc.teamcode.PurelyCalculators.TrajectoryMath;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;

import java.util.Arrays;
import java.util.Map;

public class Bot
{
    /**
     * used to save the current position for when teleop starts.
     */
    public static Pose[] currentPos = new Pose[10];
    static {
        Arrays.fill(currentPos,FieldDimensions.botTouchingRedGoal);
    }
    public static double currentTurretPosition = 0;
    public static boolean redSide = true;
    public Lights lights;
    public StepApproximation radToInchRatioMap;
    public StepApproximation velRangeRatioMap;
    public StepApproximation heightRatioMap;
    public StepApproximation velMap;
    public StepApproximation angleMap;
    VoltageSensor voltageSensor;

    //    public boolean adjustingConstants = false;
    public void oldPutConstant(double distance, double radToInch, double velRatio, double height){
        radToInchRatioMap.put(distance,radToInch);
        velRangeRatioMap.put(distance,velRatio);
        heightRatioMap.put(distance,height);
    }
    public void putConstant(double distance, double velocity,double angle){
        velMap.put(distance,velocity);
        angleMap.put(distance,angle);
    }
    public String getConstantList(){
        String ret = "";
        for(Map.Entry<Double,Double> entry:velMap.m_map.entrySet()){
            double key = entry.getKey();
            ret+=getConstantString(key);
        }
        return ret;
    }
    public String getConstantString(double key){
        return"\n"+"dist: "+key+"\n"+"vel: "+velMap.get(key)+"\n"+"angle: "+ angleMap.get(key)+"\n\n\n";
    }
    public String getConstantString(){
        return"\n"+"dist: "+getDistance()+"\n"+"vel: "+velMap.get(getDistance())+"\n"+"angle: "+ angleMap.get(getDistance())+"\n\n\n";
    }
    public Launcher launcher;
    public Intake intake;
    public Turret turret;
    public Follower follower;
    public LaunchHandler launchHandler;
    double[] targetGoalPos;
    public ServoController servoController;
    public double targetSpeed = 0;
    SectTelemetryAdder telemetry;
    public Bot(HardwareMap hardwareMap, double[] targetGoalPos){
        //Arrays.fill(currentPos,FieldDimensions.botTouchingRedGoal);
        lights = new Lights(hardwareMap);
        voltageSensor = hardwareMap.voltageSensor.get("Control Hub");
        this.targetGoalPos = targetGoalPos;
        launcher = new Launcher(hardwareMap);
        intake = new Intake(hardwareMap);
        launchHandler = new LaunchHandler();
        turret = launcher.turret;
        follower = CompConstants.createFollower(hardwareMap);
        servoController = hardwareMap.get(ServoController.class, "Control Hub");
        telemetry = new SectTelemetryAdder("BOT");
        double stepSize = 1;
//        radToInchRatioMap = new StepApproximation(stepSize," rad to inch");
//        rangeRatioMap = new StepApproximation(stepSize," range ratio");
//        heightRatioMap = new StepApproximation(stepSize," height ratio");
        radToInchRatioMap = new StepApproximation(stepSize,"rad to inch");
        velRangeRatioMap = new StepApproximation(stepSize,"vel ratio");
        heightRatioMap = new StepApproximation(stepSize,"height ratio");
        velMap = new StepApproximation(stepSize,"velocity");
        angleMap = new StepApproximation(stepSize,"angle");
        double adjustment = 12.2;//moved the target position back
        oldPutConstant(36+adjustment,1.35,0.3,0.3);
//        oldPutConstant(42+adjustment, 1.45,0.4,0.3);
//        oldPutConstant(48+adjustment,1.3375,0.5,0.3);
//        oldPutConstant(54+adjustment,1.3316,0.5,0.3);
//        oldPutConstant(60+adjustment,1.3261,0.6,0.1);
//        oldPutConstant(66+adjustment,1.2199,0.6,0.4);
//        oldPutConstant(72+adjustment,1.313,0.5,0.1);
//        oldPutConstant(78+adjustment,1.3121,0.5,0.2);
        //old values(I tuned them when the distances were measured from a different point so I needed
        // to add adjustment to the distances)
//        putConstant(36+adjustment,257,55);//48
//        putConstant(42+adjustment,251,55);//54
//        putConstant(48+adjustment,252,55);//60
//        putConstant(54+adjustment,257,55);//66
//        putConstant(60+adjustment,274,55);//72
//        putConstant(66+adjustment,275,55);//78
//        putConstant(72+adjustment,275,55);//84
//        putConstant(78+adjustment,277,52);//
//        putConstant(84+adjustment,274,55);//
//        //putConstant(140,274,55);//140
//        //putConstant(146,274,55);//146
//        putConstant(138,274,55);
//        putConstant(144,274,55);
//        putConstant(132,274,55);
//        putConstant(138,274,55);
//        putConstant(140+adjustment,274,55);//152
//        putConstant(146+adjustment,274,55);//158
        //replacing most of them with the new ones
        putConstant(54,255,55);
        putConstant(60,255,55);
        putConstant(66,260,52);
        putConstant(72,285,49);
        putConstant(84,285,48);
        putConstant(90,290,50);
        putConstant(96,300,55);
        putConstant(96,300,55);
        putConstant(132,340,55);
        putConstant(130,325,55);
//        putConstant(138,340,55);
//        putConstant(144,340,45);
//        putConstant(150,350,43);
    }
    public Bot(HardwareMap hardwareMap, double[] targetGoalPos,double turretPosition){
        this(hardwareMap,targetGoalPos);
        turret.turretRot.getEncoder().setPosition(turretPosition);
    }
    public double getDistance(){
        return Math.hypot(targetGoalPos[0]-getTurretPos().getX(), targetGoalPos[1]-getTurretPos().getY());
    }
    public double getDistance(Vector coords){
        return Math.hypot(targetGoalPos[0]-coords.getXComponent(), targetGoalPos[1]-coords.getYComponent());
    }
    public double getMinAngleVelSquared(double distance){
        return TrajectoryMath.getVelSquared(distance, Math.toRadians(RobotDimensions.Hood.minAngle));
    }
    public double getMaxAngleVelSquared(double distance){
        return TrajectoryMath.getVelSquared(distance, Math.toRadians(RobotDimensions.Hood.maxAngle));
    }
    public double[] getVelBounds(double distance) {
        return TrajectoryMath.getVelBoundsFromVelSquaredBounds(getMinAngleVelSquared(distance),getMaxAngleVelSquared(distance),distance);
    }

    public void updateConstants(double distance){
        flywheelToBallSpeedRatio = radToInchRatioMap.get(distance);
        launcher.ratio = velRangeRatioMap.get(distance);
        TrajectoryMath.ratio = heightRatioMap.get(distance);
    }
    public void updateConstants(){
        updateConstants(getDistance());
    }
    public boolean spinFlywheelToTunedSpeed(){
        return launcher.spinFlyWheelWithinRange(velMap.get(getDistance()));
    }
    public void spinFlywheelToTunedSpeed(Vector coords){
        launcher.spinFlyWheelWithinRange(velMap.get(getDistance(coords)));
    }
    //idle power for flywheel
    public void idleFlywheel(){
        launcher.spinFlyWheelWithinRange(255);//minimum speed
    }

    /**
     * for if you want to see it before the start of the opmode
     */
    public void initTelemetry(){
        telemetry.addLine("hi");
    }
    double loopStartTime = 0;
    public LaunchPhase update(){
//        double[] velBounds = getVelBounds(getDistance());
//        telemetry.addData("loop time", TIME.getTime() - loopStartTime);
//        loopStartTime = TIME.getTime();
//        telemetry.addArray("VEL BOUNDS",velBounds);
//        telemetry.addData("flywheelToBallSpeedRatio",flywheelToBallSpeedRatio);
//        telemetry.addData("flywheelToBallSpeedRatio",flywheelToBallSpeedRatio);
        updateCurrentPos();
        return launchHandler.update();
    }
    public void updateCurrentPos(){
        currentPos = LauncherPIDF.updateArray(currentPos,follower.getPose());
        telemetry.addArray("positions",currentPos);
        currentTurretPosition = turret.turretRot.getEncoder().getPos();
        telemetry.addData("turret pos",currentTurretPosition);
    }
    public void updateSpeedMeasure(Vector position){
        double[] velBounds = getVelBounds(getDistance(position));
        launcher.updateSpeedMeasurements(targetSpeed);
    }
    public void aimTurret(Pose botPose){
        turret.aimTowardsGoal(targetGoalPos, new double[] {botPose.getX(), botPose.getY()},botPose.getHeading());
    }
    public void aimTurret(){
        aimTurret(getTurretPos());
    }
    public Pose getTurretPos(){
        double[] offset = Pose.polarToCartesian(-turretOffset,follower.getHeading());
        return follower.getPose().plus(new Pose(offset[0],offset[1]));
    }
    public enum LaunchPhase
    {
        NULL,
        SPINNING_UP,
        GATE_OPENING,
        RELEASING_BALLS,
        KICKING_SERVO,
        SHUTDOWN
    }
    public class LaunchHandler
    {
        void powerIntake(){
            intake.setPower(/*pauseBetweenShots()?0.7:*/1);
        }
        public LaunchPhase launchPhase = LaunchPhase.NULL;
        public boolean isPausedToSpinUp = false;
        public double pauseStartTime = -1;
        double phaseStartTime = -1;

        static final double REQUIRED_STABLE_TIME = 0.5; // Needs tuning

        double stableSince = -1;
        public LaunchHandler(){

            phaseStartTime = -1;

        }
        public double getElapsedTime(){
            return TIME.getTime()- phaseStartTime;
        }
        public void initLaunch(){
            launchPhase = LaunchPhase.SPINNING_UP;
            intake.closeGate();
            phaseStartTime = TIME.getTime();
            isPausedToSpinUp = false;
        }
        public void stopLaunch(){
            launchPhase = LaunchPhase.SHUTDOWN;
        }

        public boolean pauseBetweenShots(){
            return getDistance()>120;
        }
        public boolean isUpToSpeed(){
            return pauseBetweenShots()?launcher.PIDF.isStable():launcher.PIDF.isStable();
        }

        /**
         * if we're in pause between shots mode, then check if the current speed is enough. otherwise check the average
         * of the past few to reduce noise, with the price of less reaction time
         * I know I hate my code too.
         * @return
         */
        public boolean shouldSpinUp(){
            return pauseBetweenShots()? !launcher.PIDF.isStable():/*!launcher.PIDF.closeToTarget()*/false;
        }
        public void displayBatteryInLeftLight(){
            Light.Color color;
            switch ((int)Math.floor(voltageSensor.getVoltage())){
                case 10:
                    color = Light.Color.RED;
                    break;
                case 11:
                    color = Light.Color.Orange;
                    break;
                case 12:
                    color = Light.Color.Yellow;
                    break;
                case 13:
                    color = Light.Color.Sage;
                    break;
                case 14:
                    color = Light.Color.Green;
                    break;
                default:
                    color = Light.Color.BLUE;
            }
            lights.leftLight.setColor(color);
        }
        public LaunchPhase update(){
            targetSpeed = velMap.get(getDistance());//launcher.betweenVel(velBounds[0],velBounds[1]);
            launcher.setAngle(Math.toRadians(angleMap.get(getDistance())));
//            telemetry.addData("launchPhase",launchPhase);
//            telemetry.addData("is pausing",pauseBetweenShots());


            boolean velInRange = false;
//            lights.leftLight.setColor(!launcher.PIDF.hasStabilized()? Light.Color.Orange:Light.Color.Green);
            lights.rightLight.setColor(launcher.PIDF.isStable()? Light.Color.Orange:Light.Color.Green);
            displayBatteryInLeftLight();
//            telemetry.addLine("start of loop");
            // basic idea is that the sequence will pause if the flywheel is not up to speed, and then attempt to get back up to speed
            //once you get to kicking the servo its far gone imo
            if(pauseBetweenShots()&&launchPhase!=LaunchPhase.NULL&&launchPhase!=LaunchPhase.KICKING_SERVO&&launchPhase!=LaunchPhase.SHUTDOWN){
                //velInRange = launcher.launcherPIDF.hasStabilized();
//                telemetry.addData("vel has stabilized",launcher.launcherPIDF.hasStabilized());
//                telemetry.addData("vel has destabilized",launcher.launcherPIDF.hasDestabilized());

                if(shouldSpinUp()&&launchPhase!=LaunchPhase.SPINNING_UP&&!isPausedToSpinUp){
                    isPausedToSpinUp = true;
                    pauseStartTime = TIME.getTime();
                    intake.setPower(pauseBetweenShots()?-0.5:0);
                }
                if(isPausedToSpinUp){
                    launcher.spinFlyWheelWithinRange(targetSpeed);
//                    telemetry.addLine("paused");
                    intake.setPower(0);
                    if(isUpToSpeed()){
                        isPausedToSpinUp = false;
                        //change the phase start time so that there is the correct time remaining in that phase.
                        phaseStartTime = TIME.getTime();
                        if(launchPhase==LaunchPhase.RELEASING_BALLS){powerIntake();}
                    }
                    //if paused, do not carry out the instructions in the switch case
                    return launchPhase;
                }
            }
//            telemetry.addLine("going to switch case");
            switch (launchPhase){
                case NULL: {
                    break;
                }
                case SPINNING_UP: {
                    intake.stop();

                    boolean stable = launcher.spinFlyWheelWithinRange(targetSpeed);

                    if (stable) {
                        if (stableSince < 0) stableSince = TIME.getTime();
                    } else {
                        stableSince = -1;
                    }

                    boolean minSpinTimePassed = getElapsedTime() > 0.30;

                    if (minSpinTimePassed && stableSince > 0 && (TIME.getTime() - stableSince) > REQUIRED_STABLE_TIME) {
                        launchPhase = LaunchPhase.GATE_OPENING;
                        phaseStartTime = TIME.getTime();
//                        launcher.fightMomentumLoss();
                        stableSince = -1;
                    }
                    break;
                }

                case GATE_OPENING:{
                    intake.stop();
                    intake.openGate();
                    launcher.spinFlyWheelWithinRange(targetSpeed);
                    if (getElapsedTime() > 0.5)
                    {
                        launchPhase = LaunchPhase.RELEASING_BALLS;
                        phaseStartTime = TIME.getTime();
                        powerIntake();
                    }
                    break;
                }
                case RELEASING_BALLS:{
                    intake.openGate();
                    powerIntake();
                    launcher.spinFlyWheelWithinRange(targetSpeed);
//                    telemetry.addLine("releasing balls!");
                    if(getElapsedTime() > 0.4){
                        launchPhase = LaunchPhase.KICKING_SERVO;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case KICKING_SERVO:{
                    intake.openGate();
                    intake.kickBall();
                    launcher.spinFlyWheelWithinRange(targetSpeed);
                    if(getElapsedTime()>0.5){
                        launchPhase = LaunchPhase.SHUTDOWN;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case SHUTDOWN:{
                    intake.unKick();
                    intake.stop();
                    intake.closeGate();
                    launcher.setPower(0);
                    launchPhase = LaunchPhase.NULL;
                    break;
                }
            }
            return launchPhase;
        }
    }
}
