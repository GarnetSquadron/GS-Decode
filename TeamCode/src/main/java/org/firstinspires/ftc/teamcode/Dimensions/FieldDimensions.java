package org.firstinspires.ftc.teamcode.Dimensions;

import com.pedropathing.geometry.Pose;

public class FieldDimensions
{
    public static double minGoalHeight = 38.75;//0.9645 meters
    public static double maxGoalHeight = 53.7598425;// 1.3655 meters

    //update later with real positions
    public static double[] fieldCenter = {72,72};//(0,0) is the corner of the field
    public static double[] goalPositionRed = {132,135};
    public static double[] goalPositionBlue = {12,135};
    /**
     * The position when the back of the robot is contacting the side of the red goal. Good starting position for tests I think
     */
    public static Pose botTouchingRedGoal = new Pose(123,123,Math.toRadians(180+36));
    /**
     * The position when the back of the robot is contacting the side of the blue goal.
     */
    //TODO: this angle doesnt make sense to me, it should be -36 since the red one should be the
    // reflection of it across the y axis. However these angles worked and -36 didnt I think this is due to the tangent swapping sign on
    // different sides of the feild or something. I need to fix this
    public static Pose botTouchingBlueGoal = new Pose(21,123,Math.toRadians(180-36));
    /**
     * red side of the triangle
     */
    public static Pose botOnTinyTriangleRedSide = new Pose(64,9);
    /**
     * blue side of the triangle
     */
    public static Pose botOnTinyTriangleBlueSide = new Pose(81,9,Math.toRadians(-90));
}
