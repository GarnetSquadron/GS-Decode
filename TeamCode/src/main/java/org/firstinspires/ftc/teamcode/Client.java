package org.firstinspires.ftc.teamcode;


import java.io.*;
import java.net.*;

public class Client {

    // Initialize socket and input/output streams
    private Socket s = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    public String latestError = "";
    private String addr;
    private int port;
    private String m = " ";


    public Client(String addr, int port)
    {
        this.addr = addr;
        this.port = port;
    }
    // Establish a connection
    public boolean attemptConnection() {
        try {
            s = new Socket(addr, port);

            // Takes input from terminal
            in = new DataInputStream(System.in);

            // Sends output to the socket
            out = new DataOutputStream(s.getOutputStream());
            return true;
        }
        catch (UnknownHostException u) {
            latestError = u.getMessage();
            return false;
        }
        catch (IOException i) {
            latestError = i.getMessage();
            return false;
        }
    }
        public void sendStringViaSocket(String sm) {
            if(!sm.isBlank()){
                m = sm;
            }
            // Keep reading until "Over" is input
            if (!m.equals("Over")) {
                try {
                    out.writeUTF(m);
                } catch (IOException i) {
                    latestError = i.getMessage();
                }
            }
            else{ // Close the connection
                try {
                    in.close();
                    out.close();
                    s.close();
                } catch (IOException i) {
                    latestError = i.getMessage();
                }
            }
        }

    public static void main(String[] args) {

    }
}