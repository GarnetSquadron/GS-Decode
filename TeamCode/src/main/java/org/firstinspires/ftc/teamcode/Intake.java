package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Intake {
    DcMotorEx motor;

    public Intake(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
    }
    public int countArtifacts(double current){
        int artifacts = 0;
        if(current <2000 && current > 1000){
        artifacts++;
    } 
        else if(current <3800){
            artifacts = 2;
    }
        else{
        artifacts = 3;
    }
        return artifacts;
    }
    public boolean hasThreeArtifacts(double current){
        if(current > 3800){
            return true;
        }
        return false;
    }
    public void motorPower(double power) {
        motor.setPower(power);
    }
    public boolean isStalling(){
        return motor.isOverCurrent();
    }
    public double getMilliamps(){
        return motor.getCurrent(CurrentUnit.MILLIAMPS);
    }
    public void stopIntake() {
        motor.setPower(0);
    }
}
