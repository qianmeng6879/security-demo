package top.mxzero.ws.websocket;

import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Peng
 * @since 2024/9/4
 */
@Component
@ServerEndpoint("/echo")
public class EchoHandle {

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText("【ECHO】" + message);
    }
}
