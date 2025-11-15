package org.firstinspires.ftc.teamcode.GamepadClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;
import java.util.Map;

/**
 * planning on doing some hashmap stuff with this but not rn
 */
public class ToggleGamepad extends BetterControllerClass
{
    String[] buttonNames = new String[]{"a", "b", "x", "y"};
    public Map<String, Boolean> toggles = new HashMap<>();
    public Map<String,Boolean> prevValues = new HashMap<>();

    public ToggleGamepad(Gamepad gamepad)
    {
        super(gamepad);
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
            try
            {
                boolean buttonPressed = (Boolean) Gamepad.class.getField(buttonName).get(gamepad);
                boolean valHasChanged = prevValue^buttonPressed;
                toggles.put(buttonName,valHasChanged^toggle);
                prevValues.put(buttonName,buttonPressed);


            } catch (NoSuchFieldException | IllegalAccessException ignored)
            {
            }
        }
    }


}
