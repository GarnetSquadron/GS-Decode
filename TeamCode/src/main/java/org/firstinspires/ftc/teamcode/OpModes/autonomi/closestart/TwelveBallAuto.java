package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;


import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos2;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos3;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.pressingGate;

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
    Bot bot;
    double launchY = 84.000;
    Pose launchPose = new Pose(90, launchY,Math.PI);
    Pose shootPose1 = closeShootPose.setHeading(intakingTargetPos1.getHeading());
    Pose shootPose2 = closeShootPose.setHeading(intakingTargetPos2.getHeading());
    Pose gateShootPose = closeShootPose.setHeading(pressingGate.getHeading());


    Path shootPreload, collectClose, shootClose, collectMiddle, shootMiddle, pressGateAndIntake, shootGateBalls, leaveShootingZone;


    SectionedTelemetry telemetry;


    public void initializePaths()
    {

        shootPreload = new Path(
                new BezierLine(
                        FieldDimensions.botTouchingRedGoal,
                        closeShootPose
                )
        );
//        shootPreload.setBrakingStrength(0.5);


        collectClose = new Path(
                new BezierCurve(
                        closeShootPose,
                        new Pose(98.275, 83.868),
                        intakingTargetPos1
                )
        );
        collectClose.setLinearHeadingInterpolation(intakingTargetPos1.getHeading()-Math.PI/5, Math.toRadians(0));


        shootClose = new Path(
                new BezierLine(
                        intakingTargetPos1,
                        shootPose1
                )
        );
        shootClose.setLinearHeadingInterpolation(intakingTargetPos1.getHeading(),intakingTargetPos1.getHeading()-Math.PI/5);


        collectMiddle = new Path(
                new BezierCurve(
                        shootPose1,
                        new Pose(92.154, 57.196),
                        intakingTargetPos2
                )
        );
        collectMiddle.setLinearHeadingInterpolation(Math.toRadians(-40),intakingTargetPos1.getHeading());


        shootMiddle = new Path(
                new BezierCurve(
                        intakingTargetPos2,
                        new Pose(92.154, 57.196),
                        shootPose2
                )
        );
        shootMiddle.setLinearHeadingInterpolation(intakingTargetPos2.getHeading(),intakingTargetPos2.getHeading()-Math.PI/5);


        pressGateAndIntake = new Path(
                new BezierCurve(
                        shootPose2,
                        new Pose(70.688, 37.357),
                        pressingGate
                )
        );
        pressGateAndIntake.setLinearHeadingInterpolation(intakingTargetPos1.getHeading()-Math.PI/5,pressingGate.getHeading());

        shootGateBalls = new Path(
                new BezierCurve(
                        pressingGate,
                        new Pose(70.355, 37.444),
                        gateShootPose
                        )
        );
        shootGateBalls.setLinearHeadingInterpolation(pressingGate.getHeading(),intakingTargetPos1.getHeading()-Math.PI/5);


        leaveShootingZone = new Path(
                new BezierLine(
                        gateShootPose,
                        closeShootPose
                )
        );
        leaveShootingZone.setConstantHeadingInterpolation(gateShootPose.getHeading()-Math.PI/5);
    }
    public boolean incrementingStep(){
        return !gamepad1.b;
    }


    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalPositionRed);
//        bot.launcher.PIDF.setConstants(
//                /*bot.launcher.launcherPIDF.Kp*/0.002,
//                -0.0003,
//                bot.launcher.PIDF.Ki,
//                bot.launcher.PIDF.Ks,
//                bot.launcher.PIDF.Kv,
//                bot.launcher.PIDF.Ka
//        );
        follower = bot.follower;
        follower.setMaxPower(0.4);
        initializePaths();
        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        this.telemetry = new SectionedTelemetry(super.telemetry);
        initSteps(
//                ()->{
//
//                    if(gamepad1.b){//TODO: remove this because it is illegal for a real match
//                        telemetry.addLine("next step");
//                        nextStep();
//                    }
//                },
                () ->
                {
                    bot.launcher.setPower(bot.launcher.PIDF.getFeedForward(300));
                    follower.setMaxPower(1);
                    follower.followPath(shootPreload, true);
                    nextStep();
                },
                () ->
                {
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        follower.setMaxPower(1);
//                        bot.launcher.resetPID();
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
                    if((!follower.isBusy())){
                        nextStep();
                    }
                },
                ()->{
                    bot.intake.setPower(1);
                    if(pathTimer.getElapsedTime()>1000 && incrementingStep()){
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
                }

//                ()->{
//                    if((!follower.isBusy())&& incrementingStep()){
//                        follower.followPath(leaveShootingZone, true);
//
//                    }
//                }

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

        if(/*stopTimer.timeover()||*/ gamepad1.a){//TODO: also remove this because it is illegal for a real match
            follower.breakFollowing();
            bot.launcher.setPower(0);
            bot.updateCurrentPos();
        }else{
            autonomousPathUpdate();
        }

        if(bot.launcher.PIDF.hasStabilized()&&!prevStabilized){
            spunUpTime = TIME.getTime();
        }
        telemetry.addData("max power scale",follower.getMaxPowerScaling());
        try {
            telemetry.addData("vector ", follower.getDriveVector());
        }catch (Exception ignored){
            telemetry.addLine("unable to get vector");
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
//        telemetry.updateSection();
//        telemetry.updateSection("BOT");
//        telemetry.updateSection("LAUNCHER");
        telemetry.updateSection("TURRET");
        telemetry.display();
        telemetry.clearAll();


    }
}



