package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeJustPressingGate;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closePressPrep;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.getGoalStart;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakeHP;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakeHPPrep;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingPrepPos1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos2;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos3;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.middleJustPressingGate;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.middlePressPrep;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TTimer;

@Autonomous(name = "12 BALLS DEFFINITELY LEGAL I PROMISE \uD83D\uDC4D \uD83D\uDC4D \uD83D\uDC4D")
public class DefinitelyLegal12Ball extends AutoSuperClass
{

//    public SideSwitchingDefinitelyLegal15Ball(){
//        super(new Pair[]{colorSelections,new Pair(new String[]{""},"in")});
//    }
    Follower follower;
    Bot bot;
    double launchY = 84.000;
//    Pose launchPose = new Pose(90, launchY,Math.PI-Math.PI/5);
    Pose shootPose0 = closeShootPose.setHeading(FieldDimensions.botTouchingRedGoal.getHeading());
    Pose shootPose1 = closeShootPose.setHeading(-Math.PI/5);
    Pose shootPose2 = closeShootPose.setHeading(-Math.PI/5);
    Pose shootPose3 = closeShootPose.setHeading(-Math.PI/5);
    Pose shootPose4 = closeShootPose.setHeading(-Math.PI/5);
    Pose gateShootPose = closeShootPose.setHeading(-Math.PI/5);


    Path shootPreload, prepCloseIntake,collectClose,intakeCloseAndOpenGate, closePrepGate, closeOpenGate, shootClose, collectMiddle, shootMiddle, pressGatePrep, pressGatePrep2,pressGate, collectFar, shootFar, LEAVE, prepHP, collectHP,shootHP;

    PathChain goGetClose,collectCloseAndPressGate, collectMiddleAndPressGate, goGetHP;
//    SectionedTelemetry telemetry;
    BetterControllerClass Gpad;


    public void initializePaths()
    {

        shootPreload = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        getGoalStart(blueSide()),
                        shootPose0
                ))
        );
//        shootPreload.setBrakingStrength(0.5);


        prepCloseIntake = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        shootPose0,
                        intakingPrepPos1
                ))
        );

        collectClose = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingPrepPos1,
                        intakingTargetPos1//.minus(new Pose(2,0))
                ))
        );
//        intakeCloseAndOpenGate = getPathFromBezierCurve(
//                correctBezierLine(new BezierLine(
//                        intakingPrepPos1,
//                        intakingTargetPos1//.minus(new Pose(2,0))
//                ))
//        );
//        collectClose.setBrakingStrength(1);
        goGetClose = new PathChain(
                prepCloseIntake,
                collectClose
        );
        closePrepGate = getPathFromBezierCurve(
                correctBezierLine(
                        new BezierLine(
                                intakingTargetPos1,
                                closePressPrep
                        )
                )
        );
        closeOpenGate = getPathFromBezierCurve(
                correctBezierLine(
                        new BezierLine(
                                closePressPrep,
                                closeJustPressingGate
                        )
                )
        );
        collectCloseAndPressGate = new PathChain(
                prepCloseIntake,
                collectClose,
                closePrepGate,
                closeOpenGate
        );


//        collectClose = new Path(
//                new BezierCurve(
//                        closeShootPose,
//                        new Pose(98.275, 83.868),
//                        intakingTargetPos1
//                )
//        );
//        if(blueSide()){
//            collectClose.setLinearHeadingInterpolation(-Math.PI / 5, Math.PI);
//        }else{
//            collectClose.setLinearHeadingInterpolation(6*Math.PI / 5, 0);
//        }
//        collectClose.setLinearHeadingInterpolation();


        shootClose = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingTargetPos1,
                        shootPose1
                ))
        );
//        shootClose.setLinearHeadingInterpolation(intakingTargetPos1.getHeading(),intakingTargetPos1.getHeading()-Math.PI/5);

        collectMiddle = getPathFromBezierCurve(
                correctBezierCurve(new BezierCurve(
                        shootPose1,
                        new Pose(92.154, 57.196),
                        intakingTargetPos2
                ))
        );
//        collectMiddle.setBrakingStrength(0.1);
////        collectMiddle = new Path(
////                new BezierCurve(
////                        shootPose1,
////                        new Pose(92.154, 57.196),
////                        intakingTargetPos2
////                )
////        );
//        collectMiddle.setLinearHeadingInterpolation(intakingTargetPos1.getHeading()-Math.PI/5, 0);
//        collectMiddle.setBrakingStrength(0.1);
        pressGatePrep = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingTargetPos2,
                        middlePressPrep
                ))
        );
//        pressGatePrep.setLinearHeadingInterpolation(0,0);
        pressGate = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        middlePressPrep,
//                        new Pose(120, 67.124694376528105),
                        middleJustPressingGate.minus(new Pose(3,1))
                ))
        );
//        pressGate.setLinearHeadingInterpolation(0,0);
        pressGate.setBrakingStrength(1);

        collectMiddleAndPressGate = new PathChain(
                collectMiddle,
                pressGatePrep,
                pressGate
        );

        shootMiddle = getPathFromBezierCurve(
                correctBezierCurve(new BezierCurve(
                        intakingTargetPos2,
                        new Pose(92.154, 57.196),
                        shootPose2
                ))
        );
//        shootMiddle.setLinearHeadingInterpolation(intakingTargetPos2.getHeading(),intakingTargetPos2.getHeading()-Math.PI/5);
        collectFar = getPathFromBezierCurve(
                correctBezierCurve(new BezierCurve(
                        shootPose2,

                        new Pose(75.3,33.97),


                        intakingTargetPos3
                ))
        );
//        collectFar.setLinearHeadingInterpolation(intakingTargetPos3.getHeading()-Math.PI/5,0);

        shootFar = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingTargetPos3,

                        shootPose2
                ))
        );
        LEAVE = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        shootPose2,

                        new Pose(83,107,shootPose2.getHeading())
                ))
        );
        prepHP = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        shootPose2,

//                        new Pose(75.3,33.97),


                        intakeHPPrep
                ))
        );
        collectHP = getPathFromBezierCurve(
                correctBezierCurve(new BezierCurve(
                        intakeHPPrep,

                        new Pose(75.3,33.97),


                        intakeHP
                ))
        );

        goGetHP = new PathChain(
                prepHP,
                collectHP
        );

        shootHP = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakeHP,

                        shootPose4
                ))
        );
    }
    public void initSteps(){
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
                    bot.intake.setPower(0);
//                    bot.launcher.setPower(bot.launcher.PIDF.getFeedForward(300));
                    follower.setMaxPower(1);
//                    follower.setMaxPower(1);
                    follower.followPath(shootPreload, true);
                    nextStep();
                },
                () ->
                {
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        //follower.setMaxPower(1);
//                        bot.launcher.resetPID();
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () ->
                {
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        follower.followPath(collectCloseAndPressGate, true);
                        nextStep();
                    }
                },
                () ->
                {
                    if(bot.follower.getChainIndex()==1){
                        bot.intake.setPower(1);
                    }else{
                        bot.intake.setPower(0);
                    }
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
//                ()->{
//                    if(!bot.follower.isBusy()){
//                        follower.setMaxPower(0.8);
//                        nextStep();
//                    }
//                },
                ()->{
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        follower.followPath(collectMiddle, true);
                        nextStep();
                        bot.intake.setPower(1);
                    }
                },
                ()->{
                    if((!follower.isBusy())){
                        nextStep();
                    }
                },
                ()->{
                    if(stepTimer.getElapsedTime()>300){
                        bot.intake.setPower(0);
                    }else {
                        bot.intake.setPower(1);
                    }
                    if(/*pathTimer.getElapsedTime()>1000 &&*/ incrementingStep()){
                        bot.intake.setPower(0);
                        follower.followPath(shootMiddle, true);
                        nextStep();
                    }
                },
                ()->{
                    if(stepTimer.getElapsedTime()>300){
                        bot.intake.setPower(0);
                    }else {
                        bot.intake.setPower(1);
                    }
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
                    if ((!follower.isBusy())&& incrementingStep()/*&&pathTimer.getElapsedTime()<4000*/)
                    {
                        follower.followPath(shootFar, true);
                        nextStep();
                    }
                },
                () ->
                {
//                    if(pathTimer.getElapsedTime()>300){
//                        bot.intake.setPower(0);
//                    }
                    //keep spinning intake while it is reversing direction, because this is when the ball tends to come out.
                    if(stepTimer.getElapsedTime()>300){
                        bot.intake.setPower(0);
                    }else {
                        bot.intake.setPower(1);
                    }
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () ->
                {
                    if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL&& incrementingStep())
                    {
                        follower.followPath(LEAVE, true);
                        nextStep();
                    }
                }
//                () ->
//                {
//                    if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL&& incrementingStep())
//                    {
//                        follower.followPath(goGetHP, true);
//                        nextStep();
//                    }
//                },
//                () ->
//                {
//                    if(follower.getChainIndex()==1){bot.intake.setPower(1);}
//                    if ((!follower.isBusy())&& incrementingStep()/*&&pathTimer.getElapsedTime()<4000*/)
//                    {
//                        bot.intake.setPower(0);
//                        follower.followPath(shootHP, true);
//                        nextStep();
//                    }
//                },
//                () ->
//                {
////                    if(pathTimer.getElapsedTime()>300){
////                        bot.intake.setPower(0);
////                    }
//                    if ((!follower.isBusy())&& incrementingStep())
//                    {
//                        bot.launchHandler.initLaunch();
//                        nextStep();
//                    }
//                }

//                ()->{
//                    if((!follower.isBusy())&& incrementingStep()){
//                        follower.followPath(leaveShootingZone, true);
//
//                    }
//                }

        );
    }
    public boolean incrementingStep(){
        return !gamepad1.b;
    }


    @Override
    public void init()
    {
        Gpad = new BetterControllerClass(gamepad1);
    }


    public TTimer stopTimer;
    double startTime = TIME.getTime();
    double spunUpTime = 0;
    boolean prevStabilized;

    public void start()
    {
        super.start();
        bot = new Bot(hardwareMap, blueSide()?FieldDimensions.goalVectorBlue :FieldDimensions.goalVectorRed);
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
        follower.setStartingPose(AutoPoints.getGoalStart(blueSide()));
//        this.telemetry = new SectionedTelemetry(super.telemetry);
        initSteps();
        Bot.redSide = !blueSide();//TODO: um this is kinda not ideal I think. probably need to get an enum system going or something
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
        return correctPose(closeShootPose).getAsVector();
    }
    public void autonomousPathUpdate()
    {
        bot.update();
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
        Gpad.update();

        if(/*stopTimer.timeover()||*/ Gpad.getToggleValue("a")){//TODO: also remove this because it is illegal for a real match
//            follower.breakFollowing();
            telemetry.addLine("paused");
            follower.setMaxPower(0);
            bot.launcher.setPower(0);
            bot.updateCurrentPos();
        }else{
            autonomousPathUpdate();
            if(bot.launcher.PIDF.hasStabilized()&&!prevStabilized){
                spunUpTime = TIME.getTime();
            }
//        telemetry.addData("max power scale",follower.getMaxPowerScaling());
//        try {
//            telemetry.addData("vector ", follower.getDriveVector());
//        }catch (Exception ignored){
//            telemetry.addLine("unable to get vector");
//        }
//        telemetry.addData("spinup time",spunUpTime-startTime);
//        telemetry.addArray("times",times);
            telemetry.addData("current position",bot.currentPos);
            prevStabilized = bot.launcher.PIDF.hasStabilized();
            telemetry.addData("step", currentStep);
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.addData("side",selections.get("color"));
            telemetry.addData("path start pose",shootPreload.getPose(0));
            telemetry.addData("pose",follower.getPose());
        }
        if(Gpad.getRisingEdge("a")){
            follower.setMaxPower(1);
        }
        bot.aimTurret();


//        telemetry.a
//        telemetry.updateSection();
//        telemetry.updateSection("BOT");
        telemetry.updateSection("LAUNCHER");
        telemetry.updateSection("PIDF");
//        telemetry.updateSection("TURRET");
        telemetry.display();
        telemetry.clearAll();


    }
}
