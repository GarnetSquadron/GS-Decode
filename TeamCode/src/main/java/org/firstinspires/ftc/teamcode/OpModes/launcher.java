package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;
@TeleOp(name = "launcher")
public class launcher extends OpMode {
    Servo angleServo;
    RAWMOTOR motor1;
    RAWMOTOR motor2;
    double power = 0;

    double servoPosition = 0;
    //temp
    boolean leftWasPressed = false;
    boolean rightWasPressed = false;
    @Override
    public void init() {

        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");
    }
    public void loop() {
        ExtraMath.Clamp(servoPosition,1,0);
        //temp
        leftWasPressed = false;
        rightWasPressed = false;
        if (gamepad1.dpad_left & !leftWasPressed) {
            servoPosition += 0.1;
            telemetry.addData("left dPad presssed",servoPosition);
            ExtraMath.Clamp(servoPosition,1,0);
            leftWasPressed = true;

        }
        if (gamepad1.dpad_right & !rightWasPressed){
            servoPosition -= 0.1;
            telemetry.addData("right dPad presssed",servoPosition);
            ExtraMath.Clamp(servoPosition,1,0);
            rightWasPressed = true;
        }

        if (gamepad1.b) power = 1;
        else power = 0;

        motor1.setPower(power);
        motor2.setPower(power / -1);

        angleServo.setPosition(servoPosition);

        telemetry.addData("power",power);

        telemetry.addData("motor power", motor1.getPower());
        telemetry.addData("servo position", angleServo.getPosition());
        telemetry.addData("hi there"," this is from a test commit");


    }
}
