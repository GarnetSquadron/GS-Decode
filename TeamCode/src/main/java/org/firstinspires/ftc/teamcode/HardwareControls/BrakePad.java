package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BrakePad {
    boolean dropped = false;
    Servo breakServo;
    public BrakePad(HardwareMap hardwareMap){
        breakServo = hardwareMap.get(Servo.class, "brakePad");
        breakServo.setPosition(0);
    }

    public void dropPadd(){
        breakServo.setPosition(0.04);
        dropped = true;
    }
    public void dropPad(){
        breakServo.setPosition(0.05);
        dropped = true;
    }
    public void dropPad2(){
        breakServo.setPosition(0.065);
        dropped = true;
    }
    public void dropPad3(){
        breakServo.setPosition(0.08);
        dropped = true;
    }
    public void raisePad(){
        breakServo.setPosition(0);
        dropped = false;
    }
}
