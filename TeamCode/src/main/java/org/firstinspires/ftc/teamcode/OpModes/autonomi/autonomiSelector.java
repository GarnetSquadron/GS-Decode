package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import static org.firstinspires.ftc.teamcode.OpModes.autonomi.autonomiSelector.follower;

import com.bylazar.configurables.PanelsConfigurables;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.telemetry.SelectableOpMode;
import com.pedropathing.util.PoseHistory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pathing.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing.AllBlue;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing.AllRed;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.pathing.test;


import java.util.ArrayList;

@TeleOp(name = "autonomiSelector")
public class autonomiSelector extends SelectableOpMode
{
    public static Follower follower;
    @IgnoreConfigurable
    static PoseHistory poseHistory;

    @IgnoreConfigurable
    static TelemetryManager telemetryM;

    @IgnoreConfigurable
    static ArrayList<String> changes = new ArrayList<>();

    static PathChain[] paths = {AllBlue.paths, AllRed.paths, test.line1};


    public autonomiSelector()
    {
        super("Autonomous Selector",s -> {
            s.folder("purely the paths",purePaths ->{
                for(PathChain path:paths){
                    purePaths.add(path.toString(),()->new PurelyPathOpMode(path));
                }
            });
            s.folder("actual autos",a->{
                a.add("OneBlueSideSix", OneBlueSideSixAuto::new);
            });
        });

    }
    @Override
    public void onSelect() {
        if (follower == null) {
            follower = Constants.createFollower(hardwareMap);
            PanelsConfigurables.INSTANCE.refreshClass(this);
        } else {
            follower = Constants.createFollower(hardwareMap);
        }

        follower.setStartingPose(new Pose());

        poseHistory = follower.getPoseHistory();

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }
}
class PurelyPathOpMode extends OpMode
{

    PathChain path;
    public PurelyPathOpMode(PathChain path){
        this.path = path;
    }

    @Override
    public void init() {
    }
    @Override
    public void loop() {
        follower.followPath(path);
        if(follower.isBusy()){
            follower.update();
            telemetry.addData("path", follower.getChainIndex());
            telemetry.update();
        }
    }

}

