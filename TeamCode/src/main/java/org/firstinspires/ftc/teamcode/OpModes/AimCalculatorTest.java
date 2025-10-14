package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;

import com.bylazar.configurables.PanelsConfigurables;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AngleFinder;
import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.Constants;

@TeleOp(name = "aimCalc" )
public class AimCalculatorTest extends OpMode {
    Launcher launcher;
        public double aimAtGoal(double[] goalPos, double[] botPos, int vel) {
            double distance = Math.toRadians( Math.sqrt(((((goalPos[0] + goalPos[1])*2)+((botPos[0]-botPos[1])*2)))));
            double angle = AngleFinder.getOptimumAngle(vel,distance);
            launcher.setAngle(angle,0);
            double rotAngle = Math.atan((goalPos[1] - botPos[1]) /(goalPos[0]-botPos[0]));
            launcher.setTurretRotation(rotAngle);
            return angle;
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
    public void loop() {
        follower.update();
        if (gamepad1.a) {
            double[] botPos = new double[] {follower.getPose().getX(), follower.getPose().getY()};
            telemetry.addData("angle",aimAtGoal(FieldDimensions.goalPositionRed,botPos,6));
            telemetry.update();
        }
        if (gamepad1.y) {
            launcher.setPower(1);
        }else {launcher.setPower(0);}
    }
}