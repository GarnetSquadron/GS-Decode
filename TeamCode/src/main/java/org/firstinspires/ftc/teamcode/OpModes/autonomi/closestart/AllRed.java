package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.pedroPathing.TestConstants;

@TeleOp(name = "AutoRedAll")
public class AllRed extends LinearOpMode
{
    Follower follower;
    @Override
    public void runOpMode() throws InterruptedException
    {
        follower = TestConstants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();
        PathBuilder builder = follower.pathBuilder();

        PathChain line1 = builder
                .addPath(
                        // Path 1, Go to shoot at position, change ALL Math.toRadians(65) values to adjust the FAR shooting horizontal angle
                        new BezierLine(new Pose(88.000, 8.000), new Pose(80.000, 20.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(65))
                .build();

        PathChain line2 = builder
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

        PathChain line3 = builder
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

        PathChain line4 = builder
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

        PathChain line5 = builder
                .addPath(
                        // Path 5, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierLine(new Pose(126.000, 57.296), new Pose(78.000, 78.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();

        PathChain line6 = builder
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

        PathChain line7 = builder
                .addPath(
                        // Path 7, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierLine(new Pose(128.282, 83.155), new Pose(78.000, 78.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();

        follower.update();
        waitForStart();
        follower.followPath(line1);//so basically the reason it wasnt working before I think is simply because we were using line4, and it was crashing because line4 only works if its in the right position
        while(follower.isBusy()){
            follower.update();
            telemetry.addData("path", follower.getChainIndex());
            telemetry.update();
        }
        //follower.followPath(line2);
    }
}
