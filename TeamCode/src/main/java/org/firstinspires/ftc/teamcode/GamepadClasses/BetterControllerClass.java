package org.firstinspires.ftc.teamcode.GamepadClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;
import java.util.Map;

public class BetterControllerClass
{
    Gamepad gamepad;
    String[] buttonNames;
    public Map<String,Boolean> prevValues = new HashMap<>();
    public Map<String,Boolean> risingEdges = new HashMap<>();
    public Map<String,Boolean> fallingEdges = new HashMap<>();
    public Map<String, Boolean> toggles = new HashMap<>();
    public BetterControllerClass(Gamepad gamepad, String[] buttonNames)
    {
        this.gamepad = gamepad;
        this.buttonNames = buttonNames;
        for(String buttonName: buttonNames){
            prevValues.put(buttonName,false);
        }
    }
    public BetterControllerClass(Gamepad gamepad)
    {
        this(gamepad,new String[]{"a", "b", "x", "y","right_bumper","left_bumper","left_trigger","right_trigger"});
    }

    /**
     * updates just a specific button. I originally intended this as a way to make the other update
     * function a little more compartmentalized, and to make it easier to extend the function.
     * @param buttonName the name of said specific button
     */
    public void update(String buttonName){
        boolean prevValue = Boolean.TRUE.equals(prevValues.get(buttonName));
        boolean toggle = Boolean.TRUE.equals(toggles.get(buttonName));
        try
        {
            boolean buttonPressed = buttonName.contains("trigger")?
                    ((Float) Gamepad.class.getField(buttonName).get(gamepad))==1:
                    (Boolean) Gamepad.class.getField(buttonName).get(gamepad);
            boolean valChanged = prevValue^buttonPressed;
            prevValues.put(buttonName,buttonPressed);
            risingEdges.put(buttonName,valChanged&&buttonPressed);
            fallingEdges.put(buttonName,valChanged&&!buttonPressed);
            toggles.put(buttonName,buttonPressed^toggle);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    }

    /**
     * updates all the buttons
     */

    public void update()
    {
        for(String buttonName:buttonNames){
            update(buttonName);
        }
    }
    public boolean getCurrentValue(String buttonName){
        return Boolean.TRUE.equals(prevValues.get(buttonName));
    }
    public boolean getRisingEdge(String buttonName){
        return Boolean.TRUE.equals(risingEdges.get(buttonName));
    }
    public boolean getFallingEdge(String buttonName){
        return Boolean.TRUE.equals(fallingEdges.get(buttonName));
    }
    public boolean getToggleValue(String buttonName){
        return Boolean.TRUE.equals(toggles.get(buttonName));
    }
}