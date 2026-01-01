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

import org.firstinspires.ftc.teamcode.HardwareControls.Intake;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;


@Autonomous(name = "BetterAutoRedAll")
public class BetterAllRed extends OpMode
{
    Follower follower;
    Timer pathTimer;
    private int pathState;

    PathChain ShootPreload,CollectClose,Shoot1,CollectMiddle,Shoot2,CollectFar,Shoot3;

    PathBuilder builder;
    private final Pose startPose = new Pose(88.000, 8.000,Math.toRadians(90)); // Start Pose of our robot.
    Path scorePreload;
    private final Pose scorePose = new Pose(80.000, 20.000, Math.toRadians(65)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
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
        pathTimer = new Timer();
        follower = CompConstants.createFollower(hardwareMap);
        initializePaths();
        follower.setStartingPose(startPose);
    }

    public void start(){
        follower.followPath(scorePreload);
    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:

            /* You could check for
            - Follower State: "if(!follower.isBusy()) {}"
            - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
            - Robot Position: "if(follower.getPose().getX() > 36) {}"
            */

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Preload */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(CollectClose,true);
                    setPathState(2);
                }
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Shoot1,true);
                    setPathState(3);
                }
                break;
            case 3:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(CollectMiddle,true);
                    setPathState(4);
                }
                break;
            case 4:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup2Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Shoot2,true);
                    setPathState(5);
                }
                break;
            case 5:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(CollectFar,true);
                    setPathState(6);
                }
                break;
            case 6:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Shoot3, true);
                    setPathState(7);
                }
                break;
            case 7:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
        }
    }

    @Override
    public void loop()
    {
        //follower.setTeleOpDrive(1,0,0);
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
}
