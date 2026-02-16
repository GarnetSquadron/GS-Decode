package org.firstinspires.ftc.teamcode.PurelyCalculators.controllers;

import org.firstinspires.ftc.teamcode.Telemetry.SectTelemetryAdder;
import org.firstinspires.ftc.teamcode.HardwareControls.encoders.SmoothVelocityEncoder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.ValueAtTimeStamp;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

public class PIDCon extends PositionController
{
    SectTelemetryAdder telemetry = new SectTelemetryAdder("TURRET");
    double kp, ki, kd;
    ValueAtTimeStamp prevPos;
    double integral = 0;

    public PIDCon(double kp, double ki, double kd)
    {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        resetState();
    }


    @Override
    public void resetState()
    {
        integral = 0;
        prevPos = new ValueAtTimeStamp(0, TIME.getTime());
    }

    @Override
    public void setTargetPosition(double targetPosition)
    {
        this.targetPosition = targetPosition;
        telemetry.addLine("reset!!");
    }
    public double getIntegral(){
        return integral;
    }

    @Override
    public double calculate()
    {
        double error = getDistanceToTarget();
        if (ki != 0) {
            double currentTime = TIME.getTime();
            double increment = -(error)*(prevPos.getTimeStamp()-currentTime);
            integral += increment;//ExtraMath.integration.trapazoid(prevPos, new ValueAtTimeStamp(error, currentTime));
            telemetry.addData("increment",increment);
            prevPos = new ValueAtTimeStamp(error, currentTime);
        }
        double velocity;
        if(encoder.getClass()== SmoothVelocityEncoder.class){
            velocity = ((SmoothVelocityEncoder)encoder).getAverageVelocity();
        }
        else
            velocity = encoder.getVelocity();
        return kp * error + ki * integral + kd * velocity;
    }
}
