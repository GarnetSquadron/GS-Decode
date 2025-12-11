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
import org.firstinspires.ftc.teamcode.HardwareControls.Intake;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.HardwareControls.Turret;
import org.firstinspires.ftc.teamcode.PurelyCalculators.GamepadClasses.GamepadClasses.BetterControllerClass;
import org.firstinspires.ftc.teamcode.Vision.aprilTags.ObeliskIdentifier;
import org.firstinspires.ftc.teamcode.HardwareControls.hardwareClasses.motors.RAWMOTOR;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

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
            s.add("intake stall test",IntakeStallDetectTest::new);
            s.add("motor test",MotorTest::new);
            s.add("servo test",ServoTest::new);
            s.add("DistanceSensorDelayTest", SensorDelayTest::new);
            s.add("intake Tests", IntakeTests::new);
            s.add("turret math", TurretMathTest::new);
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

/**
 * runs the intake (while a is pressed) until the motor is staling, at which point it stops, and waits for you to stop pressing a before allowing you to try again
 */
class IntakeStallDetectTest extends OpMode{
    Intake intake;
    boolean hasStalled = false;
    @Override
    public void init()
    {
        intake = new Intake(hardwareMap);
    }

    @Override
    public void loop()
    {
        if(hasStalled){
            intake.stop();
            hasStalled = gamepad1.a;//change has stalled to false if you stop holding a, at which point the code goes back to the else block
        } else{
            if(gamepad1.a){
                intake.setPower(-1);
            } else{
                intake.stop();
            }
        }
        //telemetry.addData("current", intake.getMilliamps());
        telemetry.addData("has stalled", hasStalled);
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
        servo = hardwareMap.get(Servo.class, "servoKicker");
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
class RandomFunctionsTest extends OpMode{

    @Override
    public void init(){

    }

    @Override
    public void loop()
    {

    }
}

class IntakeTests extends OpMode{
    boolean launching = false;
    boolean gateOpen;
    Intake intake;
    boolean wasYPressed = false;
    Launcher launcher;
    @Override
    public void init()
    {
        intake = new Intake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        intake.closeGate();
        intake.unKick();
    }

    @Override
    public void loop()
    {

        if (gamepad1.left_bumper){
            intake.timeWhenIntake = TIME.getTime();
            intake.setPower(1);
        }
        if (gamepad1.right_bumper){
            launcher.setPower(-1);
        }
//        if (launching){
//            telemetry.addData("time since launch",intake.shoot3());
//            launching = !(intake.shoot3()>7);
//        }else {
//            intake.setPower(0);
//            launching = gamepad1.y;
//        }
        if(gamepad1.y){
            intake.startShoot3();
        }
//        intake.shoot3();
        double current = intake.getCurrent();
        telemetry.addData("is gate open",gateOpen);
        telemetry.addData("intake current",current);
        telemetry.addData("ball count", intake.countArtifacts(intake.getCurrent()));
        telemetry.addData("intake time with diff", TIME.getTime()-intake.timeWhenIntake);
        telemetry.addData("time",TIME.getTime());
        telemetry.addData("launcher current", launcher.getCurrent());
        telemetry.addData("launching",launching);
    }
}
class TurretMathTest extends OpMode{
    Turret turret;
    double inputAngle = 0;
    BetterControllerClass Gpad;
    @Override
    public void init()
    {
        Gpad = new BetterControllerClass(gamepad1);
        turret = new Turret(hardwareMap);
    }

    @Override
    public void loop()
    {
        Gpad.update();
        if(Gpad.getRisingEdge("a")){
            inputAngle+=0.1;
        }
        if (Gpad.getRisingEdge("b")){
            inputAngle-=0.1;
        }
        telemetry.addData("input angle (rads)",inputAngle);
        telemetry.addData("output angle (rads)",turret.getRotation(inputAngle));
        telemetry.update();
    }
}