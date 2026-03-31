package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;

public class Logger {
    //singleton
    private Follower follower;
    private static Logger instance;
    private int t = 0;
    public Client c = new Client("192.168.43.164", 5050);
    JsonPayload JLoad = c.load;
    String error = null;

    /**
    * use "get instance" for this class to make sure you don't break smth
     */
    public Logger() {

    }

    public void updatePayload(){
        try{
            if (follower.getPose() != null){
                JLoad.updatePos(follower.getPose().getX(), follower.getPose().getY(), follower.getPose().getHeading());
            }
            JLoad.setTimeStamp(System.currentTimeMillis());
            c.load = JLoad;
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
    public void sendLog(){
        c.sendLog();
    }

    /**
     * *
     * @param data
     * @param title
     * data first, title after
     */
    public void addData(Object data, String title){
        JLoad.addDataPoint(data,title);
    }

    public void close(){
        c.close();
    }

    public static Logger getInstance(){
        if (instance == null){
            instance = new Logger();
        }
        return instance;
    }
    public String getMessage(){
        return c.latestInfo;
    }
}
