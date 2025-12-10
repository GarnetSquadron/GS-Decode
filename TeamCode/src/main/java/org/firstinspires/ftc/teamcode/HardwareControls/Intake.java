package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.HardwareControls.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

public class Intake {
    public double timeWhenIntake;
    double delay = 2;
    double maxCurrent = 0;
    static int artifacts = 0;
    Servo servoKicker;
    RAWMOTOR intakeMotor;
    Servo leftGate;
    Servo rightGate;
    double shootTime= -1;
    public boolean startingshooting3 = false;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = new RAWMOTOR(hardwareMap, "intakeMotor"/*"lf"*/);
        servoKicker = hardwareMap.get(Servo.class, "servoKicker");
        leftGate = hardwareMap.get(Servo.class, "leftGate");
        rightGate = hardwareMap.get(Servo.class, "rightGate");
        intakeMotor.reverseMotor();
    }
    public boolean openGate(){
        leftGate.setPosition(0.4); //0.4
        rightGate.setPosition(0.95); //0.95
        if (leftGate.getPosition() > 0.38 && leftGate.getPosition()<0.42  && rightGate.getPosition()>0.86&& rightGate.getPosition()<1.4){return true;}else return false;
    }
        //spin up intake and close the launcher gate
    public void prepareForIntaking(){
        closeGate();
        unKick();
        setPower(1);
    }
    public void unprepareIntake(){
        setPower(0);
    }
    public void startShoot3(){
        startingshooting3 = true;
    }

    //load ball into the launcher/ basically just launching it
//    public void loadBall(){
//        if (openGate()){
//            if (artifacts == 1){
//                if (kickBall()){
//                    closeGate();
//                    unKick();
//                }
//            }else closeGate();
//        }
//    }
    public boolean closeGate(){
        leftGate.setPosition(0.05);
        rightGate.setPosition(0.6);
        if (leftGate.getPosition() >-0.1&& leftGate.getPosition() <0.1 && rightGate.getPosition()>-0.1 && rightGate.getPosition()<0.1){return true;}else return false;
    }
    public double getCurrent(){
        return intakeMotor.getCurrentMilliamps();
    }
    public int countArtifacts(double current){
        if (current>maxCurrent+650 && artifacts == 0 && current >4100 && TIME.getTime()-timeWhenIntake > delay){
            maxCurrent=current;
            artifacts = 1;
        }
        else if (current>maxCurrent+650 && artifacts == 1 && current > 6000 && TIME.getTime()-timeWhenIntake > delay){
            maxCurrent=current;
            artifacts=2;
        }
        else if (current>maxCurrent+650 && artifacts == 2 && current > 6700 && TIME.getTime()-timeWhenIntake > delay){
            maxCurrent=current;
            artifacts=3;
        }
        return artifacts;
    }
    public boolean hasThreeArtifacts(double current){
        if(current > 3800){
            return true;
        }
        return false;
    }
    public double[] getGatePositions(){
        return new double[]{leftGate.getPosition(), rightGate.getPosition()};
    }

    public void setPower(double power) {
        intakeMotor.setPower(power);
    }
    public void stop() {
        intakeMotor.stop();
    }
    public boolean kickBall(){
        servoKicker.setPosition(0.64);
        if (servoKicker.getPosition() ==0.64){return true;}else return false;
    }
    public boolean unKick(){
        servoKicker.setPosition(0.2);
        if (servoKicker.getPosition() ==0.2){return true;}else return false;
    }


}
