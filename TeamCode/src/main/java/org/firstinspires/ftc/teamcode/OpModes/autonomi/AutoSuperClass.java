package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * this class allows you enter the process(ie the instructions run every loop) for each step of the auto in the form of lambdas.
 */
public abstract class AutoSuperClass extends OpMode
{
    Timer pathTimer = new Timer();
    public int currentStep;
    Runnable[] steps;


    public void initSteps(Runnable... steps)
    {
        this.steps = steps;
    }
    public void setCurrentStep(int step)
    {
        currentStep = step;
        pathTimer.resetTimer();
    }
    public void nextStep(){
        setCurrentStep(currentStep + 1);
    }
    public void  updateSteps(){
        if(currentStep<steps.length&&currentStep>=0){steps[currentStep].run();}
    }
    @Override
    public void loop(){
        updateSteps();
    }
}
