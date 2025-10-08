package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class launcher {

    Servo angleServo;
    RAWMOTOR motor1;
    RAWMOTOR motor2;
    double power = 0;
    public void setAngle(double angle, double tmpScaleFactor){
        angleServo.setPosition(angle * tmpScaleFactor);
    }

    public void setPower(double power) {
        motor2.setPower(power);
        motor1.setPower(power);
    }

    public void init(){
        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");
        motor2.reverseMotor();
    }}
