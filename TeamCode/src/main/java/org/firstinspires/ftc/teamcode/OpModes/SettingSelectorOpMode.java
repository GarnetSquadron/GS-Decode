package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

import java.util.HashMap;

import kotlin.Pair;

public abstract class SettingSelectorOpMode extends OpMode
{
    private final Pair<String[],String>[] settingsMap;
    private final int[] selectorPositions;
    public final HashMap<String,String> selections;
    int settingIndex = 0;
    double lastResetTime;
    private final static String[] MESSAGE = {
            "Use the d-pad to move the cursor.",
            "Press right bumper to select.",
            "Press left bumper to go back."
    };

    public SettingSelectorOpMode(Pair<String[],String>[] settingsMap) {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        this.settingsMap = settingsMap;
        selectorPositions = new int[settingsMap.length];
        selections = new HashMap<>();
        for(int i = 0; i< settingsMap.length; i++){
            selectorPositions[i] = 0;
            //selections[i] = settingsArray[i][0];
        }
        lastResetTime = TIME.getTime();
    }

    @Override
    public void init_loop() {
        for(int i = 0; i< settingsMap.length; i++){
            String display = "";
            String selector = "";
            for (int j = 0; j< settingsMap[i].component1().length; j++)
            {
                String option = settingsMap[i].component1()[j];
                display += option + "  |  ";
                for (int k = 0; k < option.length(); k++)
                {
                    selector += (k == option.length()/2&& selectorPositions[i]==j) && (settingIndex!=i||Math.sin((TIME.getTime()-lastResetTime)*12)>0) ? "^" : " ";
                }
                selector += "  |  ";
            }
            telemetry.addLine(settingsMap[i].component2()+":");
            telemetry.addLine(display);
            telemetry.addLine(selector);
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
    }
    @Override
    public void start(){
        //selections = new String[settingsArray.length];
        for(int i = 0; i< settingsMap.length; i++){
            selections.put(settingsMap[i].component2(),settingsMap[i].component1()[selectorPositions[i]]);
        }
    }
}
