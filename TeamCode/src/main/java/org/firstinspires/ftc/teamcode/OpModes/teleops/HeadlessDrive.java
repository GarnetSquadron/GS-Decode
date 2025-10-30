package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.bylazar.configurables.PanelsConfigurables;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;

///import ViperSlidesSubSystem;

@TeleOp(name = "Headless Drive")
public class HeadlessDrive extends OpMode
{
    //ViperSlidesSubSystem viperSlidesSubSystem = new ViperSlidesSubSystem();
    public static Follower follower;

    @Override
    public void init()
    {
        follower = CompConstants.createFollower(hardwareMap);
        PanelsConfigurables.INSTANCE.refreshClass(this);

        follower.setStartingPose(new Pose());
    }

    public void loop(){
        follower.update();
        follower.setTeleOpDrive(gamepad1.right_stick_x,gamepad1.right_stick_y,gamepad1.left_stick_x,true);
    }
}
