package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;




import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.SectionedTelemetry;


@Autonomous(name = "\uD83E\uDD69 FINAL BOSS OF ALL AUTOS RED \uD83E\uDD69")
public class yetAnotherRedAuto extends OpMode
{
    Follower follower;
    Timer pathTimer;
    private int pathState;
    Bot bot;


    Path ShootPreload,CollectClose,Shoot1,CollectMiddle,PressGate,Shoot2,CollectFar,Shoot3,Path1,Path2,Path3,Path4,Path5,Path6;


    PathBuilder builder;
    SectionedTelemetry telemetry;


    public void initializePaths(){


        builder = follower.pathBuilder();


        ShootPreload = new Path(
                        new BezierLine(
                                new Pose(123.000, 123.000),
                                new Pose(86.000, 86.000)
                        )
                );/*.setLinearHeadingInterpolation(Math.toRadians(216), Math.toRadians(220));*/


        CollectClose = new Path(
                        new BezierCurve(
                                new Pose(86.000, 86.000),
                                new Pose(90.622, 81.069),
                                new Pose(120.000, 84.039)
                        )
                );


        Shoot1 = new Path(
                        new BezierLine(
                                new Pose(129.646, 84.039),
                                new Pose(86.000, 86.000)
                        )
                );


        CollectMiddle = new Path(
                        new BezierCurve(
                                new Pose(86.000, 86.000),
                                new Pose(93.635, 54.596),
                                new Pose(120.000, 59.328)
                        )
                );


        PressGate = new Path(
                        new BezierCurve(
                                new Pose(128.608, 59.328),
                                new Pose(116.682, 68.759),
                                new Pose(125.740, 68.434)
                        )
                );


        Shoot2 = new Path(
                        new BezierLine(
                                new Pose(125.740, 68.434),
                                new Pose(86.000, 86.000)
                        )
                );


        CollectFar = new Path(
                        new BezierCurve(
                                new Pose(86.000, 86.000),
                                new Pose(86.622, 20.783),
                                new Pose(117.728, 38.452),
                                new Pose(130.621, 35.186)
                        )
                );


        Shoot3 = new Path(
                        new BezierLine(
                                new Pose(130.621, 35.186),
                                new Pose(86.000, 86.000)
                        )
                );


        Path1 = new Path(


                        new BezierLine(
                                new Pose(126.000, 118.700),


                                new Pose(83.000, 83.000)
                        )
                );
        Path2 = new Path(
                        new BezierLine(
                                new Pose(83.000, 83.000),


                                new Pose(130.000, 83.000)
                        )
                );


        Path3 = new Path(
                        new BezierLine(
                                new Pose(130.000, 83.000),


                                new Pose(83.000, 83.000)
                        )
                );


        Path4 = new Path(
                        new BezierCurve(
                                new Pose(83.000, 83.000),
                                new Pose(93.401, 71.235),
                                new Pose(98.000, 59.000)
                        )
                );


        Path5 = new Path(
                        new BezierLine(
                                new Pose(98.000, 59.000),

                                new Pose(133.000, 59.000)
                        )
                );
        //Path5.setVelocityConstraint(0.6);


        Path6 = new Path(
                        new BezierLine(
                                new Pose(133.000, 59.000),

                                new Pose(83.000, 83.000)
                        )
                );




    }


    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalPositionRed);
        pathTimer = new Timer();
        follower = bot.follower;
        initializePaths();
        follower.setStartingPose((new Pose(123, 123, Math.toRadians(216))));
        this.telemetry = new SectionedTelemetry(super.telemetry);
    }


    public void start(){
        //follower.followPath(ShootPreload);
        setPathState(0);
    }
    public void autonomousPathUpdate() {
        bot.update();
        bot.aimTurret();
        switch (pathState) {
            case 0:
                follower.followPath(Path1,true);
                incrementPathState();
                break;
            case 1:
                bot.spinFlyWheelWithinFeasibleRange();
                if(!follower.isBusy()){
                    bot.launchHandler.initLaunch();
                    incrementPathState();
                    //bot.intake.setPower(1);
                }
                break;
            case 2:
                if(bot.launchHandler.launchPhase== Bot.LaunchPhase.NULL){
                    follower.followPath(Path2,true);
                    incrementPathState();
                }
                break;
            case 3:
                bot.intake.setPower(1);
                if(!follower.isBusy()){
                    follower.followPath(Path3,true);
                    incrementPathState();
                }
                break;
            case 4:
                bot.spinFlyWheelWithinFeasibleRange();
                if(!follower.isBusy()){
                    //follower.followPath(Path4,true);
                    bot.launchHandler.initLaunch();
                    incrementPathState();
                }
                break;


            case 5:
                if(bot.launchHandler.launchPhase== Bot.LaunchPhase.NULL){
                    follower.followPath(Path4,true);
                    incrementPathState();
                }
                break;


            case 6:
                if(!follower.isBusy()){
                    follower.followPath(Path5,true);
                    incrementPathState();
                }
                break;
            case 7:
                bot.intake.setPower(1);
                if(!follower.isBusy()){
                    follower.followPath(Path6,true);
                    incrementPathState();
                }
                break;
            case 8:
                bot.spinFlyWheelWithinFeasibleRange();
                if(!follower.isBusy()){
                    bot.launchHandler.initLaunch();
                }
        }
    }


    @Override
    public void loop()
    {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
        telemetry.clear();


    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void incrementPathState(){
        setPathState(pathState+1);
    }
}


