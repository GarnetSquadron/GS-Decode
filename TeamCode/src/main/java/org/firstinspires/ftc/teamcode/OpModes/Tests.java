package org.firstinspires.ftc.teamcode.OpModes;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.hardware.maxbotix.MaxSonarI2CXL;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Vision.aprilTags.ObeliskIdentifier;
import org.firstinspires.ftc.teamcode.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.time.TIME;

import java.util.List;

@Configurable
@TeleOp(name = "Tests")
public class Tests extends SelectableOpMode
{
//    public static Follower follower;
//
//    @IgnoreConfigurable
//    static PoseHistory poseHistory;
//
//    @IgnoreConfigurable
//    static TelemetryManager telemetryM;
//
//    @IgnoreConfigurable
//    static ArrayList<String> changes = new ArrayList<>();

    public void onLog(List<String> lines) {}
    public Tests()
    {
        super("Select a Tuning OpMode", s -> {
            s.folder("Vision", v->{
                v.add("Obelisk Id Test",ObeliskIdTest::new);
            });
            s.add("motor test",MotorTest::new);
            s.add("servo test",ServoTest::new);
            s.add("DistanceSensorDelayTest", SensorDelayTest::new);
        });
    }

    @Override
    public void onSelect() {
//        if (follower == null) {
//            follower = Constants.createFollower(hardwareMap);
//            PanelsConfigurables.INSTANCE.refreshClass(this);
//        } else {
//            follower = Constants.createFollower(hardwareMap);
//        }
//
//        follower.setStartingPose(new Pose());
//
//        poseHistory = follower.getPoseHistory();
//
//        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }
}
class MotorTest extends OpMode
{
    RAWMOTOR lf,rf,lb,rb;
    @Override
    public void init()
    {
        rf = new RAWMOTOR(hardwareMap,"rf");
        rb = new RAWMOTOR(hardwareMap,"rb");
        lf = new RAWMOTOR(hardwareMap,"lf");
        lb = new RAWMOTOR(hardwareMap,"lb");
        telemetry.addData("lf pos", lf.getEncoder().getPos());
        telemetry.addData("rf pos", rf.getEncoder().getPos());
        telemetry.addData("lb pos", lb.getEncoder().getPos());
        telemetry.addData("rb pos", rb.getEncoder().getPos());
        //lb=lf, lf=rb, rb=rf, rf=lb
    }
    public void loop(){
        if (gamepad1.x) {
            rf.setPower(1);
        }
        else {
            rf.setPower(0);
        }
        if (gamepad1.y) {
            lf.setPower(1);
        }
        else {
            lf.setPower(0);
        }
        if (gamepad1.a) {
            rb.setPower(1);
        }
        else {
            rb.setPower(0);
        }
        if (gamepad1.b) {
            lb.setPower(1);
        }
        else {
            lb.setPower(0);
        }
        telemetry.update();
    }
}
class ObeliskIdTest extends OpMode
{
    ObeliskIdentifier obeliskIdentifier;
    @Override
    public void init()
    {
        obeliskIdentifier = new ObeliskIdentifier(hardwareMap);
    }

    @Override
    public void loop()
    {
        obeliskIdentifier.telemetryAprilTag(telemetry);
    }
}
class ServoTest extends OpMode
{
    Servo servo;

    public void init()
    {
        servo = hardwareMap.get(Servo.class, "servo");
    }
    public void loop(){
        if (gamepad1.x) {
            servo.setPosition(1);//up
        }

        if (gamepad1.a) {
            servo.setPosition(0.66666);
        }
        if (gamepad1.b) {
            servo.setPosition(0.33333);
        }
        if (gamepad1.y) {
            servo.setPosition(0);//down
        }
        if (gamepad1.left_stick_x != 0) {
            servo.setPosition(0.5 + gamepad1.left_stick_x * 0.5);
        }
        telemetry.addData("position", servo.getPosition());
        telemetry.update();
    }
}
class SensorDelayTest extends OpMode{
    TouchSensor touchSensor;
    DistanceSensor distanceSensor;
    ColorSensor colorSensor;
    MaxSonarI2CXL sonarSensor;

    double loopStartTime;
    double deltaTime = 0;
    boolean touched = false;
    double distance = 0;
    double sonarDistance = 0;
    int color = 0;
    @Override
    public void init()
    {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        sonarSensor = hardwareMap.get(MaxSonarI2CXL.class,"sonar");
        telemetry.addData("loopTime",deltaTime);
        telemetry.addData("distance", distance);
    }

    @Override
    public void loop()
    {
        loopStartTime = TIME.getTime();
        if(gamepad1.a)
        {
            touched = touchSensor.isPressed();
            telemetry.addData("touched", touched);
        }
        if(gamepad1.b)
        {
            distance = distanceSensor.getDistance(DistanceUnit.INCH);
            telemetry.addData("distance", distance);
        }
        if(gamepad1.x)
        {
            color = colorSensor.argb();
            telemetry.addData("color",color);
        }
        if(gamepad1.y)
        {
            sonarDistance = sonarSensor.getDistanceAsync(DistanceUnit.INCH);
            telemetry.addData("sonaDistance",sonarDistance);
        }

        deltaTime = TIME.getTime()-loopStartTime;
        telemetry.addData("loopTime",deltaTime);
        telemetry.update();
    }
}
