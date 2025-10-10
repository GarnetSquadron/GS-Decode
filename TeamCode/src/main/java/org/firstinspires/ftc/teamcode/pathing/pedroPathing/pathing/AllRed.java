package org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing;
import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

public class AllRed {

    public static PathBuilder builder = follower.pathBuilder();

    public static PathChain paths = builder
            .addPath(
                    // Path 1, Go to shoot at position, change ALL Math.toRadians(65) values to adjust the FAR shooting horizontal angle
                    new BezierLine(new Pose(88.000, 8.000), new Pose(80.000, 20.000))
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(65))
            .addPath(
                    // Path 2, Go to collect closest pattern
                    new BezierCurve(
                            new Pose(80.000, 20.000),
                            new Pose(83.155, 37.521),
                            new Pose(134.113, 32.451)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0))
            .addPath(
                    // Path 3, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                    new BezierCurve(
                            new Pose(134.113, 32.451),
                            new Pose(97.099, 41.070),
                            new Pose(78.000, 78.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
            .addPath(
                    // Path 4, Go to collect middle pattern
                    new BezierCurve(
                            new Pose(78.000, 78.000),
                            new Pose(92.535, 59.324),
                            new Pose(126.000, 57.296)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
            .addPath(
                    // Path 5, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                    new BezierLine(new Pose(126.000, 57.296), new Pose(78.000, 78.000))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
            .addPath(
                    // Path 6, Go to collect furthest pattern
                    new BezierCurve(
                            new Pose(78.000, 78.000),
                            new Pose(100.141, 84.169),
                            new Pose(128.282, 83.155)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
            .addPath(
                    // Path 7, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                    new BezierLine(new Pose(128.282, 83.155), new Pose(78.000, 78.000))
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
            .build();
}

