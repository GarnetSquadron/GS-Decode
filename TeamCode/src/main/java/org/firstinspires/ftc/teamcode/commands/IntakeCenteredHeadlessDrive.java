package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.function.DoubleSupplier;

/**
 * Instead of being in the
 */

public class IntakeCenteredHeadlessDrive extends CommandBase {
    MecanumDrive drive;
    DoubleSupplier xvel;
    DoubleSupplier yvel;
    DoubleSupplier AngularVel;
    DoubleSupplier CrankExtension;

    public IntakeCenteredHeadlessDrive(MecanumDrive m, DoubleSupplier x, DoubleSupplier y, DoubleSupplier angle,DoubleSupplier crankExtension){
        drive = m;
        xvel=x;
        yvel=y;
        AngularVel = angle;
        CrankExtension = crankExtension;
    }
    @Override
    public void execute(){
        drive.updatePoseEstimate();
        double direction = drive.pose.heading.toDouble();
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -Math.sin(drive.pose.heading.toDouble())*xvel.getAsDouble()-Math.cos(drive.pose.heading.toDouble())*yvel.getAsDouble()
                        /*+AngularVel.getAsDouble()*CrankExtension.getAsDouble()   <-this has the potential to be larger than 1, so it may throw error. I temporarily forgot that motors have a limit to their power*/,
                        -Math.cos(drive.pose.heading.toDouble())*xvel.getAsDouble()+Math.sin(drive.pose.heading.toDouble())*yvel.getAsDouble()
                ),
                -AngularVel.getAsDouble()
        ));
    }
}
