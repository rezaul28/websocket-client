package org.example;

import javax.websocket.*;
import java.net.URI;


@ClientEndpoint
public class WebSocketClient {
    private static final int userId = 10000;
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to WebSocket server");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);

        String replyMessage = "Message received : "+message+" by user " + userId;
        session.getAsyncRemote().sendText(replyMessage);
        System.out.println("Sent reply: " + replyMessage);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Disconnected from WebSocket server. Reason: " + reason.getReasonPhrase());
    }

    public static void main(String[] args) {
        String serverUri = "wss://socket.api.com/dev?school_id="+ userId;       //change the socket link

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            Session session = container.connectToServer(WebSocketClient.class, URI.create(serverUri));
            // Wait for the user to interrupt the program (e.g., press Ctrl+C) to exit
            Thread.sleep(Long.MAX_VALUE);
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
