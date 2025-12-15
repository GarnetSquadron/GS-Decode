package org.firstinspires.ftc.teamcode.HardwareControls;

public class LauncherPid {

    public static double setPid(double velocity, double targetVel, double give, double forceDamp) {
        double force = 0;
      if (velocity < targetVel+give){
        force = 1;
      }else if (velocity > targetVel-give) {
          force = -1;
      }else {force =  targetVel* forceDamp;}
      force = force * (velocity-targetVel);
      return force;

    }

}
