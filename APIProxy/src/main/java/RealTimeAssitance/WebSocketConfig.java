package RealTimeAssitance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    //add @value for all variables

    // connectionEndpoint: /connect
    // destinationPrefix: /app
    // subscriptionPrefix: /topic
    
    @Value("${websocket.connectionEndpoint}")
    private String connectionEndpoint;

    @Value("${websocket.destinationPrefix}")
    private String destinationPrefix;

    @Value("${websocket.subscriptionPrefix}")
    private String subscriptionPrefix;

    @Value("${websocket.allowedOrigins}")
    private String allowedOrigins;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        System.out.println("subscriptionPrefix: "+ subscriptionPrefix);
        System.out.println("destinationPrefix: "+ destinationPrefix);
        config.enableSimpleBroker(subscriptionPrefix);
        config.setApplicationDestinationPrefixes(destinationPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("connectionEndpoint: "+ connectionEndpoint);
        System.out.println("allowedOrigins: "+ allowedOrigins);
        registry.addEndpoint(connectionEndpoint).setAllowedOriginPatterns(allowedOrigins).withSockJS();
    }
  
    
}
