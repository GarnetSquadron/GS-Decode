package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class launcher {
    static Servo angleServo;
    static RAWMOTOR motor1;
    static RAWMOTOR motor2;
    double power = 0;

    public static void init(){
        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");

    }}
