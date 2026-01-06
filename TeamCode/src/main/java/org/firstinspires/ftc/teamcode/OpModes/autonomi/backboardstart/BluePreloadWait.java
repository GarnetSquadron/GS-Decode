package org.firstinspires.ftc.teamcode.OpModes.autonomi.backboardstart;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;

@Autonomous(name = "Blue Backboard Preload & Wait")
public class BluePreloadWait extends LinearOpMode
{
    Follower follower;
    @Override
    public void runOpMode() throws InterruptedException
    {
        follower = CompConstants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();
        PathBuilder builder = follower.pathBuilder();

        PathChain Path1 = builder
                .addPath(
                        new BezierLine(new Pose(20.500, 123.155), new Pose(40.288, 111.456))
                )
                .setLinearHeadingInterpolation(Math.toRadians(324), Math.toRadians(45))
                .build();

        PathChain Path2 = builder
                .addPath(
                        new BezierLine(new Pose(40.288, 111.456), new Pose(46.631, 114.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(144))
                .build();

        PathChain Path3 = builder
                .addPath(
                        new BezierLine(new Pose(46.631, 114.000), new Pose(46.631, 134.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(270))
                .build();

        PathChain Path4 = builder
                .addPath(
                        new BezierLine(new Pose(46.631, 134.000), new Pose(46.631, 75.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(40))
                .build();

        follower.update();
        waitForStart();
        follower.followPath(Path1);
        while(follower.isBusy()){
            follower.update();
            telemetry.addData("path", follower.getChainIndex());
            telemetry.update();
        }
    }
}

