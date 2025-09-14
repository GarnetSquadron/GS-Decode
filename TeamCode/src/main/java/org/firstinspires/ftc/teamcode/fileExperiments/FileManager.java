package org.firstinspires.ftc.teamcode.fileExperiments;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class FileManager
{
    public void newFile(){
        //LogFiles.registerRoutes();
        File file = new File(AppUtil.ROOT_FOLDER.toString() + "/testFile");

    }
}
