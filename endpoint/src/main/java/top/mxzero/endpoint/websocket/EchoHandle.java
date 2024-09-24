package top.mxzero.endpoint.websocket;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.mxzero.security.rbac.service.AuthorizeService;

import java.io.IOException;

/**
 * @author Peng
 * @since 2024/9/24
 */
@Slf4j
@Component
@ServerEndpoint("/echo")
public class EchoHandle {


    @Autowired
    private AuthorizeService authorizeService;

    @OnOpen
    public void onOpen(Session session) {
        String name = session.getUserPrincipal().getName();
        log.info("open {}, name {}", session.getId(), name);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText("【ECHO】" + message);
    }

}
