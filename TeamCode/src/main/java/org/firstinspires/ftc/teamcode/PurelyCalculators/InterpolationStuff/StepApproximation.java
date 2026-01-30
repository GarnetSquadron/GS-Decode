package org.firstinspires.ftc.teamcode.PurelyCalculators.InterpolationStuff;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OpModes.SectTelemetryAdder;

import java.util.TreeMap;

public class StepApproximation
{
    public TreeMap<Double,Double> m_map;
    public double stepSize;
    SectTelemetryAdder telemetry;
    public StepApproximation(double stepSize,String name){
        telemetry = new SectTelemetryAdder("step approx"+name);
        this.stepSize = stepSize;
        m_map = new TreeMap<>();
    }
    public double getKeyFromValue(double key){
        return Math.round(key/stepSize)*stepSize;
    }
    public void put(double key,double value){
        m_map.put(getKeyFromValue(key),value);
    }
    public void increment(double key,double increment){
        if(increment!=0){
            put(key, get(key) + increment);
        }
    }
    public double get(double key){
        Double val = m_map.get(key);
        if (val == null) {
            Double floorKey = m_map.floorKey(key);
            Double ceilingKey = m_map.ceilingKey(key);
            telemetry.addData("floorKey", floorKey);
            telemetry.addData("ceil key", ceilingKey);


            if (ceilingKey == null && floorKey == null)
            {
                return 0;
            }
            if (ceilingKey == null)
            {
                return m_map.get(floorKey);
            }
            if (floorKey == null)
            {
                return m_map.get(ceilingKey);
            }
            Double floor = m_map.get(floorKey);
            Double ceiling = m_map.get(ceilingKey);
            telemetry.addData("ceiling", ceiling);
            telemetry.addData("floor", floor);
            double value = key - floorKey > ceilingKey - key ? ceiling : floor;
            telemetry.addData("returning",value);


            return value;
        }else {
            return val;
        }
    }
}
