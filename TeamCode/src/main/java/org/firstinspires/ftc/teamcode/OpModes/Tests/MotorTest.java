package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

@TeleOp(name = "MotorTest", group = "test")
public class MotorTest extends LinearOpMode
{
    RAWMOTOR lf,rf,lb,rb;
    @Override
    public void runOpMode()
    {
        rf = new RAWMOTOR(hardwareMap,"rf");
        rb = new RAWMOTOR(hardwareMap,"rb");
        lf = new RAWMOTOR(hardwareMap,"lf");
        lb = new RAWMOTOR(hardwareMap,"lb");
        telemetry.addData("lf pos", lf.getEncoder().getPos());
        telemetry.addData("rf pos", rf.getEncoder().getPos());
        telemetry.addData("lb pos", lb.getEncoder().getPos());
        telemetry.addData("rb pos", rb.getEncoder().getPos());
        lb.setPower(1);
        sleep(1000);
        lb.setPower(0);
        lf.setPower(1);
        sleep(1000);
        lf.setPower(0);
        rb.setPower(1);
        sleep(1000);
        rb.setPower(0);
        rf.setPower(1);
        sleep(1000);
        rf.setPower(0);


        //lb=lf, lf=rb, rb=rf, rf=lb
    }
}
