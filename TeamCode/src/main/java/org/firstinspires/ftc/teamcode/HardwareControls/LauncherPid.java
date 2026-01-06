package org.firstinspires.ftc.teamcode.HardwareControls;

public class LauncherPid {
    static double steadyForceConst = 0.5;
    public static double setPid(double velocity, double targetVel, double give) {
        double force = 0;
      if (velocity < targetVel+give){
        force = 1;
      }else if (velocity > targetVel-give) {
          force = -1;
      }else {force =  targetVel* steadyForceConst;}
//      force = force * (velocity-targetVel);
      return force;

    }

}
