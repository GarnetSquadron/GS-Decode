package org.firstinspires.ftc.teamcode.PurelyCalculators.controllers;


/**
 * subscriptions only cost 30$/month
 */
public class PIDPlus extends PIDCon
{
    double Kplus;

    public PIDPlus(double kp, double ki, double kd,double Kplus)
    {
        super(kp, ki, kd);
        this.Kplus = Kplus;
    }

    @Override
    public double calculate(){
        return super.calculate()+Kplus*Math.cbrt(getDistanceToTarget());
    }
}
