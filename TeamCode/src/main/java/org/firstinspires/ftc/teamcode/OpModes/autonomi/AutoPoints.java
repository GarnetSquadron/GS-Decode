package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;

public class AutoPoints
{

    public static double farLaunchY = 20;
    public static double closeLaunchY = 84;
    public static double intakingTargetX = 120;
    public static Pose farShootPose =  new Pose(84, farLaunchY,Math.PI);
    public static Pose closeShootPose = new Pose(90.000, closeLaunchY, Math.toRadians(65));
    public static double intakiningPrepX = 103;
    public static Pose intakingPrepPos1 = new Pose(intakiningPrepX, 84);
    public static Pose intakingTargetPos1 = new Pose(intakingTargetX, 84);
    public static Pose intakingPrepPos2 = new Pose(intakiningPrepX, 59);
    public static Pose intakingTargetPos2 = new Pose(intakingTargetX, 59.000);
    public static Pose intakingPrepPos3 = new Pose(intakiningPrepX, 34);
    public static Pose intakingTargetPos3 = new Pose(intakingTargetX, 34.8);


}