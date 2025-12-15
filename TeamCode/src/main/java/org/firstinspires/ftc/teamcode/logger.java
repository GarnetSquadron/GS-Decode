package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class logger {
    private static final Logger log = LoggerFactory.getLogger(logger.class);
    public String fielName = null;
    String dirPath = Environment.getExternalStorageDirectory().getPath();
    String filePath = dirPath+"/"+fielName+".txt";

    File logFile = new File(dirPath);

    public logger logger(String fielName) throws IOException {
        this.fielName = fielName;
        logFile.createNewFile();
        return this;
    }

}
