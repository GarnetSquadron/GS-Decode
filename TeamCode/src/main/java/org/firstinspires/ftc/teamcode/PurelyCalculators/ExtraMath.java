package org.firstinspires.ftc.teamcode.PurelyCalculators;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Vector2d;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.PurelyCalculators.enums.AngleUnitV2;

import java.util.Arrays;

public class ExtraMath
{


    /**
     * Tau is superior to pi. Fight me
     */
    public static double Tau = Math.PI * 2;

    //region vectors
    public static Vector2d getVecFromPolarCoords(double r,double theta){
        return new Vector2d(r*Math.cos(theta),r*Math.sin(theta));
    }
    public static Vector2d project(Vector2d projectee, Vector2d directionVector){
        return directionVector.times(projectee.dot(directionVector)).div(directionVector.dot(directionVector));
    }
    public static Vector2d normalize(Vector2d vec){
        return vec.div(vec.norm());
    }
    //endregion

    public static double min(double...vals){
        return Arrays.stream(vals).min().getAsDouble();
    }
    public static double max(double...vals){
        return Arrays.stream(vals).max().getAsDouble();
    }

    /**
     * if val is below min, it returns min. if val is above max, it returns max. otherwise, it just
     * returns val.
     *
     * @param val
     * @param max
     * @param min
     * @return
     */
    public static double Clamp(double val, double max, double min)
    {
        return Math.min(Math.max(val, min), max);
    }

    public static boolean withinRange(double val, double max, double min)
    {
        return val <= max && val >= min;
    }

    public static boolean ApproximatelyEqualTo(double val, double expectedVal, double tolerance)
    {
        return withinRange(val, expectedVal + tolerance, expectedVal - tolerance);
    }
    /**
     * applies the quadratic formula to solve ax^2+bx+c=0
     * @return a double array, with the 0th element being the smaller solution and the 1st element being the larger solution
     */
    public static double[] quadraticFormula(double a, double b, double c){
        double discriminant = b*b-4*a*c;
        if(discriminant>=0){
            double sqrtDiscriminant = Math.sqrt(discriminant);
            return new double[]{
                    (-b - sqrtDiscriminant) / (2 * a),
                    (-b + sqrtDiscriminant) / (2 * a)
            };
        }
        else return new double[]{};//easy way to check real roots- if array.length==0
    }
    /**
     * check out <a href="https://en.wikipedia.org/wiki/Cubic_equation#General_cubic_formula">the cubic formula</a>
     */
    public static Complex[] solveCubic(double a,double b,double c,double d){
        double D0 = b*b-3*a*c;
        double D1 = 2*b*b*b-9*a*b*c+27*a*a*d;
        Complex sqrt = posSqrt(D1*D1-4*D0*D0*D0).neg();

        Complex G = sqrt.neg().add(D1).multiply(0.5);

        Complex[] C = getRoots(G,3);

        Complex[] roots = new Complex[3];

        Arrays.setAll(roots,i->add(C[i],C[i].reciprocal().multiply(D0).add(b)).multiply(-1/(3*a)));

        return roots;
    }

    /**
     * returns input with minimum magnitude
     *
     * @return
     */
    public static double minMag(double num1, double num2)
    {
        if (Math.abs(num1) < Math.abs(num2)) {
            return num1;
        } else return num2;
    }

    public static double maxMag(double num1, double num2)
    {
        if (Math.abs(num1) > Math.abs(num2)) {
            return num1;
        } else return num2;
    }

    public static double convertFromRatio(double min, double max, double ratio)
    {
        return min + ratio * (max - min);
    }

    public static double convertToRatio(double min, double max, double val)
    {
        return (val - min) / (max - min);
    }

    public static double getFullCircle(AngleUnitV2 unit)
    {
        if (unit == AngleUnitV2.DEGREES) {
            return 360;
        }
        if (unit == AngleUnitV2.RADIANS) {
            return Tau;
        }
        if (unit == AngleUnitV2.REVOLUTIONS) {
            return 1;
        } else {
            return -1;//this is basically impossible
        }
    }

    public static double ConvertAngleUnit(double input, AngleUnitV2 inputUnit, AngleUnitV2 outputUnit)
    {
        return input * getFullCircle(outputUnit) / getFullCircle(inputUnit);
    }

    public static class integration
    {
        /**
         * https://en.wikipedia.org/wiki/Simpson%27s_rule
         *
         * @param i            initial value
         * @param intermediate value exactly inbetween i and f
         * @param f            final value
         * @return
         */
        public static double SimpsonsRule(double deltaT, double i, double intermediate, double f)
        {
            return deltaT * (
                    i + 4 * intermediate + f
            ) / 6;
        }

        public static double trapazoid(double deltaT, double i, double f)
        {
            return deltaT * (i + f) / 2;
        }

        public static double trapazoid(ValueAtTimeStamp val1, ValueAtTimeStamp val2)
        {
            return trapazoid(val2.getTimeStamp() - val1.getTimeStamp(), val1.getValue(), val2.getValue());
        }
    }

    public static double getAverageTimeDerivative(ValueAtTimeStamp val1, ValueAtTimeStamp val2)
    {
        return (val1.getValue() - val2.getValue()) / (val1.getTimeStamp() - val2.getTimeStamp());
    }

    /**
     * WTF WHY IS (-1)%10=-1?!?!? IT SHOULD BE 9!!!! OTHERWISE THE PROPERTY n%m=(n+m)%m IS NOT PRESERVED
     * @param a
     * @param b
     * @return
     */
    public static double theRealMod(double a,double b){
        double ret = a%b;
        if(ret<0){
            ret+=b;
        }
        return ret;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Atan2">atan2</a> is a function that takes the x and y
     * and gives you the angle of the vector. You would think you can just return atan(y/x), however
     * this only works for positive x, because (-x)/(-y)=x/y but the angle for (-x,-y) is PI plus
     * the angle of (x,y).
     * @param x
     * @param y
     * @return
     */
    public static double atan2(double x, double y){
        double atan = Math.atan(y/x);
        if(x>0){
            return atan;
        }else if(x==0){
            return Math.signum(y)*Math.PI;
        }else if(y<0){
            return atan-Math.PI;
        }else{
            return atan+Math.PI;
        }
    }


    //=================================Complex Numbers=================
    public static class Complex
    {
        //this class stores a complex number a+bi
        public double a,b;
        Complex(double a, double b){
            this.a = a;
            this.b = b;
        }
        public boolean isPurelyReal(){
            return closeTo0(b);
        }
        public Complex multiply(double m){
            return new Complex(a*m,b*m);
        }
        public Complex add(double m){
            return new Complex(a+m,b);
        }
        public double getMagnitude(){
            return Math.hypot(a,b);
        }
        public double getAngle(){
            return Math.atan(b/a)+(a<0?Math.PI:0);
        }
        public Complex conjugate(){
            return new Complex(a,-b);
        }
        public Complex neg(){
            return new Complex(-a,-b);
        }
        public Complex reciprocal(){
            return conjugate().multiply(1/(a*a+b*b));
        }
        @NonNull
        public String toString(){
            return a+"+"+b+"i";
        }
    }
    public static Complex add(Complex z, Complex w){
        return new Complex(z.a+w.a,z.b+w.b);
    }
    public static Complex multiply(Complex z, Complex w){
        return new Complex(z.a*w.a-z.b*w.b,z.b*w.a+z.a*w.b);
    }

    /**
     * @param z numerator
     * @param w denominator
     * @return z/w
     */
    public static Complex divide(Complex z, Complex w){
        return multiply(z,w.reciprocal());
    }

    /**
     * takes the positive square root of any number, returning a positive multiple of i if a is negative
     * @param a
     * @return
     */
    public static Complex posSqrt(double a){
        if(a<0){
            return new Complex(0,Math.sqrt(-a));
        }else{
            return new Complex(Math.sqrt(a),0);
        }
    }

    /**
     * gets the
     * @param z
     * @param degree
     * @return
     */
    public static Complex[] getRoots(Complex z,int degree){
        double r = z.getMagnitude();
        double theta = z.getAngle();
        Complex[] roots = new Complex[degree];

        double retR = Math.pow(r, (double) 1 / degree);
        Arrays.setAll(roots,i->getComplexFromPolar(retR, (theta+2*Math.PI*i) / degree));
        return roots;
    }
    public static Complex getComplexFromPolar(double r, double theta){
        return new Complex(r*Math.cos(theta),r*Math.sin(theta));
    }
    public static boolean closeTo0(double num,double margin){
        return Math.abs(num)<margin;
    }
    public static boolean closeTo0(double num){
        return closeTo0(num,1e-10);
    }

    /**
     * Perform linear interpolation between two values.
     *
     * @param startValue The value to start at.
     * @param endValue The value to end at.
     * @param t How far between the two values to interpolate. This is clamped to [0, 1].
     * @return The interpolated value.
     */
    public static double interpolate(double startValue, double endValue, double t) {
        return startValue + (endValue - startValue) * ExtraMath.Clamp(t, 1, 0);
    }

    /**
     * Return where within interpolation range [0, 1] q is between startValue and endValue.
     *
     * @param startValue Lower part of interpolation range.
     * @param endValue Upper part of interpolation range.
     * @param q Query.
     * @return Interpolant in range [0, 1].
     */
    public static double inverseInterpolate(double startValue, double endValue, double q) {
        double totalRange = endValue - startValue;
        if (totalRange <= 0) {
            return 0.0;
        }
        double queryToStart = q - startValue;
        if (queryToStart <= 0) {
            return 0.0;
        }
        return queryToStart / totalRange;
    }
    public static Pose getPoseFromVector(Vector vector){
        return new Pose(vector.getXComponent(),vector.getYComponent());
    }
}
