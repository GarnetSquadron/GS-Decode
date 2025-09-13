package org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing;

import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

public class test {

    public static PathBuilder builder = follower.pathBuilder();

    public static PathChain line1 = builder
            .addPath(new BezierLine(new Pose(56.000, 8.000), new Pose(56.000, 36.000)))
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(56.000, 36.000),
                            new Pose(103.335, 106.680),
                            new Pose(124.108, 25.878),
                            new Pose(131.501, 83.267),
                            new Pose(69.888, 133.790),
                            new Pose(73.000, 28.000)
                    )
            )
            .setTangentHeadingInterpolation()
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierLine(new Pose(73.000, 28.000), new Pose(73.000, 109.000))
            )
            .setTangentHeadingInterpolation()
            .build();
}

