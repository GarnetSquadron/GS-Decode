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
    public boolean openGate(){
        leftGate.setPosition(0.4); //0.4
        rightGate.setPosition(0.95); //0.95
        if (leftGate.getPosition() ==0.4&rightGate.getPosition()==0.95){return true;}else return false;
    }
        //spin up intake and close the launcher gate
    public void prepareForIntaking(){
        closeGate();
        unKick();
        setPower(1);
    }
    public void unprepareIntake(){
        setPower(0);
    }
    //load ball into the launcher/ basically just launching it
    public void loadBall(){
        openGate();
        kickBall();
        closeGate();
        unKick();
    }
    public boolean closeGate(){
        leftGate.setPosition(0);
        rightGate.setPosition(0);
        if (leftGate.getPosition() ==0&rightGate.getPosition()==0){return true;}else return false;
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
    public boolean kickBall(){
        servoKicker.setPosition(0.64);
        if (servoKicker.getPosition() ==0.64){return true;}else return false;
    }
    public boolean unKick(){
        servoKicker.setPosition(0.2);
        if (servoKicker.getPosition() ==0.2){return true;}else return false;
    }
}
