package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

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
        double phaseStartTime;
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
        public LaunchPhase update(double minVel,double maxVel){
            boolean velInRange = false;
            if(launchPhase!=LaunchPhase.NULL&&launchPhase!=LaunchPhase.KICKING_SERVO&&launchPhase!=LaunchPhase.SHUTDOWN){//once you get to kicking the servo its far gone tbh
                velInRange = launcher.spinFlyWheelWithinRange(minVel,maxVel);
//                if(!velInRange){
//                    launchPhase = LaunchPhase.SPINNING_UP;
//                    phaseStartTime = TIME.getTime();
//                    intake.stop();
//                }
            }
            switch (launchPhase){
                case NULL: {
                    break;
                }
                case SPINNING_UP:{
                    intake.stop();
                    if (velInRange)
                    {
                        launchPhase = (intake.gateIsOpen()) ?LaunchPhase.RELEASING_BALLS:LaunchPhase.GATE_OPENING;
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
                    intake.setPower(1);
                    if(getElapsedTime() > 0.4){
                        launchPhase = LaunchPhase.KICKING_SERVO;
                        phaseStartTime = TIME.getTime();
                    }
                    break;
                }
                case KICKING_SERVO:{
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
