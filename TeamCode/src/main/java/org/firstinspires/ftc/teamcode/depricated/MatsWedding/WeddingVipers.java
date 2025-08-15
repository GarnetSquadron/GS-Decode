package org.firstinspires.ftc.teamcode.depricated.MatsWedding;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.LimitedMotor;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;

public class WeddingVipers
{
    RAWMOTOR left;
    RAWMOTOR right;
    DcMotorEx lefty;
    int upperLimit = 1000;
    int lowerLimit = 0;
    double limitBuffer = 100;
    public double gravityPower = 0.1;

    public WeddingVipers(HardwareMap hardwareMap)
    {
        left = new RAWMOTOR(hardwareMap, "viperleft");
        right = new RAWMOTOR(hardwareMap, "viperright");
        lefty = hardwareMap.get(DcMotorEx.class, "viperleft");
    }

    public void setpower(double power)
    {
        if(
                (!tooLow()&&power<0)//true if trying to go down and not too low
                ||
                (!tooHigh()&&power>0)//true if trying to go higher and not too high
        ) {

        }
        else{
            power = 0;
        }


//        if (power > 0) {
//            power = Math.min(power,
//                    Math.max(upperLimit - getPos(), 0) / limitBuffer
//            );
//        } else {
//            power = Math.max(power,
//                    Math.min(getPos() - lowerLimit, 0) / limitBuffer);
//        }

        double actualPower = power + gravityPower;
        if (power == 0 && ExtraMath.ApproximatelyEqualTo(getPos(), 0, 100)) {
            actualPower = 0;
        }
        left.setPower(actualPower);
        right.setPower(-actualPower);


    }

    public boolean inRange()
    {
        return !tooLow() && !tooHigh();
    }

    public boolean tooLow()
    {
        return getPos() < lowerLimit;
    }

    public boolean tooHigh()
    {
        return getPos() > upperLimit;
    }

    public double getPos()
    {
        return lefty.getCurrentPosition();
    }

    public double getPower()
    {
        return left.getPower();
    }


}
