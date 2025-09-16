package org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing;

import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

public class PatternOneRedSideSixBall {
    public static PathBuilder builder = follower.pathBuilder();

    //Shooting position, change the Math.toRadians(-32.5)) value to adjust angle,
    //make sure to change the Math.toRadians(-32.5)) value in line2 to the same number
    public static PathChain line1 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(88.000, 8.000),
                            new Pose(82.648, 11.408),
                            new Pose(80.000, 20.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-32.5))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(80.000, 20.000),
                            new Pose(83.155, 37.521),
                            new Pose(133.352, 35.493)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-32.5), Math.toRadians(-90))
            .build();
    //Shooting position, change the Math.toRadians(-32.5)) value to adjust angle,
    //make sure to change the Math.toRadians(-32.5)) value in line4 to the same number

    public static PathChain line3 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(133.352, 35.493),
                            new Pose(119.915, 8.366),
                            new Pose(80.000, 20.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-32.5))
            .build();

    //Parks in middle of field
    public static PathChain line4 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(67.000, 22.000),
                            new Pose(47.915, 34.732),
                            new Pose(48.000, 48.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-32.5), Math.toRadians(90))
            .build();
}


