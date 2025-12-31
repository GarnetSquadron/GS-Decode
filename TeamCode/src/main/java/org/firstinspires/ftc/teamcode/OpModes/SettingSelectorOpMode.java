package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.SimplerTelemetry;

import java.util.HashMap;

import kotlin.Pair;

public abstract class SettingSelectorOpMode extends OpMode
{
    public SimplerTelemetry telemetry;
    private final Pair<String[],String>[] settingsMap;
    private final int[] selectorPositions;
    public final HashMap<String,String> selections;
    int settingIndex = 0;
    double lastResetTime;

    public SettingSelectorOpMode(Pair<String[],String>[] settingsMap) {
        this(settingsMap,new HashMap<>());
    }

    /**
     * if you want to set default selections, input this. Otherwise, it defaults to the first in every list
     * @param settingsMap
     * @param selections
     */
    public SettingSelectorOpMode(Pair<String[],String>[] settingsMap,HashMap<String,String> selections) {
        this.telemetry = new SimplerTelemetry(super.telemetry);
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        this.settingsMap = settingsMap;
        selectorPositions = new int[settingsMap.length];
        this.selections = new HashMap<>();// I don't add elements to the hashmap until after the settings have been confirmed
        //instead, I first store the selections in a data type that is easier to iterate over - an array of Pairs:
        for(int i = 0; i< settingsMap.length; i++){//iterating over the different settings
            Pair<String[],String> setting = settingsMap[i];
            String selectedOption = selections.get(setting.component2());
            if(selectedOption==null){
                selectorPositions[i] = 0;
            }else {
                // find the index of the selected option
                for (int j = 0; j < setting.component1().length; j++)
                {//iterating over the different options for each setting
                    if (setting.component1()[j] == selectedOption)
                    {
                        selectorPositions[i] = j;
                    }
                }
            }

        }
        lastResetTime = TIME.getTime();
    }
    String blink(String thing){
        return (Math.sin((TIME.getTime()-lastResetTime)* (double) 12)>0) ? thing : " ";
    }
    @Override
    public void init_loop() {
        for(int i = 0; i< settingsMap.length; i++){
            String display = "";
            //String selector = "";
            String leftSelector = settingIndex==i?blink(">"):">";
            String rightSelector = settingIndex==i?blink("<"):"<";
            for (int j = 0; j< settingsMap[i].component1().length; j++)
            {
                String option = settingsMap[i].component1()[j];
                display += (selectorPositions[i]==j? leftSelector:" ") + option + ((selectorPositions[i]==j)? rightSelector:" ")+"   ";
//                for (int k = 0; k < option.length(); k++)
//                {
//                    selector += (k == option.length()/2&& selectorPositions[i]==j) && (settingIndex!=i||Math.sin((TIME.getTime()-lastResetTime)*12)>0) ? "^" : " ";
//                }
                //selector += "     ";
            }
            telemetry.addLine(settingsMap[i].component2()+":");
            telemetry.addLine(display);
            telemetry.addLine(/*selector*/);
        }
        if(gamepad1.dpadDownWasPressed()&&settingIndex< settingsMap.length-1){
            settingIndex++;
            lastResetTime = TIME.getTime();
        }
        if(gamepad1.dpadUpWasPressed()&&settingIndex>0){
            settingIndex--;
            lastResetTime = TIME.getTime();
        }
        if(gamepad1.rightBumperWasPressed()&&selectorPositions[settingIndex]< settingsMap[settingIndex].component1().length-1){
            selectorPositions[settingIndex]++;
            lastResetTime = TIME.getTime();
        }
        if(gamepad1.leftBumperWasPressed()&&selectorPositions[settingIndex]>0){
            selectorPositions[settingIndex]--;
            lastResetTime = TIME.getTime();
        }
        telemetry.update();
        telemetry.clear();
    }
    @Override
    public void start(){
        //selections = new String[settingsArray.length];
        for(int i = 0; i< settingsMap.length; i++){
            selections.put(settingsMap[i].component2(),settingsMap[i].component1()[selectorPositions[i]]);
        }
    }
}
