package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;



/**
 * the default sdk telemetry is over complicated and the clear functions make no sense.
 * so I did this.
 */
public class SimplerTelemetry
{
    static String display;
    Telemetry telemetry;
    public SimplerTelemetry(Telemetry telemetry){
        clear();
        this.telemetry = telemetry;
    }

    /**
     * sets the telemetry to what the current display is
     */
    public void display()
    {
        telemetry.addLine(display);
        telemetry.update();
    }
    public static void addLine(String line){
        display+=line+"\n";
    }
    public static void addLine(){
        display+="\n";
    }
    public static void addData(String name,Object thing){
        display+=name+": "+String.valueOf(thing)+"\n";
    }
    public static void addArray(String name, double[] array){
        display+=name+": "+"\n";
        for(int i=0;i<array.length;i++){
            display+="    "+i+": "+array[i]+"\n";
        }
    }
    public static void clear(){
        display = "";
    }
    public void setDisplayFormat(Telemetry.DisplayFormat displayFormat){
        telemetry.setDisplayFormat(displayFormat);
    }
}
