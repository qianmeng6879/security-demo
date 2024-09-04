package top.mxzero.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Peng
 * @since 2024/9/4
 */
@RestController
@EnableWebSocket
@SpringBootApplication
public class StartWebSocketSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartWebSocketSecurityApplication.class, args);
    }


    @RequestMapping("ping")
    public String pingAPi(){
        return "ok";
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
