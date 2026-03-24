package org.firstinspires.ftc.teamcode.OpModes.autonomi.Ball15;


import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.GoToPressAndIntakeControlPoint;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeCloseShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeJustPressingGate;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeLEAVEShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closePreloadShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closePressPrep;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.getGoalStart;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingPrepPos1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos2;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos3;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingGate;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoSuperClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TTimer;


@Autonomous(name = "\uD83D\uDE2E possibly illegal 15 ball that intakes far \uD83D\uDE2E")
public class CloseMiddleFarGate extends AutoSuperClass
{
    //    public SideSwitchingDefinitelyLegal15Ball(){
//        super(new Pair[]{colorSelections,new Pair(new String[]{""},"in")});
//    }
    Follower follower;
    Bot bot;
    double launchY = 84.000;
    //    Pose launchPose = new Pose(90, launchY,Math.PI-Math.PI/5);
    //    Pose shootPose1 = closeShootPose.setHeading(-Math.PI/5);
//    Pose shootPose2 = closeShootPose.setHeading(-Math.PI/5);
//    Pose shootPose3 = closeShootPose.setHeading(-Math.PI/5);
//    Pose shootPose4 = closeShootPose.setHeading(-Math.PI/5);


    Path shootPreload, prepCloseIntake,collectClose,intakeCloseAndOpenGate, closePrepGate, closeOpenGate, shootClose, collectMiddle, shootMiddle, collectFar, shootFar,pressGateAndIntake, shootGateBalls,shootGateBallsLEAVE;

    PathChain goGetClose,collectCloseAndPressGate, collectMiddleAndPressGate, goGetHP;
    //    SectionedTelemetry telemetry;
    BetterControllerClass Gpad;
    public Pose launchPose = closePreloadShootPose;
    public void setLaunchPose(Pose pose){
        launchPose = pose;
    }


    public void initializePaths()
    {

        shootPreload = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        getGoalStart(blueSide()),
                        closePreloadShootPose
                ))
        );
//        shootPreload.setBrakingStrength(0.5);


        prepCloseIntake = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        closePreloadShootPose,
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

        shootClose = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingTargetPos1,
                        closeCloseShootPose
                ))
        );
//        shootClose.setLinearHeadingInterpolation(intakingTargetPos1.getHeading(),intakingTargetPos1.getHeading()-Math.PI/5);

        collectMiddle = getPathFromBezierCurve(
                correctBezierCurve(new BezierCurve(
                        closeCloseShootPose,
                        new Pose(92.154, 57.196),
                        intakingTargetPos2
                ))
        );

        shootMiddle = getPathFromBezierCurve(
                correctBezierCurve(new BezierCurve(
                        intakingTargetPos2,
                        new Pose(92.154, 57.196),
                        closeCloseShootPose
                ))
        );
        ;
        pressGateAndIntake =getPathFromBezierCurve(
                correctBezierCurve(
                            new BezierCurve(
                                    closeCloseShootPose,
                                    GoToPressAndIntakeControlPoint,
                                    intakingGate
                        )
                )
        );

        shootGateBalls = getPathFromBezierCurve(
                correctBezierLine(
                        new BezierLine(
                                intakingGate,
                                closeShootPose
                        )
                ));
        shootGateBallsLEAVE = getPathFromBezierCurve(
                correctBezierLine(
                        new BezierLine(
                                intakingGate,
                                closeLEAVEShootPose
                        )
                ));
        collectFar = getPathFromBezierCurve(
                correctBezierCurve(new BezierCurve(
                        closeShootPose,

                        new Pose(75.3,33.97),


                        intakingTargetPos3
                ))
        );
//        collectFar.setLinearHeadingInterpolation(intakingTargetPos3.getHeading()-Math.PI/5,0);

        shootFar = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingTargetPos3,

                        closeLEAVEShootPose
                ))
        );

//        LEAVE = getPathFromBezierCurv

    }

    /**
     * should tell us if we need to pump the intake to keep everything from falling out
     * @return
     */
    public boolean ballsGoingToFallOut(){
        return follower.getAcceleration().dot(follower.getPose().getHeadingAsUnitVector())<0.5 ;//&& follower.getAcceleration().dot(follower.getVelocity())<0;
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
                    setLaunchPose(shootPreload.endPose());
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
//                    if(){
//
//                    }
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        setLaunchPose(shootClose.endPose());
                        follower.followPath(goGetClose, true);
                        bot.intake.setPower(1);
                        nextStep();
                    }
                },
                () ->
                {
//                    if(bot.follower.getChainIndex()==1){
//                        bot.intake.setPower(1);
//                    }else{
//                        bot.intake.setPower(0);
//                    }
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        follower.followPath(shootClose, true);
                        nextStep();
                    }
                },
                () ->
                {
                    if(stepTimer.getElapsedTime()>500){
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
//                ()->{
//                    if(!bot.follower.isBusy()){
//                        follower.setMaxPower(0.8);
//                        nextStep();
//                    }
//                },
                ()->{
                    if ((bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)&& incrementingStep())
                    {
                        setLaunchPose(shootMiddle.endPose());
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
                    if (bot.launchHandler.launchPhase==Bot.LaunchPhase.NULL){
                        setLaunchPose(shootGateBalls.endPose());
                        bot.follower.followPath(pressGateAndIntake);
                        bot.intake.setPower(1);
                        nextStep();
                    }
                },
                () ->
                {
                    if (!bot.follower.isBusy()){
                        nextStep();
                    }
                },
                () ->
                {
                    if (stepTimer.getElapsedTime()>1500){
                        follower.followPath(shootGateBalls);
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
                        setLaunchPose(shootFar.endPose());
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
                }
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
        bot.launcher.resetPID();
//        bot.spinFlywheelToTunedSpeed(getLaunchVector());


    }
    public void autonomousPathUpdate()
    {
        bot.update();
//        bot.updateConstants();

//        if(currentStep == 1){
//        }
        if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL/*&&currentStep == 1*/)
        {
//            bot.spinFlywheelToTunedSpeed(getLaunchPosition());
            bot.idleFlywheel();
//            bot.launcher.setPower(-bot.launcher.PIDF.getFeedForward(300));
            //if almost spun up and still accelerating(basically a temporary bandaid solution to make the pid stabilize faster.)
//            if(ExtraMath.closeTo0(bot.launcher.getFlywheelEncoder().getVelocity()-240,10)&&!bot.launcher.launcherPIDF.lowAcceleration()){
//                bot.launcher.launcherPIDF.resetPid();
//            }
        }
//        bot.updatePIDMeasures(getLaunchPosition());
        updateSteps();
    }


    @Override public void loop()
    {
        follower.update();
        Gpad.update();

//        if(/*stopTimer.timeover()||*/ Gpad.getToggleValue("a")){//TODO: also remove this because it is illegal for a real match
////            follower.breakFollowing();
//            telemetry.addLine("paused");
//            follower.setMaxPower(0);
//            bot.launcher.setPower(0);
//            bot.updateCurrentPos();
//        }else{
        autonomousPathUpdate();
        if(bot.launcher.PIDF.isStable()&&!prevStabilized){
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
        prevStabilized = bot.launcher.PIDF.isStable();
        telemetry.addData("step", currentStep);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("side",selections.get("color"));
        telemetry.addData("path start pose",shootPreload.getPose(0));
        telemetry.addData("pose",follower.getPose());
//        }
//        if(Gpad.getRisingEdge("a")){
//            follower.setMaxPower(1);
//        }
        bot.aimTurret(launchPose);


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



