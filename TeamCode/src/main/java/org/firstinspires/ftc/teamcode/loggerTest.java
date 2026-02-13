package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class loggerTest extends OpMode {
    private static final Logger log = LoggerFactory.getLogger(loggerTest.class);
    private logger logger;
    int i = 0;
    String error = "none";

    @Override
    public void init() {
        logger = new logger();
        logger.connect();
    }

    @Override
    public void loop() {
        i++;
        error = logger.c.latestError;
        logger.send("this is a message" + i);
        telemetry.addData("error: ", error);
        telemetry.update();
    }
}