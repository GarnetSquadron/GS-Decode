package org.firstinspires.ftc.teamcode.pathing.pedroPathing;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;

public class PatternThreeRed {

    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(56.000, 8.000),
                            new Pose(72.761, 9.887),
                            new Pose(67.000, 22.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(32.5))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(67.000, 22.000),
                            new Pose(48.930, 92.282),
                            new Pose(18.507, 83.915)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(32.5), Math.toRadians(100))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(18.507, 83.915),
                            new Pose(48.169, 32.704),
                            new Pose(67.000, 22.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(100), Math.toRadians(32.5))
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(67.000, 22.000),
                            new Pose(47.915, 34.732),
                            new Pose(48.000, 48.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(32.5), Math.toRadians(90))
            .build();
}

