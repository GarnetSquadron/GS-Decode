package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

@Autonomous
public class NullAuto extends OpMode
{
    public static double thing = 0;
    @Override
    public void init()
    {

    }

    @Override
    public void loop()
    {
        thing = TIME.getTime();
        telemetry.addData("time",thing);
        telemetry.update();
    }
}
