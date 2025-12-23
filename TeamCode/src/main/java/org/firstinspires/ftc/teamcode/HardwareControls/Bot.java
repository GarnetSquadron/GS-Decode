package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.SimplerTelemetry;

public class Bot
{
    public Launcher launcher;
    public Intake intake;
    public Turret turret;
    public LaunchHandler launchHandler;
    public ServoController servoController;
    public Bot(HardwareMap hardwareMap){
        launcher = new Launcher(hardwareMap);
        intake = new Intake(hardwareMap);
        launchHandler = new LaunchHandler();
        turret = launcher.turret;
        servoController = hardwareMap.get(ServoController.class, "Control Hub");
    }
    public void init(){

    }
    public LaunchPhase update(double minVel,double maxVel){
        return launchHandler.update(minVel,maxVel);
    }
    public enum LaunchPhase
    {
        NULL,
        SPINNING_UP,
        GATE_OPENING,
        RELEASING_BALLS,
        KICKING_SERVO,
        SHUTDOWN
    }
    public class LaunchHandler
    {
        public LaunchPhase launchPhase = LaunchPhase.NULL;
        public boolean isPausedToSpinUp = false;
        public double pauseStartTime = -1;
        double phaseStartTime = -1;
        public LaunchHandler(){
            phaseStartTime = -1;
        }
        public double getElapsedTime(){
            return TIME.getTime()- phaseStartTime;
        }
        public void initLaunch(){
            launchPhase = LaunchPhase.SPINNING_UP;
            intake.closeGate();
            phaseStartTime = TIME.getTime();
        }
        public LaunchPhase update(double minVel, double maxVel){
            boolean velInRange = false;
            SimplerTelemetry.addLine("start of loop");
            // basic idea is that the sequence will pause if the flywheel is not up to speed, and then attempt to get back up to speed
            if(launchPhase!=LaunchPhase.NULL&&launchPhase!=LaunchPhase.KICKING_SERVO&&launchPhase!=LaunchPhase.SHUTDOWN){//once you get to kicking the servo its far gone tbh
                velInRange = launcher.spinFlyWheelWithinRange(minVel,maxVel);
                SimplerTelemetry.addData("vel in range",velInRange);
                if(!velInRange){
                    isPausedToSpinUp = true;
                    pauseStartTime = TIME.getTime();
                    intake.stop();
                }
                if(isPausedToSpinUp){
                    SimplerTelemetry.addLine("paused");
                    intake.stop();
                    if(launcher.getInPerSec()>(minVel+maxVel)/2){
                        isPausedToSpinUp = false;
                        //change the phase start time so that there is the correct time remaining in that phase.
                        phaseStartTime = TIME.getTime();
                    }
                    //if paused, do not carry out the instructions in the switch case
                    return launchPhase;
                }
            }
            SimplerTelemetry.addLine("going to switch case");
            switch (launchPhase){
                case NULL: {
                    break;
                }
                case SPINNING_UP:{
                    intake.stop();
                    if (launcher.getInPerSec()>(minVel+maxVel)/2)//wait for it to be in the right range
                    {
                        launchPhase = LaunchPhase.GATE_OPENING;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case GATE_OPENING:{
                    intake.openGate();
                    if (getElapsedTime() > 0.5)
                    {
                        launchPhase = LaunchPhase.RELEASING_BALLS;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case RELEASING_BALLS:{
                    intake.openGate();
                    intake.setPower(1);
                    SimplerTelemetry.addLine("releasing balls!");
                    if(getElapsedTime() > 0.4){
                        launchPhase = LaunchPhase.KICKING_SERVO;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case KICKING_SERVO:{
                    intake.openGate();
                    intake.kickBall();
                    if(getElapsedTime()>0.5){
                        launchPhase = LaunchPhase.SHUTDOWN;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case SHUTDOWN:{
                    intake.unKick();
                    intake.stop();
                    intake.closeGate();
                    launcher.setPower(0);
                    launchPhase = LaunchPhase.NULL;
                    break;
                }
            }
            return launchPhase;
        }
    }
}
