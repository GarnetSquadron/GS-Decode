package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

public class Bot
{
    public Launcher launcher;
    public Intake intake;
    public LaunchHandler launchHandler;
    public Bot(HardwareMap hardwareMap){
        launcher = new Launcher(hardwareMap);
        intake = new Intake(hardwareMap);
        launchHandler = new LaunchHandler();
    }
    public void init(){

    }
    public double update(){
        return launchHandler.update();
    }
    public class LaunchHandler
    {
        double releaseStartTime;
        boolean launchingBalls = false;
        boolean releaseBalls = false;
        public LaunchHandler(){
            releaseStartTime = -1;
        }
        public void initLaunch(){
            launchingBalls = true;
            releaseBalls = false;
            intake.closeGate();
        }
        public double update(){
            if(launchingBalls){
                if(launcher.spinUpFlywheel(0.5)>-1&&!releaseBalls){
                    releaseBalls = true;
                    releaseStartTime = TIME.getTime();
                    intake.setPower(1);
                    //intake.closeGate();
                }
                else{
                    intake.setPower(1);
                    intake.openGate();
                    if(TIME.getTime()-releaseStartTime>1){
                        if(TIME.getTime()-releaseStartTime>2){
                            intake.unKick();
                            intake.stop();
                            launcher.setPower(0);
                            launchingBalls = false;
                            releaseBalls = false;
                        }else intake.kickBall();
                    }
                }
            }
            return releaseStartTime-TIME.getTime();
        }
    }
}
