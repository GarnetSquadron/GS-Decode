package org.firstinspires.ftc.teamcode.PurelyCalculators.time;

/**
 * basically pedro's timer, but I use the superior unit of time so its better.
 */
public class StopWatch
{
    double startTime;
    public StopWatch(){
        reset();
    }
    public void reset(){
        startTime = TIME.getTime();
    }
    public double getElapsedTime(){
        return TIME.getTime()-startTime;
    }
}
