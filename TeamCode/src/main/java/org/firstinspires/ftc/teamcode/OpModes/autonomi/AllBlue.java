package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.pedroPathing.Constants;

@TeleOp(name = "AutoBlueAll")
public class AllBlue extends LinearOpMode
{
    Follower follower;
    @Override
    public void runOpMode() throws InterruptedException
    {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(54,8,Math.toRadians(90)));
        follower.update();
        PathBuilder builder = follower.pathBuilder();

        PathChain line1 = builder
                .addPath(
                        // Path 1, Go to shoot at far position, change ALL Math.toRadians(110) values to adjust the FAR shooting horizontal angle
                        new BezierLine(new Pose(54.000, 8.000), new Pose(58.000, 20.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(110))
                .build();

        PathChain line2 = builder
                .addPath(
                        // Path 2, Go to collect closest pattern
                        new BezierCurve(
                                new Pose(58.000, 20.000),
                                new Pose(48.676, 34.479),
                                new Pose(15.718, 35.239)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(110), Math.toRadians(180))
                .build();

        PathChain line3 = builder
                .addPath(
                        // Path 3, Go to shoot at close position, change ALL Math.toRadians(125) values to adjust the CLOSE shooting horizontal angle
                        new BezierCurve(
                                new Pose(15.718, 35.239),
                                new Pose(59.070, 51.211),
                                new Pose(66.000, 78.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
                .build();

        PathChain line4 = builder
                .addPath(
                        // Path 4, Go to collect middle pattern
                        new BezierCurve(
                                new Pose(66.000, 78.000),
                                new Pose(52.986, 60.085),
                                new Pose(16.225, 56.028)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(125), Math.toRadians(180))
                .build();

        PathChain line5 = builder
                .addPath(
                        // Path 5, Go to shoot at close position, change ALL Math.toRadians(125) values to adjust the CLOSE shooting horizontal angle
                        new BezierCurve(
                                new Pose(16.225, 56.028),
                                new Pose(52.986, 65.915),
                                new Pose(66.000, 78.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
                        .build();

        PathChain line6 = builder
                .addPath(
                        // Path 6, Go to collect furthest pattern
                        new BezierCurve(
                                new Pose(66.000, 78.000),
                                new Pose(43.859, 83.915),
                                new Pose(16.732, 84.423)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(125), Math.toRadians(180))
                        .build();

        PathChain line7 = builder
                .addPath(
                        // Path 7, Go to shoot at close position, change ALL Math.toRadians(125) values to adjust the CLOSE shooting horizontal angle
                        new BezierLine(new Pose(16.732, 84.423), new Pose(66.000, 78.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
                .build();

        follower.update();
        waitForStart();
        follower.followPath(line1);

        while(follower.isBusy()){
            follower.update();
            telemetry.addData("path", follower.getChainIndex());
            telemetry.addData("position", follower.getPose());
            telemetry.update();
        }
        //follower.followPath(line2);
    }
}
