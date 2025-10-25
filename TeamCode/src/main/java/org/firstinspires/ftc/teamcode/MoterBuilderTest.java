package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.MOTOR;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class MoterBuilderTest extends OpMode {
    MOTOR moterTest;

    @Override
    public void init() {
        moterTest = new MOTOR.builder(hardwareMap).setName("name").build();
    }

    @Override
    public void loop() {

    }
}
