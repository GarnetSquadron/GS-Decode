package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.controllers.PidFliywheel;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.time.TIME;

@TeleOp(name = "launcher")
public class TestLauncher extends OpMode {
    Servo angleServo;
    RAWMOTOR motor1;
    RAWMOTOR motor2;
    double power = 0;
    double scale = 0.01;
    double target = 0;
    double servoPosition = 0;
    //temp

    double motorPower = 0;
    double motorVelocity;
    double timeWhen0 = TIME.getTime();
    boolean timing = false;
    double lastTime = 1000;
    boolean trigWasJustPressed = false;
    double timer = 0;
    @Override
    public void init() {
        angleServo = hardwareMap.get(Servo.class, "angleServo");
        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");
    }
    public void loop() {
//        motorVelocity = (motor1.getEncoder().getVelocity()+motor2.getEncoder().getVelocity())/2;
        motorVelocity = motor1.getEncoder().getVelocity();
        motor1.setPower(motorPower/-1);
//        motor2.setPower(0.5);
        ExtraMath.Clamp(servoPosition,1,0);
        //temp
        /*
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

        if (gamepad1.b) power = 0.6;
        else power = 0;

        motor1.setPower(power);
        motor2.setPower(power / -1);

        //angleServo.setPosition(servoPosition);

        telemetry.addData("power",power);

        telemetry.addData("motor power", motor1.getPower());
        telemetry.addData("servo position", angleServo.getPosition());
        */


        if (gamepad1.a) {target +=0.1;}
        if (gamepad1.y) {target -=0.1;}

        if (gamepad1.left_trigger > 0& !trigWasJustPressed&!timing){
            trigWasJustPressed = true;
        }else{trigWasJustPressed =false;}

        double[] returnedVal = PidFliywheel.getPid(motorVelocity,target,scale,scale);
        boolean isTarget = returnedVal[2]==1;
        boolean isReallyTarget;
        if (isTarget){timer+=0.1;}else timer = 0;
        if (timer >= 1){isReallyTarget = true;}else isReallyTarget = false;
        motorPower = returnedVal[0];
        timer = ExtraMath.Clamp(timer,10,0);

//        if (target <= 6){
//            if (scale <= 0.01 ){
//                double[] returnedVal = PidFliywheel.getPid(motorVelocity,target,scale,scale);
//                boolean isTarget = returnedVal[2]==1;
//                motorPower = returnedVal[0];
//                if (isTarget){timer+=0.1;}else{timer = 0;}
//                if (timer >= 1){
//                    scale += 0.001;
//                    motor1.setPower(0);
//                    lastTime = TIME.getTime()-timeWhen0;
//                    timeWhen0=TIME.getTime();
//                }
//            }else {scale = 0; target += 0.1;}
//        }

        telemetry.addData("left stick x",gamepad1.left_stick_x);
        telemetry.addData("left stick y",gamepad1.left_stick_y);
        telemetry.addData("right stick x",gamepad1.right_stick_x);
        telemetry.addData("right stick y",gamepad1.right_stick_y);
        telemetry.addData("differance ",returnedVal[1]);
        telemetry.addData("returned value ",returnedVal[0]);
        telemetry.addData("power ",motorPower);
        telemetry.addData("velocity ",motorVelocity);
        telemetry.addData("motor 1 velocity ",motor1.getEncoder().getVelocity());
        telemetry.addData("motor 2 velocity ",motor2.getEncoder().getVelocity());
        telemetry.addData("target ",target);
        telemetry.addData("last time ",lastTime);
        telemetry.addData("scale ",scale);
        telemetry.addData("is target ",isTarget);
        telemetry.addData("is really target ",isReallyTarget);
        telemetry.addData("timer ",timer);
        telemetry.update();
    }
}
