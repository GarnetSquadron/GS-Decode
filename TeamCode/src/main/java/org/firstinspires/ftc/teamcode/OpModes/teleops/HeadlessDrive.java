package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
///import ViperSlidesSubSystem;

@TeleOp(name = "Headless Drive")
public class HeadlessDrive extends LinearOpMode
{
    //ViperSlidesSubSystem viperSlidesSubSystem = new ViperSlidesSubSystem();
    DcMotorEx lf, rf, lb, rb;

    @Override
    public void runOpMode() throws InterruptedException
    {

    }
}
