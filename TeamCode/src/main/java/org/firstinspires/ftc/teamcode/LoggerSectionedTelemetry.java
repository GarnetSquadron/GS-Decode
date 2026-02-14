package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerSectionedTelemetry extends SectionedTelemetry
{
    private logger logger;
    public LoggerSectionedTelemetry(Telemetry telemetry)
    {
        super(telemetry);
        logger = new logger();
        logger.connect();
    }
    @Override
    public void display(){
        logger.send(display);
        super.display();
    }
}
