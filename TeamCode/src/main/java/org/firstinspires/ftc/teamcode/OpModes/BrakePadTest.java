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
        if (gamepad1.b){
            pad.dropPadd();
        }
        if (gamepad1.a){
            pad.dropPad();
        }
        if (gamepad1.x){
            pad.dropPad2();
        }
        if (gamepad1.y){
            pad.dropPad3();
        }
        if (gamepad1.left_bumper){
            pad.raisePad();
        }
    }
}
