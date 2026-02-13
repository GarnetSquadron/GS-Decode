package org.firstinspires.ftc.teamcode.OpModes.autonomi;


import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Curve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.OpModes.SettingSelectorOpMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import kotlin.Pair;

/**
 * this class allows you write the process(ie the instructions run every loop) for each step of the auto in the form of lambdas.
 */
public abstract class AutoSuperClass extends SettingSelectorOpMode
{
//    private static ArrayList<Path> paths = new ArrayList<>();
//    private static ArrayList<Pose> poses = new ArrayList<>();
    public static HashMap<String,String> colorDefault = new HashMap<String,String>(){{put("color","red");}};;
    public static Pair<String[],String> colorSelections = new Pair(new String[]{"red","blue"},"color");

//    {
//        hashmap.put("color", "red");
//    }
    public AutoSuperClass(){
        super(new Pair[]{colorSelections}, colorDefault);
    }
    public AutoSuperClass(Pair<String[],String>[] settingsMap){
        super(settingsMap);
    }
    public AutoSuperClass(Pair<String[],String>[] settingsMap, HashMap<String,String> selections){
        super(settingsMap,selections);
    }
    public Timer pathTimer = new Timer();
    public int currentStep;
    Runnable[] steps;

    public double[] times;

//    public void initializePaths(){}

    /**
     * current way of making sure that paths get mirrored when its the blue side
     * @param bezierCurve
     * @return
     */
    public Path getPathFromBezierCurve(BezierCurve bezierCurve){
        Path ret;
        ArrayList<Pose> points = bezierCurve.getControlPoints();
        ret = new Path(bezierCurve);
        ret.setLinearHeadingInterpolation(points.get(0).getHeading(),points.get(points.size()-1).getHeading());
        return ret;
    }
    public BezierCurve mirrorBezierCurve(BezierCurve bezierCurve){
        ArrayList<Pose> points = bezierCurve.getControlPoints();
        for(int i=0;i<points.size();i++){
            points.set(i,points.get(i).mirror());
        }
        return new BezierCurve(points);
    }
    public BezierLine mirrorBezierLine(BezierLine bezierLine){
        ArrayList<Pose> points = bezierLine.getControlPoints();
        for(int i=0;i<points.size();i++){
            points.set(i,points.get(i).mirror());
        }
        return new BezierLine(points.get(0),points.get(1));
    }
    public BezierLine correctBezierLine(BezierLine bezierLine){
        return blueSide()? mirrorBezierLine(bezierLine):bezierLine;
    }
    public BezierCurve correctBezierCurve(BezierCurve bezierCurve){
        return blueSide()? mirrorBezierCurve(bezierCurve):bezierCurve;
    }
    public Pose correctPose(Pose pose){
        if(blueSide()){
            return pose.mirror();
        }
        else return pose;
    }

//    public void modPoses(){
//        ArrayList<Pose> ret = new ArrayList<>();
//        for(Pose pose:poses){
//            ret.add(pose.mirror());
//        }
//        poses = ret;
//    }
//    public void modPaths(Path... paths){
//        for(int i =0;i<paths.length;i++){
//            paths[i].;
//        }
//    }
    public void setLinearHeadingInterpolation(Path path,double heading1,double heading2){
        if(blueSide()){
            path.setLinearHeadingInterpolation(-heading1, -heading2);
        }else{
            path.setLinearHeadingInterpolation(heading1, heading2);
        }
//        return path;
    }
//    public static Curve mirrorCurve(Curve curve){
//        return curve.getReversed()
//    }
//    public static Path mirrorPath(Path path){
//
//    }
//    public static void mirrorPathRotation(){
//        for (int i = 0;i<paths.size();i++)
//        {
//            paths.set(i,mirrorPathRotation(paths.get(i)));
//        }
//    }
//
    public boolean blueSide(){
        return Objects.equals(selections.get("color"), "blue");
    }
//    public void start(){
//        if(blueSide()){
//            modPoses();
//            mirrorPathRotation();
//        };
//    }
    public void initSteps(Runnable... steps)
    {
        this.steps = steps;
        times = new double[steps.length];
    }
    public void setCurrentStep(int step)
    {
        times[currentStep] = pathTimer.getElapsedTime();
        currentStep = step;
        pathTimer.resetTimer();
    }
    public void nextStep(){
        setCurrentStep(currentStep + 1);
    }
    public void  updateSteps(){
        if(currentStep<steps.length&&currentStep>=0){steps[currentStep].run();}
    }
    @Override
    public void loop(){
        updateSteps();
    }
}
