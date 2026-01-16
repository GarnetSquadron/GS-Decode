package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lights{
    public Light leftLight;
    public Light rightLight;
    public Lights(HardwareMap hardwareMap){
        leftLight = new Light(hardwareMap,"light1");
        rightLight = new Light(hardwareMap,"light2");
    }
}
