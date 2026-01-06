package org.firstinspires.ftc.teamcode.HardwareControls.encoders;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.ArrayList;
import java.util.function.DoubleSupplier;

/**
 * this extension of encoder adds the method getAverageVelocity(), which takes the average of the previous couple velocities.
 * This reduces noise and allows pid to work better.
 * This should be depricated I think, I need to extend/modify pidcon, not encoder
 */
public class SmoothVelocityEncoder extends Encoder
{
    ArrayList<Double> velocities = new ArrayList<>();
    /**
     * the number of previous velocities we keep track of to average
     */
    double sampleSize;
    //region constructors
    public SmoothVelocityEncoder(DoubleSupplier positionSupplier,double sampleSize)
    {
        super(positionSupplier);
        this.sampleSize = sampleSize;
    }

    public SmoothVelocityEncoder(SmoothVelocityEncoder encoder)
    {
        super(encoder);
        this.sampleSize = encoder.sampleSize;
    }
    public SmoothVelocityEncoder(Encoder encoder, double sampleSize){
        super(encoder);
        this.sampleSize = sampleSize;
    }

    public SmoothVelocityEncoder(DoubleSupplier positionSupplier, DoubleSupplier velocitySupplier, double sampleSize)
    {
        super(positionSupplier, velocitySupplier);
        this.sampleSize = sampleSize;
    }

    public SmoothVelocityEncoder(Motor motor,double sampleSize)
    {
        super(motor);
        this.sampleSize = sampleSize;
    }
    //endregion
    public void updateVelocityList(){
        velocities.add(getVelocity());
        if(velocities.size()>sampleSize){
            velocities.remove(0);
        }
    }
    @Override
    public void updateVelocity(){
        super.updateVelocity();
        updateVelocityList();
    }
    public double getAverageVelocity()
    {
        if(velocities.size()!=0){
            double sum = 0;
            for (double velocity : velocities)
            {
                sum += velocity;
            }
            return sum / velocities.size();
        }
        else return 0;
    }
}
