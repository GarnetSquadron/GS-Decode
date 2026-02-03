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
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoSuperClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TTimer;
import org.firstinspires.ftc.teamcode.SectionedTelemetry;


@Autonomous(name = "\uD83D\uDE2E WAIT IS THAT A 12 BALL AUTO?!?!?!?!? \uD83D\uDE2E")
public class TwelveBallAuto extends AutoSuperClass
{
    Follower follower;
    Timer pathTimer;
    Bot bot;
    double launchY = 84.000;
    Pose launchPose = new Pose(90, launchY,Math.PI);


    Path shootPreload, collectClose, shootClose, collectMiddle, shootMiddle, pressGateAndIntake, shootGateBalls, leaveShootingZone;


    SectionedTelemetry telemetry;


    public void initializePaths()
    {

        shootPreload = new Path(
                new BezierLine(
                        FieldDimensions.botTouchingRedGoal,
                        AutoPoints.closeShootPose
                )
        );


        collectClose = new Path(
                new BezierCurve(
                        AutoPoints.closeShootPose,
                        new Pose(98.275, 83.868),
                        new Pose(125.154, 83.376)
                )
        );
        collectClose.setLinearHeadingInterpolation(Math.toRadians(-40), Math.toRadians(0));


        shootClose = new Path(
                new BezierCurve(
                        new Pose(125.154, 83.376),
                        AutoPoints.closeShootPose
                )
        );


        collectMiddle = new Path(
                new BezierCurve(
                        AutoPoints.closeShootPose,
                        new Pose(92.154, 57.196),
                        new Pose(125.016, 59.730)
                )
        );
        collectMiddle.setLinearHeadingInterpolation(Math.toRadians(-40), Math.toRadians(0));


        shootMiddle = new Path(
                new BezierCurve(
                        new Pose(128.608, 59.328),
                        AutoPoints.closeShootPose
                )
        );


        pressGateAndIntake = new Path(
                new BezierCurve(
                        AutoPoints.closeShootPose,
                        new Pose(109.688, 37.357),
                        new Pose(134.158, 58.232)
                )
        );
        pressGateAndIntake.setLinearHeadingInterpolation(Math.toRadians(-40), Math.toRadians(45));

        shootGateBalls = new Path(
                new BezierCurve(
                        new Pose(134.158, 58.232),
                        new Pose(109.355, 37.444),
                        AutoPoints.closeShootPose
                        )
        );


        leaveShootingZone = new Path(
                new BezierLine(
                        AutoPoints.closeShootPose,
                        new Pose(114.145, 76.138)
                )
        );
        leaveShootingZone.setLinearHeadingInterpolation(Math.toRadians(-40), Math.toRadians(-90));


    }
    public boolean incrementingStep(){
        return !gamepad1.a;
    }


    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalPositionRed);
        bot.launcher.PIDF.setConstants(
                /*bot.launcher.launcherPIDF.Kp*/0.002,
                -0.0003,
                bot.launcher.PIDF.Ki,
                bot.launcher.PIDF.Ks,
                bot.launcher.PIDF.Kv,
                bot.launcher.PIDF.Ka
        );
        pathTimer = new Timer();
        follower = bot.follower;
        initializePaths();
        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        this.telemetry = new SectionedTelemetry(super.telemetry);
        initSteps(
                () ->
                {
                    bot.launcher.setPower(bot.launcher.PIDF.getFeedForward(300));
                    follower.followPath(shootPreload, true);
                    nextStep();
                },
                () ->
                {

                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.launcher.resetPID();
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () ->
                {
                    if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL&& incrementingStep())
                    {
                        follower.followPath(collectClose, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep()/*&&pathTimer.getElapsedTime()<4000*/)
                    {

                        follower.followPath(shootClose, true);
                        nextStep();
                    }
                },
                () ->
                {
                    if(pathTimer.getElapsedTime()>300){
                        bot.intake.setPower(0);
                    }
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () ->
                {
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        follower.followPath(collectMiddle, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.intake.setPower(0);
                        follower.followPath(shootMiddle, true);
                        nextStep();
                    }
                },
                () ->
                {
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                ()->{
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        follower.followPath(pressGateAndIntake, true);
                        nextStep();
                    }
                },
                ()->{
                    bot.intake.setPower(1);
                    if((!follower.isBusy())&& incrementingStep()){
                        bot.intake.setPower(0);
                        follower.followPath(shootGateBalls, true);
                        nextStep();
                    }
                },
                ()->{
                    if((!follower.isBusy())&& incrementingStep()){
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                ()->{
                    if((!follower.isBusy())&& incrementingStep()){
                        follower.followPath(leaveShootingZone, true);

                    }
                }

        );
    }


    public TTimer stopTimer;
    double startTime = TIME.getTime();
    double spunUpTime = 0;
    boolean prevStabilized;
    public void start()
    {
        startTime = TIME.getTime();
        stopTimer = new TTimer();
        stopTimer.StartTimer(30);
        //follower.followPath(ShootPreload);
        setCurrentStep(0);
        bot.updateConstants(bot.getDistance(getLaunchPosition()));
        bot.launcher.resetPID();
        bot.spinFlywheelToTunedSpeed(getLaunchPosition());


    }

    public Vector getLaunchPosition()
    {
        return launchPose.getAsVector();
    }
    public void autonomousPathUpdate()
    {
        bot.update();
        bot.aimTurret();
//        if(currentStep == 1){
//        }
        if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL/*&&currentStep == 1*/)
        {
            bot.spinFlywheelToTunedSpeed(getLaunchPosition());
//            bot.launcher.setPower(-bot.launcher.PIDF.getFeedForward(300));
            //if almost spun up and still accelerating(basically a temporary bandaid solution to make the pid stabilize faster.)
//            if(ExtraMath.closeTo0(bot.launcher.getFlywheelEncoder().getVelocity()-240,10)&&!bot.launcher.launcherPIDF.lowAcceleration()){
//                bot.launcher.launcherPIDF.resetPid();
//            }
        }
        bot.updateSpeedMeasure(getLaunchPosition());
        updateSteps();
    }


    @Override public void loop()
    {
        follower.update();
        follower.setMaxPower(1);
        if(stopTimer.timeover()|| gamepad1.a){
            follower.breakFollowing();
            bot.launcher.setPower(0);
            bot.updateCurrentPos();
        }else{
            autonomousPathUpdate();
        }

        if(bot.launcher.PIDF.hasStabilized()&&!prevStabilized){
            spunUpTime = TIME.getTime();
        }
        telemetry.addData("spinup time",spunUpTime-startTime);
//        telemetry.addArray("times",times);
        telemetry.addData("current position",bot.currentPos);
        prevStabilized = bot.launcher.PIDF.hasStabilized();
        telemetry.addData("step", currentStep);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
//        telemetry.a
        telemetry.updateSection();
        telemetry.updateSection("BOT");
//        telemetry.updateSection("LAUNCHER");
        telemetry.display();
        telemetry.clearAll();


    }
}



