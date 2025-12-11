package org.firstinspires.ftc.teamcode.Dimensions;

import com.pedropathing.geometry.Pose;

public class FieldDimensions
{
    public static double minGoalHeight = 38.75;//0.9645 meters
    public static double maxGoalHeight = 15;// 1.3655 meters

    //update later with real positions
    public static double[] fieldCenter = {72,72};//(0,0) is the corner of the field
    public static double[] goalPositionRed = {135,135};
    public static double[] goalPositionBlue = {15,135};
    public static Pose botTouchingRedGoal = new Pose(123,123,Math.toRadians(180+36));//The position when the back of the robot is contacting the side of the goal. Good starting position for tests I think
}
