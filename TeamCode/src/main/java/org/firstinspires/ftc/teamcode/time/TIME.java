package org.firstinspires.ftc.teamcode.time;

public class TIME
{
    /**
     * gets the time in seconds
     * @return time in seconds
     */
    public static double getTime()
    {
        return (double) System.nanoTime() / 1.0E9;
    }
}
