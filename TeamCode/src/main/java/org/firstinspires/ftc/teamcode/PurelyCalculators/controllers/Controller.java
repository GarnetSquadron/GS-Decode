package org.firstinspires.ftc.teamcode.PurelyCalculators.controllers;

import org.firstinspires.ftc.teamcode.HardwareControls.encoders.Encoder;

public abstract class Controller
{
    Encoder encoder;

    public void setEncoder(Encoder encoder)
    {
        this.encoder = encoder;
    }

    public abstract double calculate();
}
