package org.firstinspires.ftc.teamcode.Subsystems.hardwareClasses.motors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Encoder;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.Controller;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.MaxSpeedController;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.NullController;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.PIDCon;
import org.firstinspires.ftc.teamcode.Subsystems.controllers.PositionController;

public class MOTOR extends RAWMOTOR {
    Controller extTorqueController = new NullController();
    PositionController positionController;
    public MOTOR(HardwareMap hardwareMap, String name){
        super(hardwareMap, name);
        setEncoderToMotorEncoder();
        setPID(1,0,0);
    }
    @Override
    public void setEncoder(Encoder encoder){
        this.encoder = encoder;
        positionController.setEncoder(encoder);
        extTorqueController.setEncoder(encoder);
    }
    public void setExtTorqueController(Controller controller){
        controller.setEncoder(encoder);
        extTorqueController = controller;
    }
    public void setPositionController(PositionController positionController){
        positionController.setEncoder(encoder);
        if(this.positionController!=null){
            positionController.setTolerance(getTolerance());
        }
        if(positionController instanceof MaxSpeedController) {
            ((MaxSpeedController)positionController).setMaxAcceleration(maxPower);
        }
        this.positionController = positionController;
    }
    public void setPID(double kp, double ki, double kd){
        setPositionController(new PIDCon(kp,ki,kd));
    }
    public void setTolerance(double tolerance){
        positionController.setTolerance(tolerance);
    }
    public double getTolerance(){
        return positionController.getTolerance();
    }

    /**
     * hello
     * @param targetPosition
     */
    public void setTargetPosition(double targetPosition){
        positionController.setTargetPosition(targetPosition);
    }
    public double getTargetPosition(){
        return positionController.getTargetPosition();
    }
    public void runToTargetPosition(){
        setNetTorque(positionController.calculate());
    }
    public void setNetTorque(double power){
        setPower(power-extTorqueController.calculate());
    }
    public void runToPos(double tgtPos) {
        if(positionController.getTargetPosition()!=tgtPos){
            setTargetPosition(tgtPos);
        }
        runToTargetPosition();
    }
    public boolean targetReached(){
        return positionController.targetReached();
    }

}
