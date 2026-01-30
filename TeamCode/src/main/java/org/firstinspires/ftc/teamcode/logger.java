package org.firstinspires.ftc.teamcode;

public class logger {

    public NetworkConnection networkConnection;

    public logger() {
        networkConnection = new NetworkConnection();
    }

    public void connect() {
        networkConnection.connect();
    }

    public void sendMessage(String message) {
//        networkConnection.send(message);
    }
}
