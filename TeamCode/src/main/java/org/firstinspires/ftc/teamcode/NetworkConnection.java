package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import okhttp3.*;
public class NetworkConnection {
    private WebSocket webSocket;
    public volatile String latestData = "none yet";
    private final OkHttpClient client = new OkHttpClient();

    public void connect(){

        Request request = new Request.Builder().url("ws://192.168.43.1:8080").build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                webSocket.send("connected as client");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                latestData = text;
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
                latestData = "failed to connect";
            }
        });

    }
}
