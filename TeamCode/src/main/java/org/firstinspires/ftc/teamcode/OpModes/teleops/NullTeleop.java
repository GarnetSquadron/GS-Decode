package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.autonomi.Unused.NullAuto;

//@TeleOp
public class NullTeleop extends OpMode
{
    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {
        telemetry.addData("thing", NullAuto.thing);
        telemetry.update();
    }
}
