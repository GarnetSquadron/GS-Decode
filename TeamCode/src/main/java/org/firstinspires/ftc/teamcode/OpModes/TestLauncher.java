package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.LauncherPid;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;
@TeleOp(name = "launcher")
public class TestLauncher extends OpMode {
    double returned;
    double dampforce = 0;
    Servo angleServo;
    RAWMOTOR motor1;
    RAWMOTOR motor2;
    double give = 0;
    double power = 0;
    double target = 0;
    double timer = 0;
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
        returned = LauncherPid.setPid(motor1.getEncoder().getVelocity(),target,0.5,dampforce);

        ExtraMath.Clamp(servoPosition,1,0);
        //launcher pid tuning

        if(target != 1){
            if(dampforce != 0.06){
                if(give != 1){
                    returned = LauncherPid.setPid(motor1.getEncoder().getVelocity(), target, give, dampforce);
                    motor1.setPower(returned);
                    motor2.setPower(returned);
                    if(returned == target){
                        timer += 0.1;
                    }
                    if(timer == 2){
                        give+= 0.1;
                        motor1.setPower(0);
                        motor1.stop();
                    }
                }else {dampforce += 0.01;}
            }else {target += 0.1; }
        }
        telemetry.addData("power ",motor1.getPower());
        telemetry.addData("velocity ",motor1.getEncoder().getVelocity());

        if (gamepad1.a) {target +=0.1;}
        if (gamepad1.b) {target -=0.1;}
        telemetry.addData("target",target);
        telemetry.addData("a pressed",true);
        motor1.setPower(returned);
        telemetry.addData("y stick",gamepad2.left_stick_y);

        telemetry.update();
    }
}
