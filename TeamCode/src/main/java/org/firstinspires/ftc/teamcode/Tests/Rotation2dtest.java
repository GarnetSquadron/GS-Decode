package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ExtraMath;

@TeleOp(name = "rotation 2d test",group = "test")
public class Rotation2dtest extends OpMode
{

    Rotation2d rotation2d = new Rotation2d(2,3);
    Vector2d vector2d = new Vector2d(5,4);
    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {
        telemetry.addData("rotation2d",rotation2d);
        telemetry.addData("vector2d",vector2d);
        telemetry.addData("rotation vector x", rotation2d.vec().x);
        telemetry.addData("projecion of vec onto rotation", ExtraMath.project(vector2d,rotation2d.vec()));
    }
}
