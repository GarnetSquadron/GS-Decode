package org.firstinspires.ftc.teamcode.controllers;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.SmoothVelocityEncoder;
import org.firstinspires.ftc.teamcode.ValueAtTimeStamp;
import org.firstinspires.ftc.teamcode.time.TIME;

public class PIDFCon extends PositionController
{
    double kp, ki, kd, kf;
    ValueAtTimeStamp prevPos;
    double integral;

    public PIDFCon(double kp, double ki, double kd, double kf)
    {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kf = kf;
        reset();
    }

    public void reset()
    {
        integral = 0;
        prevPos = new ValueAtTimeStamp(0, TIME.getTime());
    }

    @Override
    public void setTargetPosition(double targetPosition)
    {
        this.targetPosition = targetPosition;
        reset();
    }
    public double getIntegral(){
        return integral;
    }

    @Override
    public double calculate(double aV)
    {
        double error = getDistanceToTarget();
        if (ki != 0) {
            double currentTime = TIME.getTime();
            integral += ExtraMath.integration.trapazoid(prevPos, new ValueAtTimeStamp(error, currentTime));
            prevPos = new ValueAtTimeStamp(error, currentTime);
        }
        double velocity;
        if(encoder.getClass()== SmoothVelocityEncoder.class){
            velocity = ((SmoothVelocityEncoder)encoder).getAverageVelocity();
        }
        else
            velocity = encoder.getVelocity();


        return kp * error + ki * integral + kd * velocity + kf *aV;
    }
}
