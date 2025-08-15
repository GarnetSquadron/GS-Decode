package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SmoothVelocityEncoder;
import org.firstinspires.ftc.teamcode.encoders.Encoder;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.UpdatableMOTOR;

@TeleOp(name = "PIDTest",group = "test")
public class PIDTest extends OpMode
{
    UpdatableMOTOR motor;

    @Override
    public void init()
    {
        motor = new UpdatableMOTOR(hardwareMap,"motor");
        motor.setEncoder(new SmoothVelocityEncoder(motor.getEncoder(),10));
        motor.setPID(0,0,100);
    }

    @Override
    public void loop()
    {
        motor.runToPos(0);
        telemetry.addData("position", motor.getEncoder().getPos());
        telemetry.addData("tgt pos",motor.getTargetPosition());
        telemetry.addData("power",motor.getPower());
    }
}
