package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.pedroPathing.Constants;

@TeleOp(name = "AutoOneBlueSideSix")
public class OneBlueSideSix extends OpMode
{
    Follower follower;
    PathChain line1,line2,line3,line4;
    @Override
    public void init()
    {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();
        PathBuilder builder = follower.pathBuilder();

        PathChain line1 = builder
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(64.901, 14.704),
                                new Pose(67.000, 22.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(32.5))
                .build();

        PathChain line2 = builder
                .addPath(
                        new BezierCurve(
                                new Pose(67.000, 22.000),
                                new Pose(58.563, 33.718),
                                new Pose(18.761, 35.746)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(32.5), Math.toRadians(100))
                .build();

        PathChain line3 = builder
                .addPath(new BezierLine(new Pose(18.761, 35.746), new Pose(67.000, 22.000)))
                .setLinearHeadingInterpolation(Math.toRadians(100), Math.toRadians(32.5))
                .build();

        PathChain line4 = builder
                .addPath(
                        new BezierCurve(
                                new Pose(67.000, 22.000),
                                new Pose(47.915, 34.732),
                                new Pose(48.000, 48.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(32.5), Math.toRadians(90))
                .build();
        follower.update();
        //follower.followPath(line2);
    }
    @Override
    public void loop(){
        follower.followPath(line1);//so basically the reason it wasnt working before I think is simply because we were using line4, and it was crashing because line4 only works if its in the right position
        while(follower.isBusy()){
            follower.update();
            telemetry.addData("path", follower.getChainIndex());
            telemetry.update();
        }
    }
}
