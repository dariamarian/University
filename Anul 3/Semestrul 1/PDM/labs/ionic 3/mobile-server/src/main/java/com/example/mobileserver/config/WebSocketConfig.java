package com.example.mobileserver.config;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final UserAuthProvider userAuthProvider;

  @Value("${cors.allowed-origins}")
  private String[] corsAllowedOrigins;


  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/movie-notifications").setAllowedOrigins(corsAllowedOrigins);
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic");
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    System.out.println("incepe concectarea websocket");
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
          String jwt = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
          if (jwt != null) {
            String[] elements = jwt.split(" ");
            if (elements.length == 2 && "Bearer".equals(elements[0])) {
              try {
                SecurityContextHolder.getContext().setAuthentication(
                    userAuthProvider.validateToken(elements[1])
                );
              } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT");
              }
            } else {
              throw new BadCredentialsException("Invalid JWT format");
            }
          } else {
            throw new BadCredentialsException("Missing JWT in headers");
          }
        }
        return message;
      }
    });
  }

}
