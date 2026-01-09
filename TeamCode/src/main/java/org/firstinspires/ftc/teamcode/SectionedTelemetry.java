package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.LinkedHashMap;
import java.util.Map;

public class SectionedTelemetry
{
    public static int telemetryWidth = 30;
    static LinkedHashMap<String,String> sections = new LinkedHashMap<>();
    static final String defaultKey = "default";
    Telemetry telemetry;

    public SectionedTelemetry(Telemetry telemetry)
    {
        clear();
        this.telemetry = telemetry;
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        sections = new LinkedHashMap<>();
//        sections.put(defaultKey,"");
    }
    public static void addLine(String line,String key){
        sections.merge(key,line+"\n",(u,v)-> u+v);
    }
    public static void addLine(String line){
        addLine(line, defaultKey);
    }
    public static void addLine(){
        addLine("", defaultKey);
    }
    public static void addData(String name,Object thing,String key){
        addLine(name+": "+ thing,key);
    }
    public static void addData(String name,Object thing){
        addData(name,thing, defaultKey);
    }
    public static void addArray(String name, double[] array,String key){
        addLine( name+": "+"\n",key);
        for(int i=0;i<array.length;i++){
            addLine("    "+i+": "+array[i]+"\n",key);
        }
    }
    public static void addArray(String name, double[] array){
        addArray(name,array, defaultKey);
    }
    public static void clear(){
        sections = new LinkedHashMap<>();
    }
    public static String centerText(String text,String patternUnit){
        int patternLength = telemetryWidth/patternUnit.length();
//        int halfPatternLength = sideLength;
        String spaceFiller = patternUnit.repeat(patternLength);
        return spaceFiller.substring(0,(telemetryWidth-text.length())/2)+text+spaceFiller.substring((telemetryWidth+text.length())/2);
    }
    public void update(){
        for(Map.Entry<String,String> entry:sections.entrySet()){
            if(entry.getKey()!=defaultKey) telemetry.addLine(centerText(entry.getKey(),"="));
            telemetry.addLine(entry.getValue());
            telemetry.addLine();
        }
        telemetry.update();
    }
    public void setDisplayFormat(Telemetry.DisplayFormat displayFormat){
        telemetry.setDisplayFormat(displayFormat);
    }
}
