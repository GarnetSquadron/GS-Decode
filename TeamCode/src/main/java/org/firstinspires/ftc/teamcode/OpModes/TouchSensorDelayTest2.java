package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.time.TIME;

@TeleOp(name = "delayTest")
public class TouchSensorDelayTest2 extends OpMode
{
    ColorSensor sensor;
    double loopStartTime;
    double deltaTime = 0;
    double distance = 0;
    @Override
    public void init()
    {
        sensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        telemetry.addLine("press a to activate sensor");
        telemetry.addData("loopTime",deltaTime);
        telemetry.addData("distance", distance);
    }

    @Override
    public void loop()
    {
        loopStartTime = TIME.getTime();
        if(gamepad1.a){
            distance = sensor.blue();
        }
        deltaTime = TIME.getTime()-loopStartTime;
        telemetry.update();
    }
}
