package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;

public class AutoPoints
{
//    public static boolean isBlue;

    public static double farLaunchY = 20;
    public static double closeLaunchY = 100;
    public static double intakingTargetX = 120;
    public static double pressingGateX = 128;
    public static Pose farShootPose =  new Pose(84, farLaunchY,Math.PI);
    public static Pose closeShootPose = new Pose(90.000, closeLaunchY, -Math.PI/5);
    public static double intakiningPrepX = 101;
    public static Pose intakingPrepPos1 = new Pose(intakiningPrepX, 84);
    public static Pose intakingTargetPos1 = new Pose(intakingTargetX, 84);
    public static Pose intakingPrepPos2 = new Pose(intakiningPrepX, 59);
    public static Pose intakingTargetPos2 = new Pose(intakingTargetX, 59.000);
    public static Pose intakingPrepPos3 = new Pose(intakiningPrepX, 34);
    public static Pose intakingTargetPos3 = new Pose(intakingTargetX, 34.8);
    public static Pose pressingAndIntakingGate = new Pose(134, 62,Math.toRadians(55));//new Pose(134.158, 58.232);

    public static Pose middlePressPrep = new Pose(intakingTargetX,66.5);
    public static Pose middleJustPressingGate = new Pose(pressingGateX, 66.5);//new Pose(134.158, 58.232);
    public static Pose closePressPrep = new Pose(intakingTargetX,79/*,-Math.PI/10*/);
    public static Pose closeJustPressingGate = new Pose(pressingGateX, 79);//new Pose(134.158, 58.232);
    public final static double intakeHPX = 135.5;
    public static Pose intakeHPPrep = new Pose(intakeHPX,24,-Math.PI/2);
    public static Pose intakeHP = new Pose(intakeHPX,9,-Math.PI/2);
    public static Pose getStart(boolean blueSide){
        return blueSide?FieldDimensions.botTouchingBlueGoal:FieldDimensions.botTouchingRedGoal;
    }
//    public static double[] targetPose = FieldDimensions.goalPositionRed;
//    public static double[]
//    public static Pose[] poses = new Pose[]{intakingPrepPos1,intakingTargetPos1,intakingPrepPos2,intakingTargetPos2,intakingPrepPos3,intakingTargetPos3,pressingAndIntakingGate,justPressingGate,closeShootPose,farShootPose};
//    public static double[] invertibleDoubles = new double[]{intakingTargetX,intakiningPrepX};
//    public static void setAlianceColor(boolean isBlue){
//        if(isBlue!=AutoPoints.isBlue){
//            AutoPoints.isBlue = isBlue;
//            intakingPrepPos1 = intakingTargetPos1.mirror();
//            intakingTargetPos1 = intakingTargetPos1.mirror();
//            intakingPrepPos2 = intakingPrepPos2.mirror();
//            intakingTargetPos2 = intakingTargetPos2.mirror();
//            intakingPrepPos3 = intakingPrepPos3.mirror();
//            intakingTargetPos3 = intakingTargetPos3.mirror();
//            pressingAndIntakingGate = pressingAndIntakingGate.mirror();
//            justPressingGate = justPressingGate.mirror();
//            closeShootPose = closeShootPose.mirror();
//            farShootPose = farShootPose.mirror();
//            intakingTargetX = -intakingTargetX;
//            intakiningPrepX = -intakiningPrepX;
////            for(int i=0;i<poses.length;i++){
////                poses[i] = poses[i].mirror();
////            }
//        }
//    }


}