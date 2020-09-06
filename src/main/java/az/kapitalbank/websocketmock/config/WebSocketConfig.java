package az.kapitalbank.websocketmock.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final String jwt = "NWZzMGIyM3J0MXl3a2x1MjNlcDh2YXozZzhrbG9kMDhmZHp4ZnJsNGh3aWpmNHBjeXd1cmF4ZThmbWxsdGN3enRhc3kzcjh5M3d4bTh3N3BxaWR0emljaDkwOG1sY29pbHRyNWswNnF5eWM5OWVuMzdsYTU5d3F3amx1ZHkwd2Q=";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setPathMatcher(new AntPathMatcher("."));
        config.enableSimpleBroker("/subscribe");
        config.setApplicationDestinationPrefixes("/message");
    }

    //        ws://host:port/ws-register?jwt=akmfkjoamnfkoamfklomla
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws-register")
                .setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request,
                                                   ServerHttpResponse response,
                                                   WebSocketHandler wsHandler,
                                                   Map<String, Object> attributes) {
                        // get jwt as web socket url request param
                        String token = ((ServletServerHttpRequest) request)
                                .getServletRequest().getParameter("jwt");

                        log.info("START HANDSHAKE. VALIDATING TOKEN {} ", token);

                        if (StringUtils.isEmpty(token) || !jwt.equals(token)){
                            log.warn("INVALID TOKEN {} ",token);
                            return false;
                        }

                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request,
                                               ServerHttpResponse response,
                                               WebSocketHandler wsHandler, Exception exception) {
                        log.info("HANDSHAKE COMPLETED SUCCESSFULLY");
                    }
                });
    }
}
