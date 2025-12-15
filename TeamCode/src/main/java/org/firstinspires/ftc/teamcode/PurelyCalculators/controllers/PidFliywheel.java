package org.firstinspires.ftc.teamcode.PurelyCalculators.controllers;

import org.firstinspires.ftc.teamcode.PurelyCalculators.ExtraMath;

public class PidFliywheel {
    static double inc = 0;
    public static double[] getPid(double velocity, double target,double scaleaz, double scalebz){
        double diff = target-velocity;
        double scale=0;
        double isCloseToTarget = 0;
        // target 5.5 velocity 5.7
        if (velocity <target+0.1 & velocity>target-0.1){isCloseToTarget=1;}
        double[] ret = new double[3];
        ret[2] = isCloseToTarget;
        ret[1] = diff;
        if (diff>=0) scale = scaleaz;
        if (diff<=0) scale = scalebz;
        inc += diff*scale;
        ret[0] = ExtraMath.max(inc,0);
        return ret;
    }
}
