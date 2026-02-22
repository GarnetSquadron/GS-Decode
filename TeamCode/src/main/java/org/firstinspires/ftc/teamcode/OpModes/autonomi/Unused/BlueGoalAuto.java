package org.firstinspires.ftc.teamcode.OpModes.autonomi.Unused;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoSuperClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TTimer;

//@Autonomous(name = "\uD83E\uDD69 FINAL BOSS OF ALL AUTOS BLUE \uD83E\uDD69")
public class BlueGoalAuto extends AutoSuperClass
{

    private static final double FIELD_SIZE = 144.0;

    private static double mx(double x) { return FIELD_SIZE - x; }
    private static double mh(double headingRad) { return Math.PI - headingRad; }

    private static Pose m(Pose p) {
        return new Pose(mx(p.getX()), p.getY(), mh(p.getHeading()));
    }
    private static Pose m(double x, double y) {
        return new Pose(mx(x), y);
    }
    private static Pose m(double x, double y, double h) {
        return new Pose(mx(x), y, mh(h));
    }

    Follower follower;
    Timer pathTimer;
    Bot bot;


    Pose launchPose = new Pose(mx(90), 83.000, mh(Math.PI)); // (54, 83, 0)
    double intakingTargetX = mx(120); // 24
    Pose intakingTargetPos1 = new Pose(intakingTargetX, 83.000);
    Pose intakingTargetPos2 = new Pose(intakingTargetX, 59.000);
    Pose intakingTargetPos3 = new Pose(intakingTargetX, 34.8);


    Path shootPreload, collectClose, shoot1, collectMiddle, pressGate, shoot2, collectFar, shoot3,
            launchPreload, intake1, launch2, intake2, launch3, intake3, launch4;

//    SectionedTelemetry telemetry;

    public void initializePaths()
    {


        shootPreload = new Path(
                new BezierLine(
                        m(123.000, 123.000),
                        m(86.000, 86.000)
                )
        );

        collectClose = new Path(
                new BezierCurve(
                        m(86.000, 86.000),
                        m(90.622, 81.069),
                        m(120.000, 84.039)
                )
        );

        shoot1 = new Path(
                new BezierLine(
                        m(129.646, 84.039),
                        m(86.000, 86.000)
                )
        );

        collectMiddle = new Path(
                new BezierCurve(
                        m(86.000, 86.000),
                        m(93.635, 54.596),
                        m(120.000, 59.328)
                )
        );

        pressGate = new Path(
                new BezierCurve(
                        m(128.608, 59.328),
                        m(116.682, 68.759),
                        m(125.740, 68.434)
                )
        );

        shoot2 = new Path(
                new BezierLine(
                        m(125.740, 68.434),
                        m(86.000, 86.000)
                )
        );

        collectFar = new Path(
                new BezierCurve(
                        m(86.000, 86.000),
                        m(86.622, 20.783),
                        m(117.728, 38.452),
                        m(130.621, 35.186)
                )
        );

        shoot3 = new Path(
                new BezierLine(
                        m(130.621, 35.186),
                        m(86.000, 86.000)
                )
        );



        launchPreload = new Path(
                new BezierLine(

                        m(FieldDimensions.botTouchingRedGoal),
                        launchPose
                )
        );

        intake1 = new Path(
                new BezierLine(
                        launchPose,
                        intakingTargetPos1
                )
        );
        intake1.setBrakingStrength(2);

        launch2 = new Path(
                new BezierLine(
                        intakingTargetPos1,
                        launchPose
                )
        );

        intake2 = new Path(
                new BezierCurve(
                        launchPose,
                        m(86.6, 57.9),
                        intakingTargetPos2
                )
        );
        intake2.setBrakingStrength(2);

        launch3 = new Path(
                new BezierLine(
                        intakingTargetPos2,
                        launchPose
                )
        );

        launch3.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));

        intake3 = new Path(
                new BezierCurve(
                        launchPose,
                        m(75.3, 33.97),
                        intakingTargetPos3
                )
        );

        launch4 = new Path(
                new BezierLine(
                        intakingTargetPos3,
                        launchPose
                )
        );

        launch4.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));
    }

    public boolean incrementingStep(){
        return !gamepad1.a;
    }

    @Override
    public void init()
    {

        Bot.redSide = false;
        bot = new Bot(hardwareMap, FieldDimensions.goalVectorRed);


        bot.launcher.PIDF.setConstants(
                0.002,
                -0.0003,
                bot.launcher.PIDF.Ki,
                bot.launcher.PIDF.Ks,
                bot.launcher.PIDF.Kv,
                bot.launcher.PIDF.Ka
        );

        pathTimer = new Timer();
        follower = bot.follower;

        initializePaths();

        follower.setStartingPose(m(FieldDimensions.botTouchingRedGoal));

//        this.telemetry = new SectionedTelemetry(super.telemetry);


        initSteps(
                () ->
                {
                    follower.followPath(launchPreload, true);
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
                    if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL&& incrementingStep())
                    {
                        follower.followPath(intake1, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        follower.followPath(launch2, true);
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
                        follower.followPath(intake2, true);
                        nextStep();
                    }
                },
                () ->
                {
                    bot.intake.setPower(1);
                    if ((!follower.isBusy())&& incrementingStep())
                    {
                        bot.intake.setPower(0);
                        follower.followPath(launch3, true);
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
                        follower.followPath(intake3, true);
                        nextStep();
                    }
                },
                ()->{
                    bot.intake.setPower(1);
                    if((!follower.isBusy())&& incrementingStep()){
                        bot.intake.setPower(0);
                        follower.followPath(launch4, true);
                        nextStep();
                    }
                },
                ()->{
                    if((!follower.isBusy())&& incrementingStep()){
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

        if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL)
        {
            bot.spinFlywheelToTunedSpeed(getLaunchPosition());
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

        if(bot.launcher.PIDF.isStable()&&!prevStabilized){
            spunUpTime = TIME.getTime();
        }

        telemetry.addData("spinup time",spunUpTime-startTime);
        telemetry.addArray("times",times);
        telemetry.addData("current position",bot.currentPos);
        prevStabilized = bot.launcher.PIDF.isStable();
        telemetry.addData("step", currentStep);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.updateSection();
        telemetry.updateSection("BOT");
        telemetry.display();
        telemetry.clearAll();
    }
}
