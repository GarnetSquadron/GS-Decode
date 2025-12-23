package org.firstinspires.ftc.teamcode.HardwareControls;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.HardwareControls.encoders.Encoder;
import org.firstinspires.ftc.teamcode.HardwareControls.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.PurelyCalculators.AngleFinder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.enums.AngleUnitV2;

public class Launcher {
    double maxCurrent = 0;

    double timeWhenFlywheel;
    //this variable is called angle servo because it is the position of the servo that launches the ball at an angle
    /// TODO give better name
    Servo angleServo;
    public RAWMOTOR motor1;
    public RAWMOTOR motor2;
    double radPerSecToVelRatio = 1;
    Turret turret;
    public double maxPossibleVel = 340;
    double power = 0;

    public double getCurrent(){
        return (motor1.getCurrentMilliamps()+motor2.getCurrentMilliamps())/2;
    }
    public double aimServo(double distance,double vel){
        double angle = AngleFinder.getOptimumAngle(vel,distance);
        setAngle(angle);
        return angle;
    }
    public double getInPerSec(){
        return getFlywheelEncoder().getVelocity()/radPerSecToVelRatio;
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
        angleServo.setPosition((angle-Math.toRadians(30))*0.5/ Math.toRadians(20) );
    }
    public double spinUpFlywheel(double power){
        setPower(-power);
        return getInPerSec();
    }
    public boolean spinFlyWheelWithinRange(double minVel,double maxVel){
        //spin up the flywheel to get it within the provided range
        //if its in the range return true otherwise

        //temporary flywheel code, just guesses the velocity.
        spinUpFlywheel((maxVel)/(maxPossibleVel));

        return minVel < getInPerSec() && getInPerSec() < maxVel;
    }
    public double getHoodPos(){
        return angleServo.getPosition();
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
        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");
        turret = new Turret(hardwareMap);
        //motor is a bare motor with 7 cpr, and it outputs into a 40 tooth pulley that belts into a 60 tooth pulley, so its 2/3 that speed (somehow this was 1/4 what I wanted so idk it works now)
        motor1.getEncoder().setCPR((double) (7 * 3) *2);        motor1.getEncoder().scaleToAngleUnit(AngleUnitV2.RADIANS);
        motor2.reverseMotor();
    }
}
