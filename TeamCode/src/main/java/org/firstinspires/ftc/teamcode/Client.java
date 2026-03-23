package org.firstinspires.ftc.teamcode;

import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;

public class Client {

    //temp
    String c = "";
    // Initialize socket and input/output streams
    private Socket s = null;
    private double lastPush;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private boolean connected = false;
    //initialize the Json payload and Json tools
    ObjectMapper mapper = new ObjectMapper();
    public JsonPayload load = new JsonPayload();
    int logsSent = 0;
    private String m = "";
    public String latestInfo ="none";

    void connect(String addr, int port) {
        latestInfo = ("attempting to connect");
        // Establish a connection
        try {
            s = new Socket(addr, port);
            latestInfo = ("Connected");
            connected = true;

            // Takes input from terminal
            in = new DataInputStream(System.in);

            // Sends output to the socket
            out = new DataOutputStream(s.getOutputStream());
        } catch (UnknownHostException u) {
            latestInfo = ("unable to connect" + u);
            return;
        } catch (IOException i) {
            latestInfo = (i.getMessage());
            return;
        }
    }

    public Client(String addr, int port) {
        connect(addr, port);
        int timer = 0;
        while (!connected) {
            timer++;
            if (timer > 100) {
                connect(addr, port);
                timer = 0;
            }

        }
        // String to read message from input
        // Keep reading until "Over" is input



    }
    public void close(){
        // Close the connection
        try {
            latestInfo = "disconnecting";
            out.writeUTF("Over");
            in.close();
            out.close();
            s.close();
        } catch (IOException i) {
            latestInfo = (i.getMessage());
        }
    }
    public void sendLog(){
        if ((System.currentTimeMillis() -lastPush)>5) {
            try {
                load.setTimeStamp((System.nanoTime() - lastPush) / 1000000);
                m = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(load);
                latestInfo = (m);
                out.writeUTF(m);
            } catch (IOException e) {
                latestInfo = ("error " + e);
            }
            lastPush = System.currentTimeMillis();
        }
    }
}
