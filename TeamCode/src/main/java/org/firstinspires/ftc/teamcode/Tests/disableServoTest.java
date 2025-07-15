package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp(name = "Disable Servo Test", group = "test")
public class disableServoTest extends OpMode
{
    Servo servo;
    ServoController servoController;
    ServoImplEx servoImplEx;
    @Override
    public void init()
    {
        servo = hardwareMap.get(Servo.class,"wristservo");
        servoController = servo.getController();
        servoImplEx = hardwareMap.get(ServoImplEx.class,"wristservo");
    }

    @Override
    public void loop()
    {
        if(gamepad1.a){
            servo.setPosition(0);
        }
        else
        {
            //servoController.pwmDisable();
            servoImplEx.setPwmDisable();
        }
        telemetry.addData("servo controller status", servoImplEx.isPwmEnabled());
    }
}
