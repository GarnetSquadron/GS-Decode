package org.firstinspires.ftc.teamcode;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


import java.io.File;
import java.util.HashMap;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class JsonPayload {
    //command. for sending specialized commands such as "Over" or similar
    private String Command;
    //a hashmap to contain values not specified, eg game specific functions such as flywheel
    private  HashMap<String, Object> values = new HashMap<>();
    //defining variables for the position and rotation of the robot
    private Double[] pos = new Double[]{0.0,0.0};
    private Double rot = 0.0;
    //the wheel speeds
    private double[] wheelSpeeds = new double[]{
            //1 front left,   front right 2
            0,    0,

            0,    0
            //3 back left,   back right 4
    };
    private double timeStamp = 0;
    public void addDataPoint(Object data,String label){
        if(values.containsKey(label)){
            values.replace(label, data);
        }else{values.put(label,data);}
    }
    public void updatePos(double x, double y, double rot){
        pos[1] = x;
        pos[2] = y;
        this.rot = rot;
    }
    public void setTimeStamp(double timeStamp){
        this.timeStamp = timeStamp;
    }
    public void updateWheelRpm(double fl, double fr, double br, double bl){
        wheelSpeeds[1] = fl;
        wheelSpeeds[2] = fr;
        wheelSpeeds[3] = bl;
        wheelSpeeds[4] = br;
    }
}
