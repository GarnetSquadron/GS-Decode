package org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing;
//test comment


import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

public class PatternTwoBlueSideSixBall {

    public static PathBuilder builder = follower.pathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(56.000, 8.000),
                            new Pose(74.535, 10.141),
                            new Pose(67.000, 22.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(32.5))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(67.000, 22.000),
                            new Pose(68.704, 67.690),
                            new Pose(17.239, 57.803)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(32.5), Math.toRadians(100))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(17.239, 57.803),
                            new Pose(33.972, 23.070),
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
