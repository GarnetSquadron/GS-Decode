package org.firstinspires.ftc.teamcode.Telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class LoggerSectionedTelemetry extends SectionedTelemetry
{
    private Logger logger;
    public LoggerSectionedTelemetry(Telemetry telemetry)
    {
        super(telemetry);
        logger = new Logger();
//        logger.connect();
    }
    @Override
    public void display(){
//        logger.send(display);
        super.display();
    }
}
