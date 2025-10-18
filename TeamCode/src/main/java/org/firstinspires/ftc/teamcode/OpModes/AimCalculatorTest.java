package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;

import com.bylazar.configurables.PanelsConfigurables;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AngleFinder;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.Constants;

@TeleOp(name = "aimCalc" )
public class AimCalculatorTest extends OpMode {
    Launcher launcher;
        public double[] aimAtGoal(double[] goalPos, double[] botPos, double vel) {
            double distance =  Math.sqrt(Math.pow(goalPos[0] - botPos[0],2)+Math.pow(goalPos[1]-botPos[1],2));
            double angle = AngleFinder.getOptimumAngle(vel,distance);
            launcher.setAngle(angle,1);
            double rotAngle = Math.atan((goalPos[1] - botPos[1]) /(goalPos[0]-botPos[0]));
            launcher.setTurretRotation(rotAngle);
            return new double[] {angle,distance};
        }
    @Override
    public void init() {
        if (follower == null) {
            follower = Constants.createFollower(hardwareMap);
            PanelsConfigurables.INSTANCE.refreshClass(this);
        } else {
            follower = Constants.createFollower(hardwareMap);
        }

        follower.setStartingPose(new Pose());
        launcher = new Launcher(hardwareMap);
    }

    @Override
    public void loop() {double g = -386.09;//9.8 m/s^2=386.09in/s
        follower.update();
        telemetry.addData("position",follower.getPose());
        if (gamepad1.a) {
            double vel = 236.22;
            double[] botPos = new double[] {follower.getPose().getX(), follower.getPose().getY()};
            double[] angdist = aimAtGoal(new double[]{0,0},botPos,vel);


            telemetry.addData("angle",angdist[0]);
            telemetry.addData("distance",angdist[1]);
        }
        if (gamepad1.y) {
            launcher.setPower(1);
        }else {launcher.setPower(0);}
        telemetry.update();
    }
}