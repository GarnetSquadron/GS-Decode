package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareControls.Intake;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.TestConstants;


@Autonomous(name = "AutoRedAll")
public class AllRed extends LinearOpMode
{
    Follower follower;

    @Override
    public void runOpMode() throws InterruptedException {
        follower = TestConstants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();
        PathBuilder builder = follower.pathBuilder();
        Intake intake = new Intake(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);

        PathChain Path1 = builder
                .addPath(

                        new BezierLine(
                                new Pose(126.000, 118.700),

                                new Pose(83.000, 83.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(216), Math.toRadians(300))

                .build();

        PathChain Path2 = builder
                .addPath(
                        new BezierLine(
                                new Pose(83.000, 83.000),

                                new Pose(130.000, 83.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                .build();

        PathChain Path3 = builder
                .addPath(
                        new BezierLine(
                                new Pose(130.000, 83.000),

                                new Pose(83.000, 83.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(300))

                .build();

        PathChain Path4 = builder
                .addPath(
                        new BezierCurve(
                                new Pose(83.000, 83.000),
                                new Pose(89.603, 65.774),
                                new Pose(105.000, 59.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(300), Math.toRadians(0))

                .build();

        PathChain Path5 = builder
                .addPath(
                        new BezierCurve(
                                new Pose(105.000, 59.000),
                                new Pose(120.478, 57.533),
                                new Pose(132.000, 63.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(30))

                .build();

        PathChain Path6 = builder
                .addPath(
                        new BezierLine(
                                new Pose(132.000, 63.000),

                                new Pose(83.000, 83.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(300))

                .build();

        follower.update();
        waitForStart();
        follower.followPath(Path1);
        follower.followPath(Path2);
        follower.followPath(Path3);
        follower.followPath(Path4);
        follower.followPath(Path5);
        follower.followPath(Path6);
    }
}




