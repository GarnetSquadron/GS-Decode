package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Launcher;

@TeleOp(name = "Launcher test")
public class BasicLauncherTest extends OpMode {
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
