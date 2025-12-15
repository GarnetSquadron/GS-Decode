package org.firstinspires.ftc.teamcode.HardwareControls;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

public class Bot
{
    public Launcher launcher;
    public Intake intake;
    public Turret turret;
    public LaunchHandler launchHandler;
    public Bot(HardwareMap hardwareMap){
        launcher = new Launcher(hardwareMap);
        intake = new Intake(hardwareMap);
        launchHandler = new LaunchHandler();
        turret = launcher.turret;
    }
    public void init(){

    }
    public double update(){
        return launchHandler.update();
    }
    public class LaunchHandler
    {
        double power = 1;
        double releaseStartTime;
        boolean launchingBalls = false;
        boolean releaseBalls = false;
        public LaunchHandler(){
            releaseStartTime = -1;
        }
        public void initLaunch(double power){
            launchingBalls = true;
            releaseBalls = false;
            this.power = power;
            intake.closeGate();
        }
        public double update(){
            if(launchingBalls){
                if(launcher.spinUpFlywheel(power)>-1&&!releaseBalls){
                    releaseBalls = true;
                    releaseStartTime = TIME.getTime();
                    intake.setPower(1);
                    //intake.closeGate();
                }
                else{
                    intake.setPower(1);
                    intake.openGate();
                    double timeFor3rdBallToGetUnStuck = 2;
                    if(TIME.getTime()-releaseStartTime>timeFor3rdBallToGetUnStuck){
                        if(TIME.getTime()-releaseStartTime>timeFor3rdBallToGetUnStuck+1){
                            intake.unKick();
                            intake.stop();
                            intake.closeGate();
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
