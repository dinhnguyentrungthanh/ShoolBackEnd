package com.project.smartschool.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer   {
	
	@Value("${my.domain}")
	private String domain;

//	@Override
//	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(new SocketHandler(), "/")
//        .setAllowedOrigins("*")
//        // initial Request/Handshake interceptor
//        .addInterceptors(new HttpSessionHandshakeInterceptor() {
//
//            @Override
//            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception ex) {
//                super.afterHandshake(request, response, wsHandler, ex);
//            }
//
//            @Override
//            public boolean beforeHandshake(ServerHttpRequest request, 
//                                            ServerHttpResponse response, WebSocketHandler wsHandler, 
//                                            Map<String, Object> attributes) throws Exception {
//                boolean b = super.beforeHandshake(request, response, wsHandler, attributes);
//                            // && (request.getPrincipal()).isAuthenticated();
//                return b;
//            }
//
//        });
//	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket/blog").setAllowedOrigins(domain).withSockJS();
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
		resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);

		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		converter.setContentTypeResolver(resolver);
		messageConverters.add(converter);

		return false;
	}

}
