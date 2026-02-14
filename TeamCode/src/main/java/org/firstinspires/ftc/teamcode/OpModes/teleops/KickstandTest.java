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
        //the time is 1700. this seems to wrk well but still might want to be tuned
        if (gamepad1.aWasPressed() & gamepad1.xWasPressed() & !stand.extended){
            stand.extendStand(1700);
        }
    }
}
