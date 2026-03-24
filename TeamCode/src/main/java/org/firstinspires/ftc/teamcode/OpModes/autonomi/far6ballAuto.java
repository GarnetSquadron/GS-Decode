package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.farShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.getFarStart;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingPrepPos3;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos3;

import com.pedropathing.follower.Follower;
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

@Autonomous(name = "far zone 6 ball")
public class far6ballAuto extends AutoSuperClass
{

    //    public SideSwitchingDefinitelyLegal15Ball(){
//        super(new Pair[]{colorSelections,new Pair(new String[]{""},"in")});
//    }
    Follower follower;
    Bot bot;
    double launchY = 84.000;
    //    Pose launchPose = new Pose(90, launchY,Math.PI-Math.PI/5);
    Pose shootPose0 = farShootPose.setHeading(-Math.PI/2);
    Pose shootPose1 = farShootPose.setHeading(-Math.PI/3);


    Path shootPreload, prepCloseIntake,collectClose, shoot2;

    PathChain goGetClose;
    //    SectionedTelemetry telemetry;
    BetterControllerClass Gpad;


    public void initializePaths()
    {

        shootPreload = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        getFarStart(blueSide()),
                        shootPose0
                ))
        );
//        shootPreload.setBrakingStrength(0.5);


        prepCloseIntake = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        shootPose0,
                        intakingPrepPos3
                ))
        );

        collectClose = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingPrepPos3,
                        intakingTargetPos3//.minus(new Pose(2,0))
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

        shoot2 = getPathFromBezierCurve(
                correctBezierLine(new BezierLine(
                        intakingTargetPos3,
                        shootPose1
                ))
        );
    }
    public void initSteps(){
        initSteps(
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
                        follower.followPath(goGetClose, true);
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
                        follower.followPath(shoot2, true);
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
//                        follower.followPath(collectMiddle, true);
//                        nextStep();
//                        bot.intake.setPower(1);
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
        follower.setStartingPose(AutoPoints.getFarStart(blueSide()));
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
