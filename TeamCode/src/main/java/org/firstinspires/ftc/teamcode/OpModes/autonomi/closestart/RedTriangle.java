package org.firstinspires.ftc.teamcode.OpModes.autonomi.closestart;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.OpModes.autonomi.AutoSuperClass;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TTimer;
import org.firstinspires.ftc.teamcode.SectionedTelemetry;

@Autonomous(name = "🥩 RED TRIANGLE! 🥩")
public class RedTriangle extends AutoSuperClass {

    Follower follower;
    Bot bot;
    SectionedTelemetry telemetry;
    Paths paths;

    Pose startPose = new Pose(97.300, 9.540, Math.toRadians(90));
    Pose shootPose = new Pose(84.000, 20.000, Math.toRadians(65));

    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path7;
        public PathChain Path8;
        public PathChain Path9;
        public PathChain Path10;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(97.300, 9.540),
                            new Pose(84.000, 20.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(65)).build();

            Path5 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(84.000, 20.000),
                            new Pose(130.000, 20.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0)).build();

            Path4 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(130.000, 20.000),
                            new Pose(84.000, 20.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(65)).build();

            Path2 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(84.000, 20.000),
                            new Pose(103.000, 36.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0)).build();

            Path3 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(103.000, 36.000),
                            new Pose(130.000, 36.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(5)).build();

            Path7 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(130.000, 36.000),
                            new Pose(84.000, 20.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(5), Math.toRadians(65)).build();

            Path8 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(84.000, 20.000),
                            new Pose(103.000, 60.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0)).build();

            Path9 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(103.000, 60.000),
                            new Pose(130.000, 60.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0)).build();

            Path10 = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(130.000, 60.000),
                            new Pose(84.000, 20.000)
                    )
            ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(65)).build();
        }
    }

    public boolean incrementingStep() {
        return !gamepad1.a;
    }

    @Override
    public void init() {
        bot = new Bot(hardwareMap, FieldDimensions.goalPositionRed);
        follower = bot.follower;

        paths = new Paths(follower);
        follower.setStartingPose(startPose);

        telemetry = new SectionedTelemetry(super.telemetry);

        initSteps(
                () -> {
                    follower.followPath(paths.Path1, true);
                    nextStep();
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.launchHandler.initLaunch();
                        nextStep();
                    }
                },
                () -> {
                    if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL && incrementingStep()) {
                        bot.intake.setPower(1);
                        follower.followPath(paths.Path5, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(0);
                        follower.followPath(paths.Path4, true);
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
                        follower.followPath(paths.Path2, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(1);
                        follower.followPath(paths.Path3, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(0);
                        follower.followPath(paths.Path7, true);
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
                        follower.followPath(paths.Path8, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(1);
                        follower.followPath(paths.Path9, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.intake.setPower(0);
                        follower.followPath(paths.Path10, true);
                        nextStep();
                    }
                },
                () -> {
                    if (!follower.isBusy() && incrementingStep()) {
                        bot.launchHandler.initLaunch();
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
        bot.spinFlyWheelWithinFeasibleRange(shootPose.getAsVector());
    }

    public void autonomousPathUpdate() {
        bot.update();
        bot.aimTurret();
        if (bot.launchHandler.launchPhase == Bot.LaunchPhase.NULL) {
            bot.spinFlyWheelWithinFeasibleRange(shootPose.getAsVector());
        }
        bot.updatePID(shootPose.getAsVector());
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
