package org.firstinspires.ftc.teamcode.OpModes.autonomi.Unused;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

//@Autonomous
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
