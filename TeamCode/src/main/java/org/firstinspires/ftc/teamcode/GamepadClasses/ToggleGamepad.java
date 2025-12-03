package org.firstinspires.ftc.teamcode.GamepadClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;
import java.util.Map;

/**
 * planning on doing some hashmap stuff with this but not rn
 */
public class ToggleGamepad extends BetterControllerClass
{
    String[] buttonNames;
    public Map<String, Boolean> toggles = new HashMap<>();
    public Map<String,Boolean> prevValues = new HashMap<>();

    public ToggleGamepad(Gamepad gamepad)
    {
        this(gamepad,new String[]{"a", "b", "x", "y","right_bumper","left_bumper","left_trigger","right_trigger"});
    }
    public ToggleGamepad(Gamepad gamepad, String[] buttonNames){
        super(gamepad);
        this.buttonNames = buttonNames;
        for(String buttonName: buttonNames){
            toggles.put(buttonName,false);
            prevValues.put(buttonName,false);
        }
    }

    /**
     * iterates over the buttons, and
     */
    public void update()
    {
        for(String buttonName:buttonNames){
            boolean toggle = Boolean.TRUE.equals(toggles.get(buttonName));
            boolean prevValue = Boolean.TRUE.equals(prevValues.get(buttonName));
            if(buttonName.contains("trigger")){
                try
                {
                    boolean buttonPressed = ((Float) Gamepad.class.getField(buttonName).get(gamepad))==1;
                    boolean valTurnedOn = (prevValue^buttonPressed)&&buttonPressed;
                    toggles.put(buttonName,buttonPressed);
                } catch (NoSuchFieldException | IllegalAccessException ignored){}
            }
            try
            {
                boolean buttonPressed = (Boolean) Gamepad.class.getField(buttonName).get(gamepad);
                boolean valTurnedOn = (prevValue^buttonPressed)&&buttonPressed;
                toggles.put(buttonName,valTurnedOn^toggle);
                prevValues.put(buttonName,buttonPressed);


            } catch (NoSuchFieldException | IllegalAccessException ignored)
            {
            }
        }
    }
    public boolean getToggleValue(String buttonName){
        return Boolean.TRUE.equals(toggles.get(buttonName));
    }


}
