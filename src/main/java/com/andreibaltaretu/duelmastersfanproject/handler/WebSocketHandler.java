package com.andreibaltaretu.duelmastersfanproject.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketSession>> gameSessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String receivedMessage = message.getPayload();

        // Assuming the message format is "gameId:message"
        String[] parts = receivedMessage.split(":", 2);
        if (parts.length < 2) {
            session.sendMessage(new TextMessage("Invalid message format. Use 'gameId:message'"));
            return;
        }

        String gameId = parts[0];
        String gameMessage = parts[1];

        // Broadcast the message to all sessions in the same game
        broadcastMessage(gameId, session, gameMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Optionally handle new connections
        System.out.println("New connection established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Remove the session from all games
        gameSessions.forEach((gameId, sessions) -> sessions.remove(session));
        System.out.println("Connection closed: " + session.getId());
    }

    private void broadcastMessage(String gameId, WebSocketSession sender, String message) throws IOException {
        gameSessions.putIfAbsent(gameId, new CopyOnWriteArraySet<>());
        CopyOnWriteArraySet<WebSocketSession> sessions = gameSessions.get(gameId);
        sessions.add(sender); // Ensure the sender is registered in the game

        for (WebSocketSession session : sessions) {
            if (session.isOpen() && !session.getId().equals(sender.getId())) {
                session.sendMessage(new TextMessage("Player: " + message));
            }
        }
    }
}

