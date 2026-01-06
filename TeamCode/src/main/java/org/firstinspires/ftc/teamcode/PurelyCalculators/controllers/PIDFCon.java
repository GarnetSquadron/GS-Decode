package org.firstinspires.ftc.teamcode.PurelyCalculators.controllers;

import org.firstinspires.ftc.teamcode.PurelyCalculators.ExtraMath;
import org.firstinspires.ftc.teamcode.HardwareControls.encoders.SmoothVelocityEncoder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.ValueAtTimeStamp;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

import java.util.function.DoubleSupplier;

public class PIDFCon extends PositionController
{
    double kp, ki, kd, kf;
    ValueAtTimeStamp prevPos;
    double integral;
    DoubleSupplier velocitySupplier;

    public PIDFCon(double kp, double ki, double kd, double kf, DoubleSupplier velocitySupplier)
    {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kf = kf;
        this.velocitySupplier = velocitySupplier;
        reset();
    }

    public void reset() {
        integral = 0;
        double now = TIME.getTime();
        double currentError = getDistanceToTarget();
        prevPos = new ValueAtTimeStamp(currentError, now);
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
    public double calculate() // take in lamda
    {
        double error = getDistanceToTarget();
        if (ki != 0) {
            double currentTime = TIME.getTime();
            integral += ExtraMath.integration.trapazoid(prevPos, new ValueAtTimeStamp(error, currentTime));
            prevPos = new ValueAtTimeStamp(error, currentTime);
        }
        double velocity;
        if (velocitySupplier != null) {
            velocity = velocitySupplier.getAsDouble();
        } else if (encoder instanceof SmoothVelocityEncoder) {
            velocity = ((SmoothVelocityEncoder) encoder).getAverageVelocity();
        } else {
            velocity = encoder.getVelocity();
        }



        return kp * error + ki * integral - kd * velocity + kf * velocitySupplier.getAsDouble();
    }
}
