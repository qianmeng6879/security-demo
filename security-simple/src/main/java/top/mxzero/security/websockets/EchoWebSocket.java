package top.mxzero.security.websockets;

import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/31
 */
@Component
@ServerEndpoint("/echo")
public class EchoWebSocket {
    @OnMessage
    public void onMessage(Session session, String text) throws IOException {
        session.getBasicRemote().sendText("【ECHO】" + text);
    }
}
