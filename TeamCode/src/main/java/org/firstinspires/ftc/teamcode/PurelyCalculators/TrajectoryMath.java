package org.firstinspires.ftc.teamcode.PurelyCalculators;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;

import java.util.Arrays;

public class TrajectoryMath
{
    /**
     * needs to be somewhere between minGoalHeight and maxGoalHeight, so why not the average
     */
    final static public double targetHeight = (FieldDimensions.maxGoalHeight+FieldDimensions.minGoalHeight)/2.0-RobotDimensions.Hood.approxBallExitHeight;//The height difference is the height of the goal-the height of the ball as it exits
    public final static double g = 386.09;//9.8 m/s^2=386.09in/s

    static double a = 386.09*386.09/4;

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
        return Math.atan((g/2*t*t+targetHeight)/(distance));
    }

    public static double getVelSquared(double dist, double launchAngle) {
        double c = dist*dist+targetHeight*targetHeight;
        double tSquare = 2 * (dist*Math.tan(launchAngle)- targetHeight)/g;
        return a*tSquare+c/tSquare+g*targetHeight;
    };
    public static double getMinVelSquared(double x,double y){
        return g*(y+Math.sqrt(y*y+x*x));
    }
    public static double[] getVelBoundsFromVelSquaredBounds(double minAngleVelSquared, double maxAngleVelSquared, double distance){
        //if(maxAngleVelSquared<0){ maxAngleVelSquared = bot.launcher.getMaxPossibleExitVel();}
        if(minAngleVelSquared<0){ minAngleVelSquared = getMinVelSquared(distance,targetHeight);}
        //make sure max isn't too fast
        double maxAngleVel = (maxAngleVelSquared<0)? Launcher.getMaxPossibleExitVel():Math.min(Math.sqrt(maxAngleVelSquared), Launcher.getMaxPossibleExitVel());
        double minAngleVel = Math.min(Math.sqrt(minAngleVelSquared), Launcher.getMaxPossibleExitVel());
        if(minAngleVel<maxAngleVel){// return the bounds such that the smaller one is the first one
            return new double[]{minAngleVel,maxAngleVel};
        }else{
            return new double[]{maxAngleVel,minAngleVel};
        }
    }
}
