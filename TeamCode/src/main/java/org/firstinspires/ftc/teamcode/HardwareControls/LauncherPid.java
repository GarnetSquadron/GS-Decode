package org.firstinspires.ftc.teamcode.HardwareControls;

public class LauncherPid {
    static double steadyForceConst = 0.5;
    public double[] difference = new double[]{0,0,0,0,0};
    public double[] derivatives =new double[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    public double p =0;
    public double d;
    double force = 0;
    public double GetPid(double velocity, double targetVel,double give, double forceDamp,double kP, double kD) {
        difference=updateArray(difference,targetVel-velocity);
        derivatives=updateArray(derivatives,(difference[0]-difference[4]));
        p = difference[4]*kP;
        d = ((average(derivatives))*kD);

        force += p+d;
        return force;

    }
    public double[] updateArray(double[]arr,double val){
        for(int i=1;i<arr.length;i++){
            arr[i-1]=arr[i];
        }
        arr[arr.length-1]=val;
        return arr;
    }
    public double average(double[]arr){
        double sum = 0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
        }
        return sum/arr.length;
    }

}
