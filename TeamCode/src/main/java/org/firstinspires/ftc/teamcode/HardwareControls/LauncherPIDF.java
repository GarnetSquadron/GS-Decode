package org.firstinspires.ftc.teamcode.HardwareControls;

import org.firstinspires.ftc.teamcode.OpModes.SectTelemetryAdder;
import org.firstinspires.ftc.teamcode.PurelyCalculators.ExtraMath;
import org.firstinspires.ftc.teamcode.PurelyCalculators.time.TIME;

import java.util.Arrays;

public class LauncherPIDF
{
    public int sampleSize = 20;
    public double[] differences = new double[sampleSize];
    public double[] times = new double[sampleSize];
    public double[] derivatives =new double[5];
    public double Kp,Kd,Ki,Ks,Kv,Ka;
    public double p =0;
    public double d,f;
    public double pidForce = 0;
    public double margin = 10;
    SectTelemetryAdder telemetry;
    public LauncherPIDF(double Kp,double Kd,double Ki,double Ks,double Kv,double Ka){
        setConstants(Kp,Kd,Ki, Ks,Kv,Ka);
        telemetry = new SectTelemetryAdder("PIDF");
    }
    public void setConstants(double Kp,double Kd,double Ki,double Ks,double Kv,double Ka){
        this.Kp = Kp;
        this.Kd = Kd;
        this.Ki = Ki;
        this.Ks = Ks;
        this.Kv = Kv;
        this.Ka = Ka;
    }
    public void updateArrays(double velocity, double targetVel){
        differences = updateArray(differences,velocity-targetVel);
        times = updateArray(times, TIME.getTime());
        derivatives = updateArray(derivatives,(differences[0]- differences[sampleSize-1])/(times[0]-times[sampleSize-1]));
    }
    public double getPid(double velocity, double targetVel) {
        //updateArrays(velocity,targetVel);
        p = (targetVel-velocity)*Kp;
        f = getFeedForward(targetVel);
        d = (getAcceleration()*Kd);

//        if(velocity>targetVel/3){
            //because we are controlling velocity, not position, the sum of p, i, and d needs to be the amount that the output gets incremented by, not the output itself
            pidForce += (p + d) * (times[0]-times[1]);
            pidForce = ExtraMath.Clamp(pidForce,1-f,-1-f);
//        }
        telemetry.addData("PID Force",pidForce);
        telemetry.addData("F Force",f);
        return pidForce +f;
    }
    public double getAcceleration(){
        return average(derivatives);
    }
    public boolean lowAcceleration(){
        return ExtraMath.closeTo0(getAcceleration(),20);
    }
    public boolean averageIsCloseToTarget(){
        return ExtraMath.closeTo0(differences[0],margin);
    }
    public boolean AverageCloseToTarget(){
        return ExtraMath.closeTo0(average(differences),margin);
    }
    public boolean hasStabilized(){
        return lowAcceleration() && averageIsCloseToTarget();
    }
    public boolean hasDestabilized(){
        return lowAcceleration() && averageIsCloseToTarget();
    }
//    public boolean hasStabilizedRelaxed(){
//        return lowAcceleration() && closeToTarget();
//    }
    public double getFeedForward(double targetVel){
        return Ks *Math.signum(targetVel)+Kv *targetVel;
    }
    public void resetPid(){
        pidForce = 0;
        Arrays.fill(times,TIME.getTime());
    };
    public static double[] updateArray(double[]arr,double val){
        for(int i=arr.length-1;i>0;i--){
            arr[i]=arr[i-1];
        }
        arr[0]=val;
        return arr;
    }
    public static double average(double[]arr){
        double sum = 0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
        }
        return sum/arr.length;
    }

}
