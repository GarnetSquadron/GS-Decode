package org.firstinspires.ftc.teamcode.OpModes;

import static java.lang.Thread.sleep;

import android.webkit.JavascriptInterface;

import com.acmerobotics.roadrunner.SleepAction;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;
@TeleOp(name = "launcher")
public class launcher extends OpMode {
    RAWMOTOR moter1;
    RAWMOTOR moter2;
    int power = 0;
    @Override
    public void init() {
        moter1 = new RAWMOTOR(hardwareMap, "launcherMoter1");
        moter2 = new RAWMOTOR(hardwareMap, "launcherMoter2");

    }
    public void loop() {
        moter1.setPower(power);
        moter2.setPower(power);
        if (gamepad1.a) {
           power += 1;
        }

    }
}
