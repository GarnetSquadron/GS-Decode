package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoSuperClass;
import org.firstinspires.ftc.teamcode.SectionedTelemetry;


@Autonomous(name = "\uD83E\uDD69 FINAL BOSS OF ALL AUTOS RED \uD83E\uDD69")
public class yetAnotherRedAuto extends AutoSuperClass
{
    Follower follower;
    Timer pathTimer;
    Bot bot;
    Pose launchPose = new Pose(90, 83.000,Math.PI);
    double intakingTargetX = 115;
    Pose intakingTargetPos1 = new Pose(intakingTargetX, 83.000);
    Pose intakingTargetPos2 = new Pose(intakingTargetX, 59.000);
    Pose intakingTargetPos3 = new Pose(intakingTargetX, 34.8);


    Path shootPreload, collectClose, shoot1, collectMiddle, pressGate, shoot2, collectFar, shoot3, launchPreload, intake1, launch2, intake2, launch3, intake3, launch4;


    SectionedTelemetry telemetry;


    public void initializePaths()
    {

        shootPreload = new Path(
                new BezierLine(
                        new Pose(123.000, 123.000),
                        new Pose(86.000, 86.000)
                )
        );/*.setLinearHeadingInterpolation(Math.toRadians(216), Math.toRadians(220));*/


        collectClose = new Path(
                new BezierCurve(
                        new Pose(86.000, 86.000),
                        new Pose(90.622, 81.069),
                        new Pose(120.000, 84.039)
                )
        );


        shoot1 = new Path(
                new BezierLine(
                        new Pose(129.646, 84.039),
                        new Pose(86.000, 86.000)
                )
        );


        collectMiddle = new Path(
                new BezierCurve(
                        new Pose(86.000, 86.000),
                        new Pose(93.635, 54.596),
                        new Pose(120.000, 59.328)
                )
        );


        pressGate = new Path(
                new BezierCurve(
                        new Pose(128.608, 59.328),
                        new Pose(116.682, 68.759),
                        new Pose(125.740, 68.434)
                )
        );


        shoot2 = new Path(
                new BezierLine(
                        new Pose(125.740, 68.434),
                        new Pose(86.000, 86.000)
                )
        );


        collectFar = new Path(
                new BezierCurve(
                        new Pose(86.000, 86.000),
                        new Pose(86.622, 20.783),
                        new Pose(117.728, 38.452),
                        new Pose(130.621, 35.186)
                )
        );


        shoot3 = new Path(
                new BezierLine(
                        new Pose(130.621, 35.186),
                        new Pose(86.000, 86.000)
                )
        );


        launchPreload = new Path(


                new BezierLine(
                        FieldDimensions.botTouchingRedGoal,


                        launchPose
                )
        );
        intake1 = new Path(
                new BezierLine(
                        launchPose,


                        intakingTargetPos1
                )
        );


        launch2 = new Path(
                new BezierLine(
                        intakingTargetPos1,


                        launchPose
                )
        );


        intake2 = new Path(new BezierCurve(
                launchPose,
                new Pose(86.6, 57.9),
                intakingTargetPos2
        ));
        //path4.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
//                follower.pathBuilder().addPath(
//                new BezierCurve(
//                        launchPose,
//                        new Pose(86.6, 57.9),
//                        intakingTargetPos2
//                )
//        ).addPath(
//                new BezierLine(
//                        new Pose(98.000, 59.000),
//
//                        intakingTargetPos2
//                )
//        ).build();
        //Path5.setVelocityConstraint(0.6);


        launch3 = new Path(
                new BezierLine(
                        intakingTargetPos2,

                        launchPose
                )
        );
        launch3.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));
        //path6.setHeadingConstraint(0.1);


        intake3 = new Path(
                  new BezierCurve(
                    launchPose,

                    new Pose(75.3,33.97),


                    intakingTargetPos3
                  )
                );

        launch4 = new Path(
                  new BezierLine(
                    intakingTargetPos3,

                    launchPose
                  )
                );
        launch4.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));
        //path7.setVelocityConstraint(0.6);


    }
    public boolean incrementingStep(){
        return !gamepad1.a;
    }


    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalPositionRed);
        bot.launcher.launcherPIDF.setConstants(
                /*bot.launcher.launcherPIDF.Kp*/0.002,
                bot.launcher.launcherPIDF.Kd,
                bot.launcher.launcherPIDF.Ki,
                bot.launcher.launcherPIDF.Ks,
                bot.launcher.launcherPIDF.Kv,
                bot.launcher.launcherPIDF.Ka
        );
        pathTimer = new Timer();
        follower = bot.follower;
        initializePaths();
        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        this.telemetry = new SectionedTelemetry(super.telemetry);
        initSteps(
                () ->
                {//bot.spinFlyWheelWithinFeasibleRange();
                    follower.followPath(launchPreload, true);
                    nextStep();
                },
                () ->
                {
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.launchHandler.initLaunch();
                        nextStep();
                        //bot.intake.setPower(1);
                    }
                },
                () ->
                {
                    if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL&& incrementingStep())
                    {
                        bot.launcher.launcherPIDF.resetPid();
                        follower.followPath(intake1, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.intake.setPower(0);
                        follower.followPath(launch2, true);
                        nextStep();
                    }
                },
                () ->
                {
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        //follower.followPath(Path4,true);
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () ->
                {
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        follower.followPath(intake2, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.launcher.launcherPIDF.resetPid();
                        bot.intake.setPower(0);
                        follower.followPath(launch3, true);
                        nextStep();
                    }
                },
                () ->
                {
//                bot.spinFlyWheelWithinFeasibleRange();
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                ()->{
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        bot.launcher.launcherPIDF.resetPid();
                        follower.followPath(intake3, true);
                        nextStep();
                    }
                },
                ()->{
                    bot.intake.setPower(1);
                    if((!follower.isBusy())&& incrementingStep()){
//                        bot.intake.setPower(0);
//                        bot.launcher.launcherPIDF.resetPid();
                        follower.followPath(launch4, true);
                        nextStep();
                    }
                },
                ()->{
                    if((!follower.isBusy())&& incrementingStep()){
//                        bot.launcher.launcherPIDF.resetPid();
//                        follower.followPath(path8, true);
                        nextStep();
                    }
                }
        );
    }


    public void start()
    {
        //follower.followPath(ShootPreload);
        setCurrentStep(0);
        bot.updateConstants(bot.getDistance(getLaunchPosition()));
        bot.launcher.launcherPIDF.resetPid();

    }

    public Vector getLaunchPosition()
    {
        return launchPose.getAsVector();
    }

    public void autonomousPathUpdate()
    {
        bot.update();
        bot.aimTurret();
        if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)
        {
            bot.spinFlyWheelWithinFeasibleRange(getLaunchPosition());
        }
        bot.updatePID(getLaunchPosition());
        updateSteps();
    }


    @Override
    public void loop()
    {
        follower.update();
        follower.setMaxPower(1);
        autonomousPathUpdate();
        telemetry.addData("step", currentStep);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
        telemetry.clear();


    }
}


