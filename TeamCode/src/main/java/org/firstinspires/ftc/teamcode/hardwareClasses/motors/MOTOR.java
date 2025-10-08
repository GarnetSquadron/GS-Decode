package org.firstinspires.ftc.teamcode.hardwareClasses.motors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.controllers.Controller;
import org.firstinspires.ftc.teamcode.controllers.MaxSpeedController;
import org.firstinspires.ftc.teamcode.controllers.NullController;
import org.firstinspires.ftc.teamcode.controllers.PIDCon;
import org.firstinspires.ftc.teamcode.controllers.PositionController;
import org.firstinspires.ftc.teamcode.encoders.Encoder;

public class MOTOR extends RAWMOTOR
{
    Controller extTorqueController = new NullController();
    PositionController positionController;

    public static class builder {
         double maxPower = 1;

         Controller extTorqueController = new NullController();
         PositionController positionController;
         Encoder encoder;
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

         public void setTolerance(double tolerance) {
             positionController.setTolerance(tolerance);
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

    public MOTOR(HardwareMap hardwareMap, String name)
    {
        super(hardwareMap, name);

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
