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
        telemetry.addLine("hi");
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
        derivatives = updateArray(derivatives,getAverageAccel(0,sampleSize-1));
    }
    public double getAverageAccel(int index1, int index2){
        return (differences[index1]- differences[index2])/(times[index1]-times[index2]);
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
        telemetry.addData("p increment",p);
        telemetry.addData("d increment",d);
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
    public boolean currentIsCloseToTarget(){
        return ExtraMath.closeTo0(differences[0],margin);
    }
    public boolean averageCloseToTarget(){
        return ExtraMath.closeTo0(differences[0],margin);
    }
    public boolean hasStabilized(){
        return lowAcceleration() && averageCloseToTarget();

//        for(int i=0;i<sampleSize;i++){
//            if(!ExtraMath.closeTo0(differences[i],margin-2)){
//                return false;
//            };
//        }
//        return true;
    }
    public boolean closeToTarget(){
        return ExtraMath.closeTo0(average(differences),margin);
    }
    public boolean hasDestabilized(){
        return !ExtraMath.closeTo0(differences[0],margin);
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
//    public static double getAverageAcceleration(int iterations){
//        double sum = 0;
//        for(int i = 0;i<iterations;i++){
//            sum+=;
//        }
//        return sum/iterations;
//    }

}
