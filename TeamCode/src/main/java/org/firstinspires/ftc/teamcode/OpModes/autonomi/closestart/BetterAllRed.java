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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.HardwareControls.Intake;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;
import org.firstinspires.ftc.teamcode.SectionedTelemetry;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;


@Autonomous(name = "BetterAutoRedAll")
public class BetterAllRed extends OpMode
{
    Follower follower;
    Timer pathTimer;
    SectionedTelemetry telemetry;
    private int pathState;
    Bot bot;

    PathChain ShootPreload,CollectClose,Shoot1,CollectMiddle,Shoot2,CollectFar,Shoot3;

    PathBuilder builder;
    private final Pose startPose = new Pose(88.000, 8.000,Math.toRadians(-90)); // Start Pose of our robot.
    Path scorePreload;
    private final Pose scorePose = new Pose(80.000, 20.000, Math.toRadians(-115)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public void initializePaths(){
        scorePreload = new Path(new BezierLine(startPose, scorePose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        builder = follower.pathBuilder();

        CollectClose = builder
                .addPath(
                        // Path 2, Go to collect closest pattern
                        new BezierCurve(
                                new Pose(80.000, 20.000),
                                new Pose(83.155, 37.521),
                                new Pose(134.113, 32.451)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0))
                .build();

        Shoot1 = builder
                .addPath(
                        // Path 3, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierCurve(
                                new Pose(134.113, 32.451),
                                new Pose(97.099, 41.070),
                                new Pose(78.000, 78.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();

        CollectMiddle = builder
                .addPath(
                        // Path 4, Go to collect middle pattern
                        new BezierCurve(
                                new Pose(78.000, 78.000),
                                new Pose(92.535, 59.324),
                                new Pose(126.000, 57.296)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                .build();

        Shoot2 = builder
                .addPath(
                        // Path 5, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierLine(new Pose(126.000, 57.296), new Pose(78.000, 78.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();

        CollectFar = builder
                .addPath(
                        // Path 6, Go to collect furthest pattern
                        new BezierCurve(
                                new Pose(78.000, 78.000),
                                new Pose(100.141, 84.169),
                                new Pose(128.282, 83.155)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                .build();

        Shoot3 = builder
                .addPath(
                        // Path 7, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierLine(new Pose(128.282, 83.155), new Pose(78.000, 78.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();
    }
    @Override
    public void init()
    {
        bot = new Bot(hardwareMap, FieldDimensions.goalPositionRed);
        pathTimer = new Timer();
        follower = bot.follower;
        initializePaths();
        follower.setStartingPose(startPose);
        telemetry = new SectionedTelemetry(super.telemetry);
    }
    @Override
    public void init_loop(){
        updateTelemetry();
        bot.initTelemetry();
    }

    public void start(){
//        follower.followPath(scorePreload);
    }
    public void autonomousPathUpdate() {
        bot.update();
        switch (pathState) {

            case 0:
                follower.followPath(scorePreload,true);
                incrementPathState();
//                if(!follower.isBusy()){
//                    incrementPathState();
//                    bot.launchHandler.initLaunch();
//                    //follower.pausePathFollowing();
//                }
                break;

            case 1:
                bot.aimTurret();
                bot.spinFlyWheelWithinFeasibleRange();
                if(!follower.isBusy()){
                    incrementPathState();
                    bot.launchHandler.initLaunch();
                    //follower.pausePathFollowing();
                }
                break;
            case 2:
                /* Score Preload */

                if(bot.launchHandler.launchPhase== Bot.LaunchPhase.SHUTDOWN){

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(CollectClose,true);
                    incrementPathState();
                }
                break;
            case 3:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Shoot1,true);
                    incrementPathState();
                }
                break;
            case 4:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(CollectMiddle,true);
                    incrementPathState();
                }
                break;
            case 5:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup2Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Shoot2,true);
                    incrementPathState();
                }
                break;
            case 6:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(CollectFar,true);
                    incrementPathState();
                }
                break;
            case 7:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Shoot3, true);
                    incrementPathState();
                }
                break;
            case 8:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
        }
    }
    double loopStartTime;

    @Override
    public void loop()
    {
        //follower.setTeleOpDrive(1,0,0);
        follower.update();
        autonomousPathUpdate();
        updateTelemetry();
//        telemetry.addData("path state", pathState);
//        telemetry.addData("is busy", follower.isBusy());
//        telemetry.addData("loop time", TIME.getTime() - loopStartTime);
//        loopStartTime = TIME.getTime();
//        telemetry.addData("x", follower.getPose().getX());
//        telemetry.addData("y", follower.getPose().getY());
//        telemetry.addData("heading", follower.getPose().getHeading());
//        telemetry.update();
//        telemetry.clear();

    }
    public void updateTelemetry(){
        telemetry.addData("path state", pathState);
        telemetry.addData("is busy", follower.isBusy());
        telemetry.addData("loop time", TIME.getTime() - loopStartTime);
        loopStartTime = TIME.getTime();
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
