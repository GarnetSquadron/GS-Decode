package org.firstinspires.ftc.teamcode.controllers;

public class PidFliywheel {
    public static double setPid(double velocity, double targetVelocity) {
        double diff = targetVelocity -velocity;
        if (diff < 0){
            return 0.0;
        }else {return 0.0;}
    }
}
