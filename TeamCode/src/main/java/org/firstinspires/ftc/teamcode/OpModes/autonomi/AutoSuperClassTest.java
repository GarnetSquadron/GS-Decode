package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;

//@Autonomous
public class AutoSuperClassTest extends AutoSuperClass
{
    Bot bot;
    Path path1,path2,path3,path4;
    public void initPaths(){
        path1 = new Path(
                new BezierLine(
                        new Pose(),
                        new Pose(10,10)
                )
        );
    }
    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalVectorRed);
        bot.follower.setStartingPose(new Pose());
        initPaths();
        initSteps(
                ()->{
                    bot.follower.followPath(new Path());
                    nextStep();
                },
                ()->{
                    if(!bot.follower.isBusy()){
                        nextStep();
                    }
                }
//                ()->{
//                    bot.aimTurret();
//                }
        );
    }
}
