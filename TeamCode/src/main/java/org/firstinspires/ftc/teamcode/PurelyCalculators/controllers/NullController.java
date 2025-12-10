package org.firstinspires.ftc.teamcode.PurelyCalculators.controllers;

import org.firstinspires.ftc.teamcode.HardwareControls.encoders.encoders.Encoder;

/**
 * calculate always returns 0
 */
public class NullController extends Controller
{
    public NullController()
    {
        super();
        setEncoder(new Encoder(() -> 0));
    }

    @Override
    public double calculate()
    {
        return 0;
    }
}
