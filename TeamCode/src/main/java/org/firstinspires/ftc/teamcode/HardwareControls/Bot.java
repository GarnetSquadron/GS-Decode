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
    public boolean update(double minVel,double maxVel){
        return launchHandler.update(minVel,maxVel);
    }
    public class LaunchHandler
    {
        double power = 1;
        double releaseStartTime;
        double startGateTime;
        double targetVel = 0;
        public boolean launchingBalls = false;
        public boolean releaseBalls = false;
        public boolean waitingForGate = false;
        public LaunchHandler(){
            releaseStartTime = -1;
        }
        public void initLaunch(){
            launchingBalls = true;
            releaseBalls = false;
            intake.closeGate();
        }
        public boolean update(double minVel,double maxVel){
            double elapsedTime = TIME.getTime()-releaseStartTime;
            boolean fastEnough = minVel<launcher.getFlywheelEncoder().getVelocity();
            boolean ret = releaseBalls;
            if(launchingBalls){
                if(launcher.spinFlyWheelWithinRange(minVel,maxVel)){
                    if (!releaseBalls)//if its just gotten up to speed
                    {//I thought I might as well take the absolute value in case its reversed
                        if(!waitingForGate){//initiate opening the gate
                            waitingForGate = true;
                            startGateTime = TIME.getTime();
                            intake.openGate();
                            intake.setPower(0);
                            return ret;
                        }
                        if(TIME.getTime()- startGateTime >0.5){// once its been long enough, the gate is open and we can run the intake
                            releaseBalls = true;
                            releaseStartTime = TIME.getTime();
                            intake.setPower(1);
                        }
                        return ret;
                    }
                    double timeFor3rdBallToGetUnStuck = 0.5;
                    intake.setPower(1);
                    if (elapsedTime < timeFor3rdBallToGetUnStuck) return ret;
                    if (elapsedTime > timeFor3rdBallToGetUnStuck + 0.5)
                    {
                        intake.unKick();
                        intake.stop();
                        intake.closeGate();
                        launcher.setPower(0);
                        launchingBalls = false;
                        waitingForGate = false;
                        releaseBalls = false;
                        return ret;
                    }//else
                    intake.kickBall();
                }else{
                    intake.stop();
                    releaseBalls = false;
                }
            }
            return ret;
        }
    }
}
