package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TeleOp(name = "loggerTest")
public class loggerTest extends OpMode {
    private static final Logger log = LoggerFactory.getLogger(loggerTest.class);
    private logger logger;
    int i = 0;
    String error = "none";

    @Override
    public void start() {
        logger = new logger();
        logger.connect();
    }
    @Override
    public void init(){

    }
    @Override
    public void loop() {
        i++;
        error = logger.c.latestError;
        logger.send("this is a message" + i);
        telemetry.addData("error: ", error);
        telemetry.update();
    }
    @Override
    public void stop(){
        logger.close();
    }
}