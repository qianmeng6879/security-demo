package top.mxzero.security.websockets;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/31
 */
@Slf4j
@Component
@ServerEndpoint("/ws/echo")
public class EchoWebSocket {
    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        SESSION_MAP.put(session.getId(), session);
        log.info("session:{} connect", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        SESSION_MAP.remove(session.getId());
        log.info("session:{} close", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable exception) {
//        sessionMap.remove(session.getId());
        log.error("session:{} error:{}", session.getId(), exception.getMessage());
    }

    @OnMessage
    public void onMessage(Session session, String text) throws IOException {
//        session.getBasicRemote().sendText("【ECHO】" + text);
        this.broadcast("【ECHO】" + text);
    }


    public void broadcast(String message) {
        SESSION_MAP.forEach((sessionId, session) -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
