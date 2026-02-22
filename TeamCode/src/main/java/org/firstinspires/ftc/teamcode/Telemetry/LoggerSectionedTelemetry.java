package org.firstinspires.ftc.teamcode.Telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.logger;

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
