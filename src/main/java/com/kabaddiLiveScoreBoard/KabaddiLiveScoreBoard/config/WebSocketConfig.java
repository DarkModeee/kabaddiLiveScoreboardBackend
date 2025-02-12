package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable simple broker for broadcasting messages
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();

        config.enableSimpleBroker("/topic", "/user")
                .setTaskScheduler(taskScheduler) // Heartbeat mechanism
                .setHeartbeatValue(new long[]{10000, 20000}); // Send heartbeat every 10 sec, expect every 20 sec

        config.setApplicationDestinationPrefixes("/app"); // Prefix for client messages
        config.setUserDestinationPrefix("/user"); // Enables private messages if needed
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/scoreboard-websocket")
                .setAllowedOrigins("wondrous-tartufo-db985a.netlify.app") // Allow React frontend
                .withSockJS(); // Enable SockJS fallback for clients that don’t support WebSocket
    }
}
