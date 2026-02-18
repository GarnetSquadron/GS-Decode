package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import kotlin.Pair;

//@TeleOp(name = "settings test")
public class TestSettings extends SettingSelectorOpMode
{
    public TestSettings()
    {
        super(new Pair[]{
                new Pair(
                        new String[]{"hi","hello","howdy"},"greetings"
                ),
                new Pair(
                        new String[]{"pizza","banana","apple"},"fruit"
                )
        });
    }

    @Override
    public void init()
    {
    }

    @Override
    public void loop()
    {

    }
}
