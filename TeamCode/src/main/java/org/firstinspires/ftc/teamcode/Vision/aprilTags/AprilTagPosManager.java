package org.firstinspires.ftc.teamcode.Vision.aprilTags;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pathing.pedroPathing.CompConstants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import static org.firstinspires.ftc.teamcode.pathing.pedroPathing.Tuning.follower;
import com.pedropathing.geometry.Pose;


import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//Adds telemetry
import org.firstinspires.ftc.teamcode.Telemetry.SectTelemetryAdder;

//tracks april tags with webcam
public class AprilTagPosManager {


    // A an instance of the AprilTagProcessor class
    private AprilTagProcessor aprilTagProcessor;

    // An instance of the Vision Portal class
    private VisionPortal visionPortal;

    //An instance of the SectTelemtryAdder Class
    SectTelemetryAdder telemetry;

    // Constructor
    public AprilTagPosManager(HardwareMap hardwareMap) {
        //Calls SectTelemtryAdder constructor
        telemetry = new SectTelemetryAdder("APRIL TAG");

        //Basically calls a constructor, don't worry about it
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();

        //Basically calls a constructor, don't worry about it
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTagProcessor);
    }

    //Prints the position of every tag
    public Pose getOffset(){
        telemetry.addLine("RBE = Bearing");
        Pose pose = new Pose();
        for(AprilTagDetection detection : aprilTagProcessor.getDetections()){
            telemetry.addLine(String.format("RBE %6.1f  (deg)", detection.ftcPose.bearing));
            pose = new Pose(0,0,detection.ftcPose.bearing);
        }
        return pose;
    }
}

