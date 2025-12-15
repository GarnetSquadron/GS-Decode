package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ClassUtil;


import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.HardwareControls.LauncherPid;
import org.firstinspires.ftc.teamcode.HardwareControls.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.PurelyCalculators.ExtraMath;

@TeleOp(name = "test launcher")
public class TestLauncher extends OpMode {
    Launcher launcher;
    double returned;
    double dampforce = 0.01;
    Servo angleServo;
    double give = 0;
    double power = 0;
    double target = 0.1;
    double timer = 0;
    double servoPosition = 0;
    //temp
    boolean leftWasPressed = false;
    boolean rightWasPressed = false;
    @Override
    public void init() {
        launcher = new Launcher(hardwareMap);
        angleServo = hardwareMap.get(Servo.class, "angleServo");
//        motor1 = new RAWMOTOR(hardwareMap, "launcherMotor1");
//        motor2 = new RAWMOTOR(hardwareMap, "launcherMotor2");

    }
    public void loop() {
        ExtraMath.Clamp(servoPosition,1,0);
        //launcher pid tuning

        if(target != 1){
            if(dampforce != 0.1){
                if(give != 1){
                    returned = LauncherPid.setPid(launcher.getFlywheelEncoder().getVelocity(), target, give, dampforce);
                    launcher.spinUpFlywheel(returned);
                    if(returned == target){
                        timer += 0.1;
                    }
                    if(timer == 2){
                        give+= 0.1;
                        launcher.setPower(0);
                    }
                }else {dampforce += 0.01;}
            }else {target += 0.1; }
        }
        telemetry.addData("velocity ",launcher.getFlywheelEncoder().getVelocity());

        if (gamepad1.a) {target +=0.1;}
        if (gamepad1.b) {target -=0.1;}
        telemetry.addData("timer ", timer);
        telemetry.addData("damp ",dampforce);
        telemetry.addData("target ",target);
        telemetry.addData("give ", give);
        telemetry.addData("returned ", returned);

        telemetry.update();
    }
}
