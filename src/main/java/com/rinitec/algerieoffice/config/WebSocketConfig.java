package com.rinitec.algerieoffice.config;

import java.util.List;

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
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	
	@Override
	public void configureMessageBroker(final MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic/", "/queue/");
		config.setApplicationDestinationPrefixes("/website-socket");
		config.setUserDestinationPrefix("/secured/user");
		config.setPreservePublishOrder(true);
	}
	
	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		registry.addEndpoint("/secured/room");
		registry.addEndpoint("/secured/room").withSockJS();
	}
	
	@Override
	public boolean configureMessageConverters(final List<MessageConverter> messageConverters) {
		final DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
		converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
	}
	
}
