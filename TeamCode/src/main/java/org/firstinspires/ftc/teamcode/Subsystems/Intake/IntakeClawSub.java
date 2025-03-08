package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.ActionServo;

import java.util.function.DoubleSupplier;

public class IntakeClawSub extends SubsystemBase {
    public ActionServo SERVO;


    public IntakeClawSub(HardwareMap hardwareMap, DoubleSupplier time) {
        SERVO = new ActionServo(hardwareMap, "intake claw", 0.45, 0,1,time);
    }

    public Action Close() {
        return SERVO.runToRatio(1);
    }

    public Action Open() {
        return SERVO.runToRatio(0.3);
    }
    public boolean isOpen(){
        return SERVO.AtMax();
    }
}

