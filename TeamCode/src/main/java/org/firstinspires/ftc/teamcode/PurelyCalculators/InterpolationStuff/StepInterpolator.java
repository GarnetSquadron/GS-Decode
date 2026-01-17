package org.firstinspires.ftc.teamcode.PurelyCalculators.InterpolationStuff;

public class StepInterpolator extends InterpolatingDoubleTreeMap
{
    public double step;
    public StepInterpolator(double step){
        super();
        this.step = step;
    }
    public void put(double key,double value){
        m_map.put(key,Math.round(value/step)*step);
    }

}
