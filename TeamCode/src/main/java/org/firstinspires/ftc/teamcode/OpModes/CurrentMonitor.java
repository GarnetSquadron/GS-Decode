package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.HardwareControls.Bot;
import org.firstinspires.ftc.teamcode.Telemetry.SectTelemetryAdder;

public class CurrentMonitor
{
    VoltageSensor voltageSensor;
    double totalMaxCurrent = 0,lfMax = 0,rfMax = 0, lbMax = 0,rbMax = 0,intakeMax = 0, launcherMax1 = 0,launcherMax2 = 0;
    Bot bot;
    public DcMotorEx lf,rf,lb,rb,intakeMotor;
    double lfCurrent,rfCurrent,lbCurrent,rbCurrent,intakeCurrent,launcher1Current,launcher2Current,totalCurrent;
    SectTelemetryAdder telemetry;

    public CurrentMonitor(HardwareMap hardwareMap,Bot bot){
        this.bot = bot;
        lf = hardwareMap.get(DcMotorEx.class,"lf");
        rf = hardwareMap.get(DcMotorEx.class,"rf");
        lb = hardwareMap.get(DcMotorEx.class,"lb");
        rb = hardwareMap.get(DcMotorEx.class,"rb");
        intakeMotor = hardwareMap.get(DcMotorEx.class,"intakeMotor");
        telemetry = new SectTelemetryAdder("CURRENTS=IN=MILLIAMPS");
    }
    public void updateData(){
        lfCurrent = lf.getCurrent(CurrentUnit.MILLIAMPS);
        rfCurrent = rf.getCurrent(CurrentUnit.MILLIAMPS);
        lbCurrent = lb.getCurrent(CurrentUnit.MILLIAMPS);
        rbCurrent = rb.getCurrent(CurrentUnit.MILLIAMPS);
        intakeCurrent = intakeMotor.getCurrent(CurrentUnit.MILLIAMPS);
        launcher1Current = bot.launcher.motor1.getCurrentMilliamps();
        launcher2Current = bot.launcher.motor2.getCurrentMilliamps();
        totalCurrent = lfCurrent+rfCurrent+lbCurrent+rbCurrent+intakeCurrent+launcher1Current+launcher2Current;
        totalMaxCurrent = Math.max(totalMaxCurrent,totalCurrent);
        lfMax = Math.max(lfCurrent,lfMax);
        rfMax = Math.max(rfCurrent,rfMax);
        lbMax = Math.max(lbCurrent,lbMax);
        rbMax = Math.max(rbCurrent,rbMax);
        intakeMax = Math.max(intakeCurrent,intakeMax);
        launcherMax1 = Math.max(launcher1Current, launcherMax1);
        launcherMax2 = Math.max(launcher2Current, launcherMax2);
    }
    public void addTelemetry(){
        telemetry.addData("lf", lfCurrent);
        telemetry.addData("rf", rfCurrent);
        telemetry.addData("lb", lbCurrent);
        telemetry.addData("rb", rbCurrent);
        telemetry.addData("intake", intakeCurrent);
        telemetry.addData("launcherMotor1", launcher1Current);
        telemetry.addData("launcherMotor2", launcher2Current);
        telemetry.addData("total current",totalCurrent);
        telemetry.addData("max total current reached", totalMaxCurrent);
        telemetry.addData("max lf",lfMax);
        telemetry.addData("max rf",rfMax);
        telemetry.addData("max lb",lbMax);
        telemetry.addData("max rb",rbMax);
        telemetry.addData("max intake",intakeMax);
        telemetry.addData("max launcher motor 1",launcherMax1);
        telemetry.addData("max launcher motor 2",launcherMax2);
    }
}
