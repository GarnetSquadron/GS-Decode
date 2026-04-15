package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BrakePad {
    boolean dropped = false;
    Servo breakServo;
    public BrakePad(HardwareMap hardwareMap){
        breakServo = hardwareMap.get(Servo.class, "breakPad");
        breakServo.setPosition(0);
    }

    public void dropPad(){
        breakServo.setPosition(30);
        dropped = true;
    }
    public void raisePad(){
        breakServo.setPosition(0);
        dropped = false;
    }
}
