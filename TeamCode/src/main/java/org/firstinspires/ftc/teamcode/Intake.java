package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class Intake {
    RAWMOTOR intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = new RAWMOTOR(hardwareMap, "intakeMotor");
        intakeMotor.reverseMotor();
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }
    public void stop() {
        intakeMotor.stop();
    }
}
