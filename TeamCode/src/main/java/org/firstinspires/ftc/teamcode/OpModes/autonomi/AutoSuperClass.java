package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * this class allows you write the process(ie the instructions run every loop) for each step of the auto in the form of lambdas.
 */
public abstract class AutoSuperClass extends OpMode
{
    public Timer pathTimer = new Timer();
    public int currentStep;
    Runnable[] steps;

    public double[] times;


    public void initSteps(Runnable... steps)
    {
        this.steps = steps;
        times = new double[steps.length];
    }
    public void setCurrentStep(int step)
    {
        times[currentStep] = pathTimer.getElapsedTime();
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
