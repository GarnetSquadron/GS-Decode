package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * this is maybe a little redundant, but this way I can store the names of the devices
 * <a href="https://cdn11.bigcommerce.com/s-x56mtydx1w/images/stencil/original/products/2275/15126/3118-0808-0002-Product-Insight-4__88285.1757516465.png?c=1">colors</a>
 */
public class Light
{
    public enum Color{
        RED(0.279),//gobuilda thinks its 0.277 but it only works at 0.279
        Orange(0.300),
        Yellow(0.338),
        Sage(0.444),
        Green(0.500),
        Teal(0.530),
        BLUE(0.611);
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
