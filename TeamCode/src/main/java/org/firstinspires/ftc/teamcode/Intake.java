package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class Intake {
    Servo servoKicker;
    RAWMOTOR intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = new RAWMOTOR(hardwareMap, "intakeMotor");
        servoKicker = hardwareMap.get(Servo.class, "servoKicker");
        intakeMotor.reverseMotor();
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }
    public void stop() {
        intakeMotor.stop();
    }
    public void kickBall(){
        servoKicker.setPosition(0.64);
    }
    public void unKick(){
        servoKicker.setPosition(0.2);
    }
}
