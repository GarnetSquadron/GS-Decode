package org.firstinspires.ftc.teamcode.OpModes.autonomi.Unused;

import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.closeShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.farShootPose;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingPrepPos2;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingPrepPos3;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos2;
import static org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoPoints.intakingTargetPos3;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoSuperClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TTimer;

//@Autonomous(name = "🥩 RED TRIANGLE! 🥩")
public class RedTriangle extends AutoSuperClass {

    Follower follower;
    Bot bot;
//    SectionedTelemetry telemetry;

    public static Pose startPose = FieldDimensions.botOnTinyTriangleRedSide; //new Pose(97.300, 9.540, Math.toRadians(90));

    static public Path intake1, launch2,intakePrep2,intake2, launch3,intakePrep3,intake3, launch4;

    static  {
        intake1 = new Path(
                new BezierLine(
                        startPose,
                        intakingTargetPos3
                )
        );
        intake1.setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(65));

        launch2 = new Path(
                new BezierLine(
                        intakingTargetPos3,
                        closeShootPose
                )
        );
        launch2.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(65));

        intakePrep2 = new Path(
                new BezierLine(
                        closeShootPose,
                        intakingPrepPos2
                )
        );
        intake2 = new Path(
                new BezierLine(
                        intakingPrepPos2,
                        intakingTargetPos2
                )
        );
        intake2.setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0));

        launch3 = new Path(
                new BezierLine(
                        intakingTargetPos2,
                        closeShootPose
                )
        );
        launch3.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(5));

        intakePrep3 = new Path(
                new BezierLine(
                        closeShootPose,
                        intakingPrepPos3
                )
        );

        intake3 = new Path(
                new BezierLine(
                        intakingPrepPos3,
                        intakingTargetPos1
                )
        );
        intake3.setLinearHeadingInterpolation(Math.toRadians(5), Math.toRadians(65));

        launch4 = new Path(
                new BezierLine(
                        intakingTargetPos1,
                        closeShootPose
                )
        );
        launch4.setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0));
    }

    public boolean incrementingStep() {
        return !gamepad1.a;
    }

    @Override
    public void init() {
        bot = new Bot(hardwareMap, FieldDimensions.goalVectorRed);
        follower = bot.follower;
        follower.setStartingPose(startPose);

//        telemetry = new SectionedTelemetry(super.telemetry);

        initSteps(
                () -> {
                    bot.launchHandler.initLaunch();
                    nextStep();
                },
                () -> {
                    if(bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL){
                        bot.intake.setPower(1);
                        follower.followPath(intake1, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(0);
                        follower.followPath(launch2, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () -> {
                    if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL && incrementingStep()) {
                        follower.followPath(intakePrep2, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(1);
                        follower.followPath(intake2, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(0);
                        follower.followPath(launch3, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        follower.followPath(intakePrep3, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(1);
                        follower.followPath(intake3, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(0);
                        follower.followPath(launch4, true);
                        nextStep();
                    }
                },
                () -> { }
        );
    }

    public TTimer stopTimer;

    public void start() {
        stopTimer = new TTimer();
        stopTimer.StartTimer(29);
        setCurrentStep(0);
        bot.launcher.resetPID();
        bot.spinFlywheelToTunedSpeed(farShootPose.getAsVector());
    }

    public void autonomousPathUpdate() {
        bot.update();
        bot.aimTurret();
        if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL) {
            bot.spinFlywheelToTunedSpeed(farShootPose.getAsVector());
        }
        bot.updateSpeedMeasure(farShootPose.getAsVector());
        updateSteps();
    }

    @Override
    public void loop() {
        follower.update();
        follower.setMaxPower(1);

        if (stopTimer.timeover() || gamepad1.a) {
            follower.breakFollowing();
            bot.intake.setPower(0);
            bot.launcher.setPower(0);
            bot.updateCurrentPos();
            return;
        }

        autonomousPathUpdate();

        telemetry.addData("step", currentStep);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.clear();
    }
}
