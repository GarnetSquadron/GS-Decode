package org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
public class PatternThreeRedSideSixBall {
    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(88.000, 8.000),
                            new Pose(83.155, 13.690),
                            new Pose(80.000, 20.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-32.5))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(80.000, 20.000),
                            new Pose(94.563, 91.521),
                            new Pose(126.761, 83.915)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-32.5), Math.toRadians(-90))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(126.761, 83.915),
                            new Pose(77.577, 81.634),
                            new Pose(110.028, 27.380),
                            new Pose(80.000, 20.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-32.5))
            .build();
}


