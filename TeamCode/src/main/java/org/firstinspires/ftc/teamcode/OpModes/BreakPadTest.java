package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareControls.BreakPad;

@TeleOp(name = "launcher")
public class BreakPadTest extends OpMode {

    BreakPad pad;
    @Override
    public void init(){
        pad = new BreakPad(hardwareMap);
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
