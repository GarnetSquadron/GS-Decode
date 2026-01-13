package org.firstinspires.ftc.teamcode.OpModes;

import org.firstinspires.ftc.teamcode.SectionedTelemetry;

/**
 * pretty much the sole purpose of this class is to let me avoid typing the same string for 15 lines in a row
 */
public class SectTelemetryAdder
{
    String defaultKey;
    public SectTelemetryAdder(String defaultKey){
        this.defaultKey = defaultKey;
    }
    public void addLine(String line){
        SectionedTelemetry.addLine(line, defaultKey);
    }
    public void addLine(){
        SectionedTelemetry.addLine("", defaultKey);
    }
    public void addData(String name,Object thing){
        SectionedTelemetry.addData(name,thing, defaultKey);
    }
    public void addArray(String name, double[] array){
        SectionedTelemetry.addArray(name,array, defaultKey);
    }
}
