package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareControls.BrakePad;

@TeleOp(name = "BrakePad")
public class BrakePadTest extends OpMode {

    BrakePad pad;
    @Override
    public void init(){
        pad = new BrakePad(hardwareMap);
    }

    @Override
    public void loop(){
        if (gamepad1.a){
            pad.dropPad();
        }
        if (gamepad1.b){
            pad.raisePad();
        }
    }
}
