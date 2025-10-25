package org.firstinspires.ftc.teamcode.hardwareClasses.motors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.controllers.Controller;
import org.firstinspires.ftc.teamcode.controllers.MaxSpeedController;
import org.firstinspires.ftc.teamcode.controllers.NullController;
import org.firstinspires.ftc.teamcode.controllers.PIDCon;
import org.firstinspires.ftc.teamcode.controllers.PositionController;
import org.firstinspires.ftc.teamcode.encoders.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MOTOR extends RAWMOTOR {
    private static final Logger log = LoggerFactory.getLogger(MOTOR.class);
    Controller extTorqueController = new NullController();
    PositionController positionController;

    public static class builder {
         double maxPower = 1;
         String name = "null name";
         double tolerance = 1;
         HardwareMap hardwareMap;
         Controller extTorqueController = new NullController();
         PositionController positionController;
         Encoder encoder;

         public builder(HardwareMap hardwareMap){
             this.hardwareMap = hardwareMap;
         }
        public double getTolerance()
        {
            return positionController.getTolerance();
        }
         public void setEncoder(Encoder encoder) {
             this.encoder = encoder;
             positionController.setEncoder(encoder);
             extTorqueController.setEncoder(encoder);
         }

         public void setExtTorqueController(Controller controller) {
             controller.setEncoder(encoder);
             extTorqueController = controller;
         }

         public void setPositionController(PositionController positionController) {
             positionController.setEncoder(encoder);
             if (this.positionController != null) {
                 positionController.setTolerance(getTolerance());
             }
             if (positionController instanceof MaxSpeedController) {
                 ((MaxSpeedController) positionController).setMaxAcceleration(maxPower);
             }
             this.positionController = positionController;
         }
         public builder setName(String name) {
            this.name = name;
            return this;
         }

         public void setTolerance(double tolerance) {
             this.tolerance = tolerance;
         }

         public MOTOR build() {
            return new MOTOR(hardwareMap,name,maxPower,tolerance,extTorqueController,positionController,encoder);
        }
     }


    public double getTargetPosition()
    {
        return positionController.getTargetPosition();


    }




    public void setTargetPosition(double targetPosition)
    {
        positionController.setTargetPosition(targetPosition);
    }
    public void runToPos(double tgtPos)
    {
        if (positionController.getTargetPosition() != tgtPos) {
            setTargetPosition(tgtPos);
        }
        runToTargetPosition();
    }

    public double getIntegral(){
        return ((PIDCon)positionController).getIntegral();
    }

    public double getTolerance()
    {
        return positionController.getTolerance();
    }
    public void runToTargetPosition()
    {
        setNetTorque(positionController.calculate());
    }
    public boolean targetReached()
    {
        return positionController.targetReached();
    }

    public MOTOR(HardwareMap hardwareMap, String name, double maxPower, double tolerance, Controller extTorqueController, PositionController positionController, Encoder encoder)
    {
        super(hardwareMap, name);
        this.maxPower = maxPower;
        this.setMaxPower(maxPower);
        this.positionController.setTolerance(tolerance);
        this.extTorqueController = extTorqueController;
        this.positionController = positionController;
        this.encoder = encoder;
    }

    public void setNetTorque(double power)
    {
        setPower(power - extTorqueController.calculate());
    }







    /**
     * hello
     *
     * @param targetPosition
     */

}
