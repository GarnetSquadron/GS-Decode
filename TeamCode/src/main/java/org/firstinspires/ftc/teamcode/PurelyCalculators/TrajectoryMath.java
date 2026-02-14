package org.firstinspires.ftc.teamcode.PurelyCalculators;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;

import java.util.Arrays;

public class TrajectoryMath
{
    static public double ratio = 0;
    /**
     * needs to be somewhere between minGoalHeight and maxGoalHeight, so why not the average
     */
    static public double getTargetHeight ()
    {
        return FieldDimensions.maxGoalHeight * ratio + FieldDimensions.minGoalHeight * (1 - ratio) - RobotDimensions.Hood.approxBallExitHeight;
    }//The height difference is the height of the goal-the height of the ball as it exits
    public final static double g = 386.09;//9.8 m/s^2=386.09in/s

    static final double a = g*g/4;

    /**
     * calculate both the angles (in radians of course)
     * @param vel the velocity that the ball leaves the shooter at
     * @param distance the distance (flat on the floor) from the robot to the goal
     * @return a double array, with the 0th element being the smaller angle and the 1st element being the larger. if there are no solutions, it returns an empty array
     */
    public static double[] getAngles(double vel,double distance)
    {
        double targetHeight = getTargetHeight();
        double b = g*targetHeight-vel*vel;
        double c = distance*distance+targetHeight*targetHeight;
        double[] tSquared = ExtraMath.quadraticFormula(a,b,c);
        if(tSquared.length==2){
            //not sure if stream is the fastest option but its simpler
            return Arrays.stream(tSquared)//solves a quadratic in terms of t^2
                    .map(Math::sqrt)//gets t from t^2 (we know t is positive because it is the time after launch, not before)
                    .map(t -> getAngleFromTime(t, distance))//gets the angle from the time.
                    .toArray();//turns it back to an arrays
        }
        else return new double[] {};
    }
    /**
     * chooses the optimum angle (rn if theres a choice it just chooses the higher one by default because that one is more fun)
     * @param vel
     * @param distance
     * @return one of the angles that works, or -1 if it doesn't exist at this speed.
     */
    public static double getOptimumAngle(double vel,double distance){
        double[] angles = getAngles(vel,distance);
        if(angles.length==0){
            return -1;// this means that the angle is impossible. In this case because the flywheel speed is too low
        }
        else if(!isInAngleRange(angles[1])){
            if(!isInAngleRange(angles[0])){
                return -1;// angle is impossible because the angle is outside of the
            }
            else return angles[0];//if large angle
        }
        else return angles[1];//default to the larger angle because it is more fun
    }
     static boolean isInAngleRange(double angle){
        return angle<=Math.toRadians(RobotDimensions.Hood.maxAngle)&&angle>=Math.toRadians(RobotDimensions.Hood.minAngle);
     }

    /**
     *
     * @param t the times that are
     * @return
     */
    static double getAngleFromTime(double t,double distance){
        double targetHeight = getTargetHeight();
        return Math.atan((g/2*t*t+targetHeight)/(distance));
    }

    /**
     * gets the velocity given an angle
     * @param dist distance between robot and goal in inches
     * @param launchAngle angle of the initial velocity vector that the ball comes out of the launcher with with respect to the horizontal
     * @return
     */
    public static double getVelSquared(double dist, double launchAngle) {
        double targetHeight = getTargetHeight();
        double c = dist*dist+targetHeight*targetHeight;
        double tSquare = 2 * (dist*Math.tan(launchAngle)- targetHeight)/g;
        return a*tSquare+c/tSquare+g*targetHeight;
    };
    public static double getMinVelSquared(double x,double y){
        return g*(y+Math.sqrt(y*y+x*x))+1;
    }
    public static double[] getVelBoundsFromVelSquaredBounds(double minAngleVelSquared, double maxAngleVelSquared, double distance){
        double targetHeight = getTargetHeight();
        //if(maxAngleVelSquared<0){ maxAngleVelSquared = bot.launcher.getMaxPossibleExitVel();}
        if(minAngleVelSquared<0){ minAngleVelSquared = getMinVelSquared(distance,targetHeight);}
        //make sure max isn't too fast
        double maxAngleVel = (maxAngleVelSquared<0)? Launcher.getMaxPossibleExitVel():Math.sqrt(maxAngleVelSquared);
        double minAngleVel = Math.sqrt(minAngleVelSquared);
        if(minAngleVel<maxAngleVel){// return the bounds such that the smaller one is the first one
            return new double[]{minAngleVel,maxAngleVel};
        }else{
            return new double[]{maxAngleVel,minAngleVel};
        }
    }
}
