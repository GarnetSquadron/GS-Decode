package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

public class ValueAtTimeStamp {
    double value;
    double timeStamp;
    public ValueAtTimeStamp(double value, double timeStamp){
        this.value = value;
        this.timeStamp = timeStamp;
    }
    public double getValue(){
        return value;
    }
    public double getTimeStamp(){
        return timeStamp;
    }

}
