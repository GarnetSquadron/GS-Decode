package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Vision.aprilTags.ObeliskIdentifier;

@TeleOp(group = "test")
public class ObeliskIdTest extends OpMode
{
    ObeliskIdentifier obeliskIdentifier;
    @Override
    public void init()
    {
        obeliskIdentifier = new ObeliskIdentifier(hardwareMap);
    }

    @Override
    public void loop()
    {
        obeliskIdentifier.telemetryAprilTag(telemetry);
    }
}
