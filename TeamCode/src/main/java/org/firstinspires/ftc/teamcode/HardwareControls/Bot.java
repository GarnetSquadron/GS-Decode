package org.firstinspires.ftc.teamcode.HardwareControls;

import static org.firstinspires.ftc.teamcode.HardwareControls.Launcher.flywheelToBallSpeedRatio;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.OpModes.SectTelemetryAdder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.InterpolationStuff.StepApproximation;
import org.firstinspires.ftc.teamcode.PurelyCalculators.TrajectoryMath;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;

import java.util.Map;

public class Bot
{
    public Lights lights;
    public StepApproximation radToInchRatioMap;
    public StepApproximation velRangeRatioMap;
    public StepApproximation heightRatioMap;
    public boolean adjustingConstants = false;
    public void putConstant(double distance,double radToInch,double velRatio,double height){
        radToInchRatioMap.put(distance,radToInch);
        velRangeRatioMap.put(distance,velRatio);
        heightRatioMap.put(distance,height);
    }
    public String getConstantList(){
        String ret = "";
        for(Map.Entry<Double,Double> entry:radToInchRatioMap.m_map.entrySet()){
            double key = entry.getKey();
            ret+=key+"\n"+entry.getValue()+"\n"+ velRangeRatioMap.get(key)+"\n"+ heightRatioMap.get(key)+"\n\n\n";
        }
        return ret;
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
        lights = new Lights(hardwareMap);
        this.targetGoalPos = targetGoalPos;
        launcher = new Launcher(hardwareMap);
        intake = new Intake(hardwareMap);
        launchHandler = new LaunchHandler();
        turret = launcher.turret;
        follower = CompConstants.createFollower(hardwareMap);
        servoController = hardwareMap.get(ServoController.class, "Control Hub");
        telemetry = new SectTelemetryAdder("BOT");
        double stepSize = 6;
//        radToInchRatioMap = new StepApproximation(stepSize," rad to inch");
//        rangeRatioMap = new StepApproximation(stepSize," range ratio");
//        heightRatioMap = new StepApproximation(stepSize," height ratio");
        radToInchRatioMap = new StepApproximation(stepSize,"rad to inch");
        velRangeRatioMap = new StepApproximation(stepSize,"vel ratio");
        heightRatioMap = new StepApproximation(stepSize,"height ratio");
        double adjustment = 6.708;
        putConstant(36+adjustment,1.35,0.3,0.3);
        putConstant(42+adjustment, 1.45,0.4,0.3);
        putConstant(48+adjustment,1.3375,0.5,0.3);
        putConstant(54+adjustment,1.3316,0.5,0.3);
        putConstant(60+adjustment,1.3261,0.6,0.1);
        putConstant(66+adjustment,1.2199,0.6,0.4);
        putConstant(72+adjustment,1.313,0.5,0.1);
        putConstant(78+adjustment,1.3121,0.5,0.2);
    }
    public double getDistance(){
        return Math.hypot(targetGoalPos[0]-follower.getPose().getX(), targetGoalPos[1]-follower.getPose().getY());
    }
    public double getMinAngleVelSquared(){
        return TrajectoryMath.getVelSquared(getDistance(), Math.toRadians(RobotDimensions.Hood.minAngle));
    }
    public double getMaxAngleVelSquared(){
        return TrajectoryMath.getVelSquared(getDistance(), Math.toRadians(RobotDimensions.Hood.maxAngle));
    }
    public double[] getVelBounds() {
        return TrajectoryMath.getVelBoundsFromVelSquaredBounds(getMinAngleVelSquared(),getMaxAngleVelSquared(),getDistance());
    }

    public void updateConstants(){
        double distance = getDistance();
        flywheelToBallSpeedRatio = radToInchRatioMap.get(distance);
        launcher.ratio = velRangeRatioMap.get(distance);
        TrajectoryMath.ratio = heightRatioMap.get(distance);
    }
    public boolean spinFlyWheelWithinFeasibleRange(){
        return launcher.spinFlyWheelWithinRange(getVelBounds());
    }

    /**
     * for if you want to see it before the start of the opmode
     */
    public void initTelemetry(){
        telemetry.addLine("hi");
    }
    public LaunchPhase update(){
        launcher.aimServo(getDistance(), launcher.getExitVel());
        double[] velBounds = getVelBounds();
        telemetry.addArray("VEL BOUNDS",velBounds);
        if(!adjustingConstants){
            updateConstants();
        }
        return launchHandler.update(velBounds);
    }
    public void aimTurret(){
        turret.aimTowardsGoal(targetGoalPos, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading());
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
            intake.setPower(pauseBetweenShots()?0.7:1);
        }
        public LaunchPhase launchPhase = LaunchPhase.NULL;
        public boolean isPausedToSpinUp = false;
        public double pauseStartTime = -1;
        double phaseStartTime = -1;
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
            return launcher.launcherPIDF.hasStabilized();
        }

        /**
         * if we're in pause between shots mode, then check if the current speed is enough. otherwise check the average
         * of the past few to reduce noise, with the price of less reaction time
         * I know I hate my code too.
         * @return
         */
        public boolean shouldSpinUp(){
            return pauseBetweenShots()? launcher.launcherPIDF.hasDestabilized():!launcher.launcherPIDF.hasStabilized();
        }
        public LaunchPhase update(double[] velBounds){
            launcher.updatePID(velBounds[0],velBounds[1]);
            targetSpeed = launcher.betweenVel(velBounds[0],velBounds[1]);
            telemetry.addData("launchPhase",launchPhase);
            telemetry.addData("is pausing",pauseBetweenShots());


            boolean velInRange = false;
            lights.leftLight.setColor(!launcher.launcherPIDF.hasStabilized()? Light.Color.Orange:Light.Color.Green);
            lights.rightLight.setColor(launcher.launcherPIDF.hasDestabilized()? Light.Color.Orange:Light.Color.Green);
            telemetry.addLine("start of loop");
            // basic idea is that the sequence will pause if the flywheel is not up to speed, and then attempt to get back up to speed
            //once you get to kicking the servo its far gone imo
            if(launchPhase!=LaunchPhase.NULL&&launchPhase!=LaunchPhase.KICKING_SERVO&&launchPhase!=LaunchPhase.SHUTDOWN){
                //velInRange = launcher.launcherPIDF.hasStabilized();
                telemetry.addData("vel has stabilized",launcher.launcherPIDF.hasStabilized());
                telemetry.addData("vel has destabilized",launcher.launcherPIDF.hasDestabilized());

                if(shouldSpinUp()&&launchPhase!=LaunchPhase.SPINNING_UP&&!isPausedToSpinUp){
                    isPausedToSpinUp = true;
                    pauseStartTime = TIME.getTime();
                    intake.setPower(pauseBetweenShots()?-0.5:0);
                }
                if(isPausedToSpinUp){
                    launcher.spinFlyWheelWithinRange(velBounds[0],velBounds[1]);
                    telemetry.addLine("paused");
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
            telemetry.addLine("going to switch case");
            switch (launchPhase){
                case NULL: {
                    break;
                }
                case SPINNING_UP:{
                    intake.stop();
                    if (launcher.spinFlyWheelWithinRange(velBounds[0],velBounds[1])&&getElapsedTime()>1)//wait for it to be in the right range
                    {
                        launchPhase = LaunchPhase.GATE_OPENING;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case GATE_OPENING:{
                    intake.stop();
                    intake.openGate();
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
                    telemetry.addLine("releasing balls!");
                    if(getElapsedTime() > 0.4){
                        launchPhase = LaunchPhase.KICKING_SERVO;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case KICKING_SERVO:{
                    intake.openGate();
                    intake.kickBall();
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
