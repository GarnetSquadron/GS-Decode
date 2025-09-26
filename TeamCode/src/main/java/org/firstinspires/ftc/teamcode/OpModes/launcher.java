package org.firstinspires.ftc.teamcode.OpModes;

import static java.lang.Thread.sleep;

import android.webkit.JavascriptInterface;


import com.acmerobotics.roadrunner.SleepAction;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;
@TeleOp(name = "launcher")
public class launcher extends OpMode {
    Servo angleServo;
    RAWMOTOR motor1;
    RAWMOTOR motor2;
    double power = 0;

    double servoPosition = 0.5;
    boolean wasPressed = false;
    @Override
    public void init() {

        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");
    }
    public void loop() {

        if (gamepad1.b) power = 1;
        else power = 0;

        motor1.setPower(power);
        motor2.setPower(power / -1);

        angleServo.setPosition(servoPosition);

        telemetry.addData("power",power);

        telemetry.addData("motor power", motor1.getPower());
        telemetry.addData("servo angle", angleServo.getDirection());


    }
}
