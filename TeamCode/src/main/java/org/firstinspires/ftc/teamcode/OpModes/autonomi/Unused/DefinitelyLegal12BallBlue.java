package org.firstinspires.ftc.teamcode.OpModes.autonomi.Unused;

import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos2;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos3;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetX;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.middleJustPressingGate;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoSuperClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TTimer;

//@Autonomous(name = "12 BALLS DEFFINITELY LEGAL I PROMISE BLUE \uD83D\uDC4D \uD83D\uDC4D \uD83D\uDC4D")

public class DefinitelyLegal12BallBlue extends AutoSuperClass
{

    Follower follower;
    Bot bot;
    double launchY = 84.000;
    Pose launchPose = new Pose(90, launchY,-Math.PI/5);
    Pose shootPose1 = closeShootPose.setHeading(-Math.PI/5);
    Pose shootPose2 = closeShootPose.setHeading(-Math.PI/5);
    Pose gateShootPose = closeShootPose.setHeading(-Math.PI/5);
    Pose pressPrep = new Pose(intakingTargetX,66.5);


    Path shootPreload, collectClose, shootClose, collectMiddle, shootMiddle, pressGatePrep,pressGate, collectFar, shootFar;


    PathChain pressGateAndCollectMiddle;
//    SectionedTelemetry telemetry;


    public void initializePaths()
    {
        shootPreload = getPathFromBezierCurve(
                new BezierLine(
                        FieldDimensions.botTouchingBlueGoal,
                        closeShootPose.mirror()
                )
        );


        collectClose = getPathFromBezierCurve(
                new BezierCurve(
                        closeShootPose.mirror(),
                        new Pose(98.275, 83.868).mirror(),
                        intakingTargetPos1.mirror()
                )
        );
//        collectClose.setLinearHeadingInterpolation(intakingTargetPos1.mirror().getHeading()-Math.PI/5, Math.toRadians(0));


        shootClose = getPathFromBezierCurve(
                new BezierLine(
                        intakingTargetPos1.mirror(),
                        shootPose1.mirror()
                )
        );
//        shootClose.setLinearHeadingInterpolation(intakingTargetPos1.mirror().getHeading(),intakingTargetPos1.mirror().getHeading()-Math.PI/5);

        collectMiddle = getPathFromBezierCurve(
                new BezierCurve(
                        shootPose1.mirror(),
                        new Pose(92.154, 57.196).mirror(),
                        intakingTargetPos2.mirror()
                )
        );
//        collectMiddle.setLinearHeadingInterpolation(intakingTargetPos1.mirror().getHeading()-Math.PI/5, 0);
        collectMiddle.setBrakingStrength(0.1);
        pressGatePrep = getPathFromBezierCurve(
                new BezierLine(
                        intakingTargetPos2.mirror(),
                        pressPrep.mirror()
                )
        );
//        pressGatePrep.setLinearHeadingInterpolation(0,0);
        pressGate = getPathFromBezierCurve(
                new BezierLine(
                        pressPrep.mirror(),
                        middleJustPressingGate.mirror().minus(new Pose(3,1))
                )
        );
        pressGate.setLinearHeadingInterpolation(0,0);
        pressGate.setBrakingStrength(1);

        pressGateAndCollectMiddle = new PathChain(
                collectMiddle,
                pressGatePrep,
                pressGate
        );

        shootMiddle = getPathFromBezierCurve(
                new BezierCurve(
                        intakingTargetPos2.mirror(),
                        new Pose(92.154, 57.196).mirror(),
                        shootPose2.mirror()
                )
        );
//        shootMiddle.setLinearHeadingInterpolation(intakingTargetPos2.mirror().getHeading(),intakingTargetPos2.mirror().getHeading()-Math.PI/5);
        collectFar = getPathFromBezierCurve(
                new BezierCurve(
                        launchPose.mirror(),
                        new Pose(75.3,33.97).mirror(),
                        intakingTargetPos3.mirror()
                )
        );
//        collectFar.setLinearHeadingInterpolation(intakingTargetPos3.mirror().getHeading()-Math.PI/5,0);

        shootFar = getPathFromBezierCurve(
                new BezierLine(
                        intakingTargetPos3.mirror(),
                        launchPose.mirror()
                )
        );
//        shootFar.setLinearHeadingInterpolation(intakingTargetPos2.mirror().getHeading(),intakingTargetPos2.mirror().getHeading()-Math.PI/5);
    }
    public boolean incrementingStep(){
        return !gamepad1.b;
    }


    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalVectorBlue);
        follower = bot.follower;
        follower.setMaxPower(0.4);
        initializePaths();
        follower.setStartingPose(FieldDimensions.botTouchingBlueGoal);
        bot.redSide = false;
//        this.telemetry = new SectionedTelemetry(super.telemetry);
        initSteps(
                () ->
                {
                    bot.intake.setPower(0);
                    bot.launcher.setPower(bot.launcher.PIDF.getFeedForward(300));
                    follower.setMaxPower(1);
                    follower.followPath(shootPreload, true);
                    nextStep();
                },
                () ->
                {
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
                        follower.followPath(collectClose, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.intake.setPower(0);
                        follower.followPath(shootClose, true);
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
                        follower.followPath(pressGateAndCollectMiddle, true);
                        nextStep();
                        bot.intake.setPower(1);
                    }
                },
                ()->{
                    if(follower.getPose().getX()>124){
                        bot.intake.setPower(0);
                    }
                    if((!follower.isBusy())){
                        nextStep();
                    }
                },
                ()->{
                    if(incrementingStep()){
                        bot.intake.setPower(0);
                        follower.followPath(shootMiddle, true);
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
                        follower.followPath(collectFar, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.intake.setPower(0);
                        follower.followPath(shootFar, true);
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
        if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL/*&&currentStep == 1*/)
        {
            bot.spinFlywheelToTunedSpeed(getLaunchPosition());
        }
        bot.updateSpeedMeasure(getLaunchPosition());
        updateSteps();
    }


    @Override public void loop()
    {
        follower.update();

        if(gamepad1.a){//TODO: also remove this because it is illegal for a real match
            follower.breakFollowing();
            bot.launcher.setPower(0);
            bot.updateCurrentPos();
        }else{
            autonomousPathUpdate();
        }

        if(bot.launcher.PIDF.hasStabilized()&&!prevStabilized){
            spunUpTime = TIME.getTime();
        }
        telemetry.addData("current position",bot.currentPos);
        prevStabilized = bot.launcher.PIDF.hasStabilized();
        telemetry.addData("step", currentStep);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.updateSection();
        telemetry.updateSection("TURRET");
        telemetry.display();
        telemetry.clearAll();
    }
}
