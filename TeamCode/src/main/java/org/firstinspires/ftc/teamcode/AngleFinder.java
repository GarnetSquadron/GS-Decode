package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;

import java.util.Arrays;

public class AngleFinder
{
    /**
     * needs to be somewhere between minGoalHeight and maxGoalHeight, so why not the average
     */
    final static double targetHeight = (FieldDimensions.maxGoalHeight+FieldDimensions.minGoalHeight)/2.0;
    final static double g = 9.8;

    /**
     * calculate both the angles (in radians of course)
     * @param vel the velocity that the ball leaves the shooter at
     * @param distance the distance (flat on the floor) from the robot to the goal
     * @return a double array, with the 0th element being the smaller angle and the 1st element being the larger. if there are no solutions, it returns an empty array
     */
    public static double[] getAngles(double vel,double distance)
    {
        double a = g*g/4;
        double b = g*targetHeight-vel*vel;
        double c = distance*distance+targetHeight*targetHeight;
        double[] tSquared = ExtraMath.quadraticFormula(a,b,c);
        if(tSquared.length==2){
            //not sure if stream is the fastest option but its simpler
            return Arrays.stream(tSquared)//solves a quadratic in terms of t^2
                    .map(Math::sqrt)//gets t from t^2 (we know t is positive because it is the time after launch, not before)
                    .map(t -> getAngleFromTime(t, distance))//gets the angle from the time.
                    .toArray();//turns it back to an array
        }
        else return tSquared;
    }
    public static double getOptimumAngle(double vel,double distance){
        double[] angles = getAngles(vel,distance);
        if(angles.length==0){
            return -1;// this means that the angle is impossible.
        }
        else return angles[1];
    }

    /**
     *
     * @param t the times that are
     * @return
     */
    static double getAngleFromTime(double t,double distance){
        return Math.atan((g/2*t*t+targetHeight)/(distance));
    }
}
