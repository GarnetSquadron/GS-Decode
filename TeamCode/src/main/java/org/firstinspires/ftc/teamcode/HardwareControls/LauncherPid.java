package org.firstinspires.ftc.teamcode.HardwareControls;

public class LauncherPid {
    static double steadyForceConst = 0.5;
    public static double GetPid(double velocity, double targetVel, double scaleValue) {
        double differance = targetVel-velocity;
        double force = differance*scaleValue;
        return force;
    }

}
