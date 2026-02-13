package org.firstinspires.ftc.teamcode;

public class logger {
    private int t = 0;
    public Client c = new Client("192.168.43.164", 5000);;

    public logger() {;

    }

    public void send(String m){
        c.sendStringViaSocket(m);
    }

    public void close(){
        c.sendStringViaSocket("Over");
    }

    public void connect() {
        t++;
        if(!c.attemptConnection()&t>1000){connect();t=0;}
    }

    public void sendMessage(String message) {

    }
}
