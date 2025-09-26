package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.telemetry.SelectScope;
import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class RobotDetectOpMode extends SelectableOpMode
{
    public RobotDetectOpMode(String name, Consumer<SelectScope<Supplier<OpMode>>> opModes)
    {
        super(name, opModes);
        try
        {
            hardwareMap.get(Servo.class, "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679");
        } catch (IllegalArgumentException ignored)
        {

        }
    }
}
