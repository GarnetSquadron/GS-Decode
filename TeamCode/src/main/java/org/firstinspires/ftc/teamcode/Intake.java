package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class Intake {
    Servo servoKicker;
    RAWMOTOR intakeMotor;
    Servo leftGate;
    Servo rightGate;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = new RAWMOTOR(hardwareMap, "intakeMotor");
        servoKicker = hardwareMap.get(Servo.class, "servoKicker");
        leftGate = hardwareMap.get(Servo.class, "leftGate");
        rightGate = hardwareMap.get(Servo.class, "rightGate");
        intakeMotor.reverseMotor();
    }
    public void openGate(){
        leftGate.setPosition(0.4); //0.4
        rightGate.setPosition(0.95); //0.9
    }
    //load ball into the launcher/ basically just launching it
    public void prepareForIntaking(){
        closeGate();
        unKick();
        setPower(1);
    }
    public void loadBall(){
        openGate();
        kickBall();
        closeGate();
        unKick();
    }
    public void closeGate(){
        leftGate.setPosition(0);
        rightGate.setPosition(0);
    }
    public double[] getGatePositions(){
        return new double[]{leftGate.getPosition(), rightGate.getPosition()};
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
