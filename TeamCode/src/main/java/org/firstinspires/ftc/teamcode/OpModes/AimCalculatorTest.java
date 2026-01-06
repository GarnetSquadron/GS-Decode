package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;

import com.bylazar.configurables.PanelsConfigurables;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.TestConstants;

@TeleOp(name = "aimCalc" )
public class AimCalculatorTest extends OpMode {
    Launcher launcher;
    @Override
    public void init() {
        if (follower == null) {
            follower = TestConstants.createFollower(hardwareMap);
            PanelsConfigurables.INSTANCE.refreshClass(this);
        } else {
            follower = TestConstants.createFollower(hardwareMap);
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
            double[] angdist = launcher.aimAtGoal(new double[]{0, 0}, botPos, vel,follower.getPose().getHeading());


            telemetry.addData("angle",angdist[0]);
            telemetry.addData("distance",angdist[1]);
        }
        if (gamepad1.y) {
            launcher.setPower(1);
        }else {launcher.setPower(0);}
        telemetry.update();
    }
}