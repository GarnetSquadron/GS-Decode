package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.TTimer;

import java.util.function.DoubleSupplier;

public class ServoSub {
    private final Servo servo;
    private double Max;
    private double Min;
    public TTimer timer;
    double runtimeCoefficient;
    private boolean powered = false;
    public ServoSub(HardwareMap hardwareMap, String name, double min, double max, DoubleSupplier time, double runtimeCoefficient/*the time it takes for the servo to rotate to its maximum position*/) {
        servo = hardwareMap.get(Servo.class, name);
        Max = max;
        Min = min;
        timer = new TTimer(time);
        this.runtimeCoefficient = runtimeCoefficient;
    }
    public ServoSub(HardwareMap hardwareMap, String name, double min, double max, DoubleSupplier time){
        this(hardwareMap, name, min, max,time,2);

    }

    /**
     * after all this effort to restrict the movement in code I find the function servo.scaleRange(). I guess its too late to change it now lol. maybe later
     * @param ratio
     * @return
     */
    double getPosFromRatio(double ratio){
        return Min+ratio*(Max-Min);
    }
    double getRatioFromPos(double pos){
        return (pos-Min)/(Max-Min);
    }
    public void goToRatio(double ratioPos){
        ratioPos = ExtraMath.Clamp(ratioPos,1,0);
        double deltaPos = getPosFromRatio(ratioPos)-getPos();
        double distanceToTgt = Math.abs(deltaPos);
        boolean alreadyTargeted = ExtraMath.ApproximatelyEqualTo(distanceToTgt, 0, 0.01);
        if(!powered)
            servo.setPosition(1);//on init, the servo position is set to 0, even though it isnt powered and probably isnt at 0. if you then run servo.setposition(0), it will not move because it already this
        else if (!alreadyTargeted) {//if not trying to get there
            timer.StartTimer(runtimeCoefficient* distanceToTgt);//when the timer goes off, the servo should be at the correct position. this needs to be tuned
        }
        servo.setPosition(getPosFromRatio(ratioPos));
        powered = true;
    }

    public void MoveToMax() {
        goToRatio(1);
    }
    public void MoveToMin() {
        goToRatio(0);
    }
//    private double getToothSize(int teeth){
//        return 4.0/(teeth*3.0);
//    }
    public double getPos(){
        return servo.getPosition();
    }
    public double getRatioPos(){
        return getRatioFromPos(getPos());
    }
    public boolean targetReached(){
        return timer.timeover()|| !timer.timestarted();
    }
    public void changePosBy(double delta){
        goToRatio(getRatioPos()+delta);
    }
    public boolean isPowered(){
        return powered;
    };
    public boolean atMax(){
        return ExtraMath.ApproximatelyEqualTo(servo.getPosition(),Max,0.01);
    }

}
