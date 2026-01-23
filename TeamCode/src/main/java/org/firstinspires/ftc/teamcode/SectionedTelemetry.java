package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * anytime you want to use this, you need to initialize it like this:
 * {@code SectionedTelemetry telemetry = new SectionedTelemetry(super.telemetry);}
 * and then when you want to add stuff to it you do it like this:
 * {@code
 * telemetry.addLine("telemetry output");
 * telemetry.addData("name",thing);
 * }
 * once you have everything added, you need to update everything and display it:
 * {@code
 * telemetry.updateAll();
 * telemetry.display();
 * }
 * </pre>
 *
 */
public class SectionedTelemetry extends SimplerTelemetry
{
    public static int telemetryWidth = 30;
    static LinkedHashMap<String,String> sections = new LinkedHashMap<>();
    static final String defaultKey = "default";
    Telemetry telemetry;

    public SectionedTelemetry(Telemetry telemetry)
    {
        super(telemetry);
        clearAll();
        this.telemetry = telemetry;
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        sections = new LinkedHashMap<>();
        sections.put(defaultKey,"");
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
    public static <T> void addArray(String name, T[] array,String key){
        addLine( name+": "+"\n",key);
        for(int i=0;i<array.length;i++){
            addLine("    "+i+": "+array[i]+"\n",key);
        }
    }
    public static void addArray(String name, double[] array){
        addArray(name,array, defaultKey);
    }
    public static void clearAll(){
        sections.clear();
    }
    public static void clearSection(String key){
        sections.remove(key);
    }
    public static String centerText(String text,String patternUnit){
        int patternLength = telemetryWidth/patternUnit.length();
//        int halfPatternLength = sideLength;
        String spaceFiller = patternUnit.repeat(patternLength);
        return spaceFiller.substring(0,(telemetryWidth-text.length())/2)+text+spaceFiller.substring((telemetryWidth+text.length())/2);
    }
    public void updateAllSections(){
        updateSection();
        for(Map.Entry<String,String> entry:sections.entrySet()){
            if(entry.getKey()!=defaultKey)
            {
                updateSection(entry.getKey());
            }
        }
    }
    public void updateSection(String key){
        display+=centerText(key, "=")+"\n"+sections.get(key)+"\n";
    }
    public void updateSection(){
        display+=sections.get(defaultKey)+"\n";
    }
    @Override
    public void display(){
        super.display();
        super.clear();
    }
    public void setDisplayFormat(Telemetry.DisplayFormat displayFormat){
        telemetry.setDisplayFormat(displayFormat);
    }
}
