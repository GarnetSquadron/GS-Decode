package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lights{
    public Light light1;
    public Light light2;
    public Lights(HardwareMap hardwareMap){
        light1 = new Light(hardwareMap,"light1");
        light2 = new Light(hardwareMap,"light2");
    }
}
