package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * this is maybe a little redundant, but this way I can store the names of the devices
 */
public class Light
{
    public enum Color{
        RED(0.277),
        Orange(0.333),
        Yellow(0.338),
        Sage(0.444),
        Green(0.500);
        public final double position;
        Color(double position){
            this.position = position;
        }
    }
    Servo light;
    public Light(HardwareMap hardwareMap,String name){
        light = hardwareMap.get(Servo.class,name);
    }
    public void setColor(double color){
        light.setPosition(color);
    }
    public void setColor(Color color){
        light.setPosition(color.position);
    }
}
