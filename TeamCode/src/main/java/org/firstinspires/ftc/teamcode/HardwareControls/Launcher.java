package org.firstinspires.ftc.teamcode.HardwareControls;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.encoders.Encoder;
import org.firstinspires.ftc.teamcode.HardwareControls.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.OpModes.SectTelemetryAdder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.TrajectoryMath;
import org.firstinspires.ftc.teamcode.PurelyCalculators.enums.AngleUnitV2;

public class Launcher {
    public LauncherPIDF launcherPIDF = new LauncherPIDF(0.001,-0.0003,0,0.05,0.002,0);
    VoltageSensor voltageSensor;
    SectTelemetryAdder telemetry = new SectTelemetryAdder("LAUNCHER");
    double maxCurrent = 0;
    public double ratio = 0.5;

    //this variable is called angle servo because it is the servo that sets the angle of the ball
    /// TODO give better name
    Servo angleServo;
    public RAWMOTOR motor1;
    public RAWMOTOR motor2;
    /**
     *  = (flywheel angular velocity in rad/sec)/(ball exit velocity in inches/sec).
     *  so basically its in rad/inch
     */
    public static double flywheelToBallSpeedRatio = 1.4;

    /**
     * at 280 ball speed its 300
     * @param flywheelSpeed
     * @return
     */
    public static double getBallSpeedFromFlywheelSpeed(double flywheelSpeed){
        return flywheelSpeed/flywheelToBallSpeedRatio;
    }
    public static double getFlywheelSpeedFromBallSpeed(double ballSpeed){
        return ballSpeed*flywheelToBallSpeedRatio;
    }
    public static
    Turret turret;
    /**
     * 13.00 battery:
     * 1.0: 400
     * 0.9: 365
     *
     * 12.55 battery:
     * 1.0: 370
     * 0.9: 335
     * 0.8: 300
     * 0.7: 260
     * 0.6: 215
     * 0.5: 170
     * 0.4: 130
     */
    public static double maxPossibleAngVel = 340;
    /**
     * the theoretical maximum velocity a ball could leave the launcher at
     */
    public static double getMaxPossibleExitVel() {
        return getBallSpeedFromFlywheelSpeed(maxPossibleAngVel);
    }
    double power = 0;

    public double getCurrent(){
        return (motor1.getCurrentMilliamps()+motor2.getCurrentMilliamps())/2;
    }
    public double aimServo(double distance,double vel){
        double angle = TrajectoryMath.getOptimumAngle(vel,distance);
        setAngle(angle);
        return angle;
    }
    public double getExitVel(){
        //getVelocity gets the rad/sec, so we divide that by rad/sec and then multiply by in/sec to get the inches per sec
        return getBallSpeedFromFlywheelSpeed(getFlywheelEncoder().getVelocity());
    }
    public double[] aimAtGoal(double[] goalPos, double[] botPos, double vel,double heading) {
        double distance =  Math.sqrt(Math.pow(goalPos[0] - botPos[0],2)+Math.pow(goalPos[1]-botPos[1],2));
        double angle = aimServo(distance,vel);
        turret.aimTowardsGoal(goalPos,botPos,heading);
        return new double[] {angle,distance};
    }
//    public void setAngle(double angle, Telemetry telemetry){
//       setAngle(angle);
//        telemetry.addData()
//    }

    /**
     * sets the hood angle
     * @param angle the angle relative to horizontal that the ball comes out at (in radians of course)
     */
    public void setAngle(double angle){
        angleServo.setPosition((angle-Math.toRadians(RobotDimensions.Hood.minAngle))*0.5/ Math.toRadians(RobotDimensions.Hood.maxAngle-RobotDimensions.Hood.minAngle) );
    }
    public double getAngle(){
        return angleServo.getPosition()*Math.toRadians(20)/0.5+Math.toRadians(30);
    }
    public double spinUpFlywheel(double power){
        setPower(-power);
        return getExitVel();
    }
    public double betweenVel(double minVel, double maxVel){
        return Math.min(minVel*(1-ratio)+maxVel*ratio,getMaxPossibleExitVel());
    }
    public void updatePID(double minVel, double maxVel){
        double targetVel = getFlywheelSpeedFromBallSpeed(betweenVel(minVel,maxVel));
        double currentVel = motor1.getEncoder().getVelocity();
        launcherPIDF.updateArrays(currentVel,targetVel);
        telemetry.addData("target speed",targetVel);
        telemetry.addData("actual speed",currentVel);
        telemetry.addData("power", power);
        telemetry.addData("acceleration",launcherPIDF.getAcceleration());
        telemetry.addData("has stabilized", launcherPIDF.hasStabilized());
        telemetry.addData("acceleration good", launcherPIDF.lowAcceleration());
        telemetry.addData("velocity good", launcherPIDF.averageIsCloseToTarget());
        telemetry.addData("velocity difference", launcherPIDF.differences[0]);
        telemetry.addData("supposed velocity difference", currentVel-targetVel);
        telemetry.addData("target ratio", ratio);
        telemetry.addData("rad/inches ratio", flywheelToBallSpeedRatio);
    }
    public boolean spinFlyWheelWithinRange(double minVel,double maxVel){
        //spin up the flywheel to get it within the provided range
        //if its in the range return true otherwise

        //pid code
        double targetVel = getFlywheelSpeedFromBallSpeed(betweenVel(minVel,maxVel));
        double currentVel = motor1.getEncoder().getVelocity();
        double power = launcherPIDF.getPid(currentVel,targetVel);

        spinUpFlywheel(power);
        //temporary flywheel code, just guesses the velocity.
        //it doesn't exist anymore mb

        return launcherPIDF.hasStabilized();
    }
//    public boolean SpinUpFlywheelWithPid(double minVel, double maxVel){
//        //pd code
//        double targetVel = (minVel+maxVel)/2;
//        double speed = launcherPIDF.GetPid(motor1.getEncoder().getVelocity(),targetVel,(minVel+maxVel)/2);
//        spinUpFlywheel(speed);
//
//        return minVel < getExitVel() && getExitVel() < maxVel;
//    }
    public boolean spinFlyWheelWithinRange(double[] range){
        return spinFlyWheelWithinRange(range[0],range[1]);
    }
    public Encoder getFlywheelEncoder(){
        return motor1.getEncoder();
    }

    public void setPower(double power) {
        motor2.setPower(power);
        motor1.setPower(power);
    }
//    public int wasBallShot(double current){
//        if(flyWheelTime>lastShotBallTime) {
//            lastShotBallTime = flyWheelTime;
//        }
//        if (lastShotBallTime > 1500){
//            shootBall = true;
//            lastShotBallTime=0;
//        }
//        if (current>1800 && flyWheelTime > 1500 && shootBall){
//
//            //create int lastShotBallTime
//            // create boolean shootBall
//            //create flywheel time
//            Intake.artifacts --;
//            shootBall = false;
//        }
//        return Intake.artifacts;
//    }

    public Launcher(HardwareMap hardwareMap){
        voltageSensor = hardwareMap.voltageSensor.get("Control Hub");

        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");
        turret = new Turret(hardwareMap);
        //motor is a bare motor with 7 cpr, and it outputs into a 40 tooth pulley that belts into a 60 tooth pulley, so its 2/3 that speed (somehow this was 1/4 what I wanted so idk it works now)
        motor1.getEncoder().setCPR((double) (7 * 3) *2);        motor1.getEncoder().scaleToAngleUnit(AngleUnitV2.RADIANS);
        motor2.reverseMotor();
    }
}
