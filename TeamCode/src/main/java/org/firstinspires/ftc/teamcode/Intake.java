package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    DcMotorEx intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
    }

    public void motorPower(double power) {
        intakeMotor.setPower(power);
    }
    public boolean isStalling(){
        return intakeMotor.isOverCurrent();
    }
    public void stopIntake() {
        intakeMotor.setPower(0);
    }
}
