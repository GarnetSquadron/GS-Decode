package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareControls.KickStand;

@TeleOp(name = "kickstand test")
public class KickstandTest extends OpMode {
    KickStand stand;
    @Override
    public void init(){
        stand = new KickStand(hardwareMap);
    }

    @Override
    public void loop(){
        telemetry.addData("eached time", stand.extendStand(1700));
        telemetry.addData("time", stand.time);
        telemetry.update();
    }
}
