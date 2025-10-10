package org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing;
import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
public class AllBlue {

    public static PathBuilder builder = follower.pathBuilder();

    public static PathChain paths = builder
            .addPath(
                    // Path 1
                    new BezierLine(new Pose(54.000, 8.000), new Pose(58.000, 20.000))
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(125))
            .addPath(
                    // Path 2
                    new BezierCurve(
                            new Pose(58.000, 20.000),
                            new Pose(48.676, 34.479),
                            new Pose(15.718, 35.239)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(125), Math.toRadians(180))
            .addPath(
                    // Path 3
                    new BezierCurve(
                            new Pose(15.718, 35.239),
                            new Pose(59.070, 51.211),
                            new Pose(66.000, 78.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
            .addPath(
                    // Path 4
                    new BezierCurve(
                            new Pose(66.000, 78.000),
                            new Pose(52.986, 60.085),
                            new Pose(16.225, 56.028)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(125), Math.toRadians(180))
            .addPath(
                    // Path 5
                    new BezierCurve(
                            new Pose(16.225, 56.028),
                            new Pose(52.986, 65.915),
                            new Pose(66.000, 78.000)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
            .addPath(
                    // Path 6
                    new BezierCurve(
                            new Pose(66.000, 78.000),
                            new Pose(43.859, 83.915),
                            new Pose(16.732, 84.423)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(125), Math.toRadians(180))
            .addPath(
                    // Path 7
                    new BezierLine(new Pose(16.732, 84.423), new Pose(66.000, 78.000))
            )
            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125))
            .build();
}
