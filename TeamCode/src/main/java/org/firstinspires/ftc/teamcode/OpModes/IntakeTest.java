package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Launcher;

import java.util.jar.Attributes;

@TeleOp(name = "intake test")
public class IntakeTest extends OpMode {
    Launcher launcher;
    @Override
    public void init() {
        launcher = new Launcher(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {launcher.setPower(1);}
        else {launcher.setPower(0);}
    }
}
