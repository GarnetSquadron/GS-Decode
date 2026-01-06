package org.firstinspires.ftc.teamcode.PurelyCalculators;

import org.firstinspires.ftc.teamcode.Dimensions.FieldDimensions;
import org.firstinspires.ftc.teamcode.Dimensions.RobotDimensions;
import org.firstinspires.ftc.teamcode.HardwareControls.Launcher;
import org.firstinspires.ftc.teamcode.PurelyCalculators.ExtraMath.Complex;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TrajectoryMath
{


    /**
     * quadratic curve approximation parameters for
     * A+B*theta+Cy+D*theta*y+Ey^2
     */
    static double A = -58.97183942365856,B=-0.03645626379853637,C=399.981124985381,D=-2.032211274012549,E=0.004655773262312472;
    public static double getM(double flywheelVel){
        return A+B*flywheelVel+E*flywheelVel*flywheelVel;
    }
    public static double getN(double flywheelVel){
        return C+D*flywheelVel;
    }
    //https://www.desmos.com/calculator/sxcfveqbri
    public static double a(double M){
        return M*M*g*g/4;
    }
    public static double b(double M,double N,double X,double Y){
        return g*N*M*X+g*M*M*Y-g*g*X*X/4;
    }
    public static double c(double M,double N,double X,double Y){
        return N*N*X*X+2*N*M*Y*X+M*M*Y*Y-Y*X*X*g;
    }
    public static double d(double X,double Y){
        return -Y*Y*X*X-X*X*X*X;
    }
    /**
     * needs to be somewhere between minGoalHeight and maxGoalHeight, so why not the average
     */
    final static public double targetHeight = (FieldDimensions.maxGoalHeight+FieldDimensions.minGoalHeight)/2.0-RobotDimensions.Hood.approxBallExitHeight;//The height difference is the height of the goal-the height of the ball as it exits
    public final static double g = 386.09;//9.8 m/s^2=386.09in/s

    //static final double a = g*g/4;

    /**
     * calculate both the angles (in radians of course)
     * @param vel the velocity that the ball leaves the shooter at
     * @param distance the distance (flat on the floor) from the robot to the goal
     * @return a double array, with the 0th element being the smaller angle and the 1st element being the larger. if there are no solutions, it returns an empty array
     */
    public static double[] getAngles(double vel,double distance)
    {

        double M = getM(vel);
        double N = getN(vel);
        double a = a(M);
        double b = b(M, N, distance, targetHeight);
        double c = c(M, N, distance, targetHeight);
        double d = d(distance, targetHeight);
        Complex[] solutions = ExtraMath.solveCubic(a, b, c, d);
        // Filter using Java Streams
        Complex[] realSolutions = Arrays.stream(solutions)
                .filter(Complex::isPurelyReal)
                .toArray(Complex[]::new);
        double[] angles = new double[realSolutions.length];
        for (int i = 0; i < realSolutions.length; i++)
        {
            double t = Math.sqrt(realSolutions[i].a);
            double slope = (g * t / 2 + targetHeight) / distance;
            angles[i] = Math.atan(slope);
        }
        return angles;

    }
    /**
     * chooses the optimum angle (rn if theres a choice it just chooses the higher one by default because that one is more fun)
     * @param vel
     * @param distance
     * @return one of the angles that works, or -1 if it doesn't exist at this speed.
     */
    public static double getOptimumAngle(double vel,double distance){
        double[] angles = getAngles(vel,distance);
        for(int i=0;i<angles.length;i++){

        }
        return angles[0];
        //else return angles[1];//default to the larger angle because it is more fun
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

    /**
     * gets the velocity given an angle
     * @param dist distance between robot and goal in inches
     * @param launchAngle angle of the initial velocity vector that the ball comes out of the launcher with with respect to the horizontal
     * @return
     */
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
