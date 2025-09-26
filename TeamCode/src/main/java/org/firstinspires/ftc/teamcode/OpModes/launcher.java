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
    RAWMOTOR motor1;
    //RAWMOTOR motor2;
    double power = 0;
    boolean wasPressed = false;
    @Override
    public void init() {
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        //motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");
    }
    public void loop() {
        motor1.setPower(power);
        //motor2.setPower(power);
        if (gamepad1.a) {
            if (wasPressed == false) {
                power += 0.1;
                telemetry.addLine("a pressed");
                wasPressed = true;
            }

        }
        else {
            wasPressed = false;
        }

        telemetry.addData("power",power);
        telemetry.addData("motor power", motor1.getPower());


    }
}
