package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.TestConstants;
import org.firstinspires.ftc.teamcode.HardwareControls.Intake;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;


@TeleOp(name = "AutoRedAll")
public class AllRed extends LinearOpMode
{
    Follower follower;

    @Override
    public void runOpMode() throws InterruptedException
    {
        follower = TestConstants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();
        PathBuilder builder = follower.pathBuilder();
        Intake intake = new Intake(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);

        PathChain ShootPreload = builder
                .addPath(
                        // Path 1, Go to shoot at position, change ALL Math.toRadians(65) values to adjust the FAR shooting horizontal angle
                        new BezierLine(new Pose(88.000, 8.000), new Pose(80.000, 20.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(65))
                .build();

        PathChain CollectClose = builder
                .addPath(
            // Path 2, Go to collect closest pattern
            new BezierCurve(
                    new Pose(80.000, 20.000),
                    new Pose(83.155, 37.521),
                    new Pose(134.113, 32.451)
            )
    )
                .setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0))
                .build();

        PathChain Shoot1 = builder
                .addPath(
                        // Path 3, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierCurve(
                                new Pose(134.113, 32.451),
                                new Pose(97.099, 41.070),
                                new Pose(78.000, 78.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();

        PathChain CollectMiddle = builder
                .addPath(
                        // Path 4, Go to collect middle pattern
                        new BezierCurve(
                                new Pose(78.000, 78.000),
                                new Pose(92.535, 59.324),
                                new Pose(126.000, 57.296)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                .build();

        PathChain Shoot2 = builder
                .addPath(
                        // Path 5, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierLine(new Pose(126.000, 57.296), new Pose(78.000, 78.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();

        PathChain CollectFar = builder
                .addPath(
                        // Path 6, Go to collect furthest pattern
                        new BezierCurve(
                                new Pose(78.000, 78.000),
                                new Pose(100.141, 84.169),
                                new Pose(128.282, 83.155)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                .build();

        PathChain Shoot3 = builder
                .addPath(
                        // Path 7, Go to shoot at close position, change ALL Math.toRadians(45) values to adjust the CLOSE shooting horizontal angle
                        new BezierLine(new Pose(128.282, 83.155), new Pose(78.000, 78.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                .build();

        follower.update();
        waitForStart();
        follower.followPath(ShootPreload);

        while(follower.isBusy()){
            follower.update();

            if(follower.getChainIndex()==1){
                follower.pausePathFollowing();
                launcher.setPower(-0.7);
            }

            if(follower.getChainIndex()==2){
                intake.setPower(1);
            }

            if(follower.getChainIndex()==3){
                follower.pausePathFollowing();
                intake.setPower(0);
                launcher.setPower(0.7);
            }

            if(follower.getChainIndex()==4){
                intake.setPower(1);
            }

            if(follower.getChainIndex()==5){
                follower.pausePathFollowing();
                intake.setPower(0);
                launcher.setPower(0.7);
            }

            if(follower.getChainIndex()==6){
                intake.setPower(1);
            }

            if(follower.getChainIndex()==7){
                follower.pausePathFollowing();
                intake.setPower(0);
                launcher.setPower(0.7);
            }

            telemetry.addData("path", follower.getChainIndex());
            telemetry.update();
        }
    }
}
