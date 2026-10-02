package com.rinitec.algerieoffice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	
	@Override
	protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
		messages.nullDestMatcher().authenticated()
				.simpSubscribeDestMatchers("/secured/user/queue/errors").permitAll()
				.simpSubscribeDestMatchers("/topic/**", "/secured/user/**").hasAnyAuthority("ACCOUNT_PRIVILEGE")
				.simpDestMatchers("/website-socket/**").hasAnyAuthority("ACCOUNT_PRIVILEGE")
				.simpTypeMatchers(SimpMessageType.SUBSCRIBE, SimpMessageType.MESSAGE).authenticated()
				.anyMessage().authenticated();
	}
	
	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
	
}
