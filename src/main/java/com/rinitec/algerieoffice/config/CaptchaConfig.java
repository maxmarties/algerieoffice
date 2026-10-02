package com.rinitec.algerieoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = { "com.rinitec.algerieoffice.captcha" })
public class CaptchaConfig {

	@Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5 * 1000);
        factory.setReadTimeout(8 * 1000);
        return factory;
	}
	
	@Bean
    public RestOperations restTemplate() {
		RestTemplate restTemplate = new RestTemplate(this.clientHttpRequestFactory());
		return restTemplate;
	}
	
}
