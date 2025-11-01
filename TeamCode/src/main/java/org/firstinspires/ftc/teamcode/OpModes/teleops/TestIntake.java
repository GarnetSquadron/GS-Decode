package org.firstinspires.ftc.teamcode.OpModes.teleops;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Intake;

@TeleOp(name = "Intaking test")
public class TestIntake extends OpMode {
    Intake intake;
    @Override
    public void init() {
        intake = new Intake(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {intake.setPower(1);}
        else {intake.stop();}
    }
}
