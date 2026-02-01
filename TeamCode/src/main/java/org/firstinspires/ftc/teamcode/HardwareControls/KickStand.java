package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HardwareControls.hardwareClasses.SERVO;

public class KickStand {
    public KickStand(HardwareMap hardwareMap){
        servo1 = hardwareMap.get(CRServo.class, "KickServo1");
        servo2 = hardwareMap.get(CRServo.class, "KickServo2");

    }
    CRServo servo1;
    CRServo servo2;

    public int time = 0;
    private void setServoPower(int power){
        servo1.setPower(power);
        servo2.setPower(-power);
    }

    public boolean extendStand(int TestTime){
        time += 1;

        if(time <= TestTime) {
            setServoPower(1);
            return false;
        } else {
            setServoPower(0);
            return false;
        }
    }
}