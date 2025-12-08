package org.firstinspires.ftc.teamcode.OpModes.teleops;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;



@TeleOp(name = "Main teleop")
public class MainTeleop extends OpMode
{
    public static Follower follower;
    Intake intake;
    Launcher launcher;
    double velScale = 0.6;
    double servoPos = 0.5;
    double launcherPower = 0.6;

    BetterControllerClass Gpad;

    @Override
    public void init()
    {
        intake = new Intake(hardwareMap);
        launcher = new Launcher(hardwareMap);

        Gpad = new BetterControllerClass(gamepad1);

        //set up follower
        follower = CompConstants.createFollower(hardwareMap);
//        PanelsConfigurables.INSTANCE.refreshClass(this);

        follower.setStartingPose(FieldDimensions.botTouchingRedGoal);
        follower.startTeleopDrive();
        telemetry.addData("follower",follower);
    }

    public void loop(){
        follower.update();

        follower.setTeleOpDrive(-gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y), Math.abs(gamepad1.left_stick_x)*gamepad1.left_stick_x, Math.abs(gamepad1.right_stick_x)*gamepad1.right_stick_x, true);
        Gpad.update();

        //I wanted to find a better way, but this seems like the best option for organizing the button inputs
        //==============================INPUTS====================================\\
        boolean kickInput = Gpad.getCurrentValue("right_trigger");
        boolean intakeToggle = Gpad.getToggleValue("right_bumper");
        boolean launcherToggle = Gpad.getToggleValue("left_bumper");
        boolean turretZeroInput = gamepad1.x;
        boolean autoAimOn = gamepad1.b;
        boolean openGateInput = gamepad1.a;

        servoPos = gamepad1.left_trigger*20+30;

        //==============================OUTPUTS===================================\\

        //welcome to the now slightly contained mess

        //intake
//        if(gamepad1.x){
//            intake.setPower(-1);
//        } else {
        intake.setPower(intakeToggle ?1:0);
//        }
        //if (gamepad1.b){intake.loadBall();};

//        if (gamepad1.x){
//            intake.prepareForIntaking();
//        } else intake.unprepareIntake();
        //launcher

        launcher.setPower(launcherToggle?-launcherPower:0);
        if(turretZeroInput){
            launcher.zeroTurret();
        }
        if(autoAimOn){
            telemetry.addData("target",launcher.aimTurret(FieldDimensions.goalPositionBlue, new double[] {follower.getPose().getX(), follower.getPose().getY()},follower.getPose().getHeading()));
        } else {
            launcher.setTurretPower(0);
        }
        if(turretZeroInput){
            launcher.zeroTurret();
        }
        if(openGateInput){
            intake.openGate();
        }
        else {
            intake.closeGate();
        }
        double distance = Math.hypot(FieldDimensions.goalPositionBlue[0]-follower.getPose().getX(), FieldDimensions.goalPositionBlue[1]-follower.getPose().getY());

        //manualOrAutoAimHood(gamepad1.b,distance);
        //toggleLaunchPower(Gpad.getToggleValue("gamepad1.rightTrigger"));// there are too many buttons in use rn so I am taking this away for now
        launcherPower = 0.9;

        if(kickInput){
            intake.kickBall();
        } else{
            intake.unKick();
        }

        //========================TELEMETRY===========================\\
//        telemetry.addData("left gate position",intake.getGatePositions()[0]);
//        telemetry.addData("right gate position",intake.getGatePositions()[1]);
//        telemetry.addData("left stick x",gamepad1.left_stick_x);
//        telemetry.addData("left stick y",gamepad1.left_stick_y);
//        telemetry.addData("right stick x",gamepad1.right_stick_x);
//        telemetry.addData("right stick y",gamepad1.right_stick_y);
        telemetry.addData("rotation",launcher.getTurretEncoder().getPos());
        telemetry.addData("ticks",launcher.getTurretEncoder().getTicks());
        telemetry.addData("CPR",launcher.getTurretEncoder().getCPR());
        telemetry.addData("scale",launcher.getTurretEncoder().getScale());
        telemetry.addData("motor type", launcher.getMotorType());
        telemetry.addLine("---------Position-------");
        telemetry.addData("position",follower.getPose());//TODO: check this, I think that the position is not getting updated, its possible the pinpoint isn't connected well or something
        telemetry.addData("goal x",FieldDimensions.goalPositionBlue[0]);
        telemetry.addData("goal y",FieldDimensions.goalPositionBlue[1]);
        telemetry.addData("distance", distance);
        telemetry.addData("velScale",velScale);
//        telemetry.addData("servoPos but better", (servoPos-Math.toRadians(30))*0.5/ Math.toRadians(20));
//        telemetry.addData("launcherPower", launcherPower);
//        telemetry.addData("lbump toggle",Gpad.getToggleValue("left_bumper"));
//        //telemetry.addData("hoodPos",launcher.getHoodPos());
//        telemetry.addData("lbump val",Gpad.getCurrentValue("left_bumper"));
//        telemetry.addData("lbump redge",Gpad.getRisingEdge("left_bumper"));
        //telemetry.addData("lbump changed",Gpad.getCurrentValue("left_bumper"));
        telemetry.update();

    }
}
