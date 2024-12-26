package org.firstinspires.ftc.teamcode;

import java.util.function.BooleanSupplier;

public class BooleanChangeDetector {
    BooleanSupplier bool;
    boolean prevVal;
    boolean State = false;
    public BooleanChangeDetector(BooleanSupplier bool){
        this.bool = bool;
    }
    public void update(){
        State = prevVal != bool.getAsBoolean();
    }
    public boolean getState(){
        return State;
    }
}
