package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.slf4j.LoggerFactory;

@TeleOp(name = "loggerTest")
public class loggerTest extends OpMode {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(loggerTest.class);
    private Logger logger;
    int i = 0;
    String error = "none";

    @Override
    public void start() {
        logger = Logger.getInstance();
    }
    @Override
    public void init(){

    }
    @Override
    public void loop() {
        i++;
        if(logger.error != null){
            telemetry.addData("error", logger.error);
        }
        telemetry.addData("message",logger.getMessage());
        logger.addData(i,"value");
        logger.updatePayload();
        logger.sendLog();

    }
    @Override
    public void stop(){
        logger.close();
    }
}