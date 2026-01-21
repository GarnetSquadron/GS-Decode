package org.firstinspires.ftc.teamcode.PurelyCalculators.time;

import java.util.function.DoubleSupplier;

/**
 * easier to use than util.Timer and has slightly different functionality. you can get timeover() to
 * see if the time is up, but this cannot schedule tasks like utils.Timer can
 */

public class TTimer
{
    double duration;
    double startTime;
    boolean timeStarted;

    public TTimer()
    {
        duration = 0;
        startTime = TIME.getTime();
        timeStarted = false;
    }

    public void StartTimer(double duration)
    {
        startTime = TIME.getTime();
        timeStarted = true;
        this.duration = duration;
    }

    public boolean timestarted()
    {
        return timeStarted;
    }

    public boolean timeover()
    {
        return timeLeft()<0;
    }

    public double timeLeft()
    {
        return startTime + duration - TIME.getTime();
    }

}
