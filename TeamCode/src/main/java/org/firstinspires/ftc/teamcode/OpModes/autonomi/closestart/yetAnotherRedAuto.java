package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;




import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;




@Autonomous(name = "\uD83E\uDD69 FINAL BOSS OF ALL AUTOS RED \uD83E\uDD69")
public class yetAnotherRedAuto extends OpMode
{
    Follower follower;
    Timer pathTimer;
    private int pathState;
    Bot bot;


    PathChain ShootPreload,CollectClose,Shoot1,CollectMiddle,PressGate,Shoot2,CollectFar,Shoot3,Path1,Path2,Path3,Path4,Path5,Path6;


    PathBuilder builder;


    public void initializePaths(){


        builder = follower.pathBuilder();


        ShootPreload = builder
                .addPath(
                        new BezierLine(
                                new Pose(123.000, 123.000),
                                new Pose(86.000, 86.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(216), Math.toRadians(220))
                .build();


        CollectClose = builder
                .addPath(
                        new BezierCurve(
                                new Pose(86.000, 86.000),
                                new Pose(90.622, 81.069),
                                new Pose(120.000, 84.039)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(340), Math.toRadians(360))
                .build();


        Shoot1 = builder
                .addPath(
                        new BezierLine(
                                new Pose(129.646, 84.039),
                                new Pose(86.000, 86.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(360), Math.toRadians(220))
                .build();


        CollectMiddle = builder
                .addPath(
                        new BezierCurve(
                                new Pose(86.000, 86.000),
                                new Pose(93.635, 54.596),
                                new Pose(120.000, 59.328)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(290), Math.toRadians(360))
                .build();


        PressGate = builder
                .addPath(
                        new BezierCurve(
                                new Pose(128.608, 59.328),
                                new Pose(116.682, 68.759),
                                new Pose(125.740, 68.434)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(360), Math.toRadians(325))
                .build();


        Shoot2 = builder
                .addPath(
                        new BezierLine(
                                new Pose(125.740, 68.434),
                                new Pose(86.000, 86.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(325), Math.toRadians(220))
                .build();


        CollectFar = builder
                .addPath(
                        new BezierCurve(
                                new Pose(86.000, 86.000),
                                new Pose(86.622, 20.783),
                                new Pose(117.728, 38.452),
                                new Pose(130.621, 35.186)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(300), Math.toRadians(360))
                .build();


        Shoot3 = builder
                .addPath(
                        new BezierLine(
                                new Pose(130.621, 35.186),
                                new Pose(86.000, 86.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(360), Math.toRadians(220))
                .build();


        Path1 = builder
                .addPath(


                        new BezierLine(
                                new Pose(126.000, 118.700),


                                new Pose(83.000, 83.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(216), Math.toRadians(300))


                .build();
        Path2 = builder
                .addPath(
                        new BezierLine(
                                new Pose(83.000, 83.000),


                                new Pose(130.000, 83.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))


                .build();


        Path3 = builder
                .addPath(
                        new BezierLine(
                                new Pose(130.000, 83.000),


                                new Pose(83.000, 83.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(300))


                .build();


        PathChain Path4 = builder
                .addPath(
                        new BezierCurve(
                                new Pose(83.000, 83.000),
                                new Pose(89.603, 65.774),
                                new Pose(105.000, 59.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(300), Math.toRadians(0))


                .build();


        Path5 = builder
                .addPath(
                        new BezierCurve(
                                new Pose(105.000, 59.000),
                                new Pose(120.478, 57.533),
                                new Pose(132.000, 63.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(30))


                .build();


        Path6 = builder
                .addPath(
                        new BezierLine(
                                new Pose(132.000, 63.000),


                                new Pose(83.000, 83.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(300))


                .build();




    }


    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalPositionRed);
        pathTimer = new Timer();
        follower = bot.follower;
        initializePaths();
        follower.setStartingPose((new Pose(123, 123, Math.toRadians(216))));
    }


    public void start(){
        follower.followPath(ShootPreload);
    }
    public void autonomousPathUpdate() {
        bot.update();
        setPathState(0);
        switch (pathState) {


            case 0:
                follower.followPath(Path1);
                setPathState(1);
                break;


            case 1:
                if(!follower.isBusy()){
                    follower.followPath(Path2);
                    setPathState(2);
                }
                break;


            case 2:
                if(!follower.isBusy()){
                    follower.followPath(Path3);
                    setPathState(3);
                }
                break;
            case 3:
                follower.followPath(Path4);
                setPathState(4);
                break;


            case 4:
                if(!follower.isBusy()){
                    follower.followPath(Path5);
                    setPathState(5);
                }
                break;


            case 5:
                if(!follower.isBusy()){
                    follower.followPath(Path6);
                    setPathState(6);
                }
                break;
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


    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void incrementPathState(){
        setPathState(pathState+1);
    }
}


