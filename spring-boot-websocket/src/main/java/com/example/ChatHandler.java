package com.example;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private ConcurrentHashMap<String, Set<WebSocketSession>> roomSessionPool = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String roomName = session.getUri().getQuery();

        roomSessionPool.compute(roomName, (key, sessions) -> {

            if (sessions == null) {
                sessions = new CopyOnWriteArraySet<>();
            }
            sessions.add(session);

            return sessions;
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String roomName = session.getUri().getQuery();

        for (WebSocketSession roomSession : roomSessionPool.get(roomName)) {
            roomSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String roomName = session.getUri().getQuery();

        roomSessionPool.compute(roomName, (key, sessions) -> {

            sessions.remove(session);
            if (sessions.isEmpty()) {
                // 1件もない場合はMapからクリア
                sessions = null;
            }

            return sessions;
        });
    }
}
