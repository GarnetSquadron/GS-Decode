package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.HardwareControls.encoders.encoders.Encoder;
import org.firstinspires.ftc.teamcode.HardwareControls.hardwareClasses.motors.MOTOR;
import org.firstinspires.ftc.teamcode.PurelyCalculators.ExtraMath;
import org.firstinspires.ftc.teamcode.PurelyCalculators.enums.AngleUnitV2;

public class Turret
{
    public MOTOR turretRot;
    final double[] turretRange = {Math.toRadians(-95),Math.toRadians(75)};
    public Turret(HardwareMap hardwareMap){
        turretRot = new MOTOR(hardwareMap, "turretRot");
        turretRot.setMaxPower(0.7);
        turretRot.getEncoder().setCPR(384.5*4.5);//motor is 435, which has a 384.5 ticks per rotation. The belt is belted at a 4.5:1 ratio
        turretRot.getEncoder().scaleToAngleUnit(AngleUnitV2.RADIANS);
        turretRot.reverseMotor();
        turretRot.setPID(7,0,0);
    }

    public void setPower(double power) {
        turretRot.setPower(power);
    }
    public Encoder getEncoder(){
        return turretRot.getEncoder();
    }
    public String getMotorType(){
        return turretRot.getMotorType();
    }
    /**
     * remember to input radians of course
     */
    public void setRotation(double rotation) {
        turretRot.runToPos(getRotation(rotation));
    }
    public double getRotation(double rotation) {
        double rangeSize =  turretRange[1]-turretRange[0];

        // theta
        rotation =ExtraMath.theRealMod((rotation - turretRange[0]),ExtraMath.Tau)+ ExtraMath.theRealMod(turretRange[0],ExtraMath.Tau)-ExtraMath.Tau;//rotation goes until it reaches 360-angleRange[0] at which point it goes to -angleRange[0]
        if(rotation< turretRange[1]){
            return rotation;
        }else {
            return turretRange[0]+(turretRange[0]-rotation+ExtraMath.Tau)*rangeSize/(ExtraMath.Tau-rangeSize);
        }
    }

    /**
     * aims the turret towards the goal
     * @param goalPos
     * @param botPos
     * @param heading the heading in radians
     * @return
     */
    public double aimTowardsGoal(double[] goalPos, double[] botPos, double heading){
        double rotAngle = Math.atan((goalPos[1] - botPos[1]) /(goalPos[0]-botPos[0]))-heading;
        setRotation(rotAngle);
        return rotAngle;
    }
    public void zero(){
        turretRot.getEncoder().setPosition(0);
    }
}
