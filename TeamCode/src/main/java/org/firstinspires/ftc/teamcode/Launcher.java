package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.encoders.Encoder;
import org.firstinspires.ftc.teamcode.enums.AngleUnitV2;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.MOTOR;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class Launcher {

    //this variable is called angle servo because it is the position of the servo that launches the ball at an angle
    /// TODO give better name
    Servo angleServo;
    RAWMOTOR motor1;
    RAWMOTOR motor2;
    MOTOR turretRot;
    double power = 0;
    public double aimServo(double distance,double vel){
        double angle = AngleFinder.getOptimumAngle(vel,distance);
        setAngle(angle);
        return angle;
    }
    public double aimTurret(double[] goalPos, double[] botPos,double heading){
        double rotAngle = Math.atan((goalPos[1] - botPos[1]) /(goalPos[0]-botPos[0]))-heading;
        setTurretRotation(rotAngle);
        return rotAngle;
    }
    public double[] aimAtGoal(double[] goalPos, double[] botPos, double vel,double heading) {
        double distance =  Math.sqrt(Math.pow(goalPos[0] - botPos[0],2)+Math.pow(goalPos[1]-botPos[1],2));
        double angle = aimServo(distance,vel);
        aimTurret(goalPos,botPos,heading);
        return new double[] {angle,distance};
    }
//    public void setAngle(double angle, Telemetry telemetry){
//       setAngle(angle);
//        telemetry.addData()
//    }
    public void setAngle(double angle){
        angleServo.setPosition((angle-Math.toRadians(30))*0.5/ Math.toRadians(20) );
    }
    public double getHoodPos(){
        return angleServo.getPosition();
    }

    public void setTurretRotation(double rotation) {
        turretRot.runToPos(rotation);
    }
    public void setTurretPower(double power) {
        turretRot.setPower(power);
    }
    public Encoder getTurretEncoder(){
        return turretRot.getEncoder();
    }
    public Encoder getFlywheelEncoder(){
        return motor1.getEncoder();
    }
    public String getMotorType(){
        return turretRot.getMotorType();
    }

    public void setPower(double power) {
        motor2.setPower(power);
        motor1.setPower(power);
    }

    public Launcher(HardwareMap hardwareMap){
        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");


        motor1.getEncoder().setCPR((double) (7 * 3) /2);//motor is a bare motor with 7 cpr, and it outputs into a 40 tooth pulley that belts into a 60 tooth pulley, so its 2/3 that speed
        motor1.getEncoder().scaleToAngleUnit(AngleUnitV2.RADIANS);

        turretRot = new MOTOR(hardwareMap, "turretRot");
        turretRot.setMaxPower(0.5);
        turretRot.getEncoder().setCPR(384.5*4.5);//motor is 435, which has a 384.5 ticks per rotation. The belt is belted at a 4.5:1 ratio
        turretRot.getEncoder().scaleToAngleUnit(AngleUnitV2.RADIANS);
        turretRot.reverseMotor();
        turretRot.setPID(2,0,0);
        motor2.reverseMotor();
    }
    public void zeroTurret(){
        turretRot.getEncoder().setPosition(0);
    }
}
