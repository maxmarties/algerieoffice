package com.rinitec.algerieoffice.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.web.client.RestTemplate;

import com.rinitec.algerieoffice.security.google2fa.CustomAuthenticationProvider;
import com.rinitec.algerieoffice.security.google2fa.CustomWebAuthenticationDetailsSource;
import com.rinitec.algerieoffice.security.oauth2.CustomOAuth2UserService;
import com.rinitec.algerieoffice.security.oauth2.CustomOidcUserService;
import com.rinitec.algerieoffice.security.oauth2.OAuth2AccessTokenResponseConverterWithDefaults;
import com.rinitec.algerieoffice.security.users.RememberMeUser;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.rinitec.algerieoffice.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] IGNORED_RESOURCE = new String[] {
			"/i18n/**", 
			"/static/**", 
			"/resources/**", 
			"/media/**", 
			"/mapsite/**",
			"/robots.txt", 
			"/robot.txt"
	};
	
	private static final String[] PASSWORD_RESOURCE = new String[] {
			"/website/update-password/new*", 
			"/website/update-password/save*"
	};
	
	private static final String[] ACCOUNT_RESOURCE = new String[] {
			"/config/**",
			"/proxy/**",
			"/user/**/**",
			"/feedback/browser/**",
			"/feedback/members/**",
			"/feedback/dashboard/**",
			"/website/login-target*",
			"/inbox/messages/**",
			"/inbox/notification/**",
			"/inbox/chater/**",
			"/inbox/support/**",
			"/inbox/followed/companies*",
			"/inbox/followed/accounts*"
	};
	
	private static final String[] VISITOR_RESOURCE = new String[] {"/guest/**/**"};
	
	private static final String[] COMPANY_VISIT_RESOURCE = new String[] {
			"/forums/**",
			"/document/**",
			"/inbox/talk/**",
			"/inbox/followed/users*",
			"/explorer/preview/**",
			"/feedback/analytic/**",
			"/feedback/easylist/**",
			"/feedback/forums/**",
			"/company/help/**",
			"/company/dashboard*",
			"/company/dashboard/home*",
			"/company/dashboard/analytic*",
			"/company/dashboard/journal*",
			"/company/dashboard/detect*",
			"/company/posts/all*",
			"/company/posts/categories*",
			"/company/posts/statistic*",
			"/company/marketplace/promotes*",
			"/company/marketplace/ads*",
			"/company/marketplace/jobs*",
			"/company/marketplace/campaigns*",
			"/company/portfolio/works*",
			"/company/portfolio/actus*",
			"/company/portfolio/events*",
			"/company/portfolio/faqs*",
			"/company/portfolio/partners*",
			"/company/communication/notices*",
			"/company/communication/appointments*",
			"/company/communication/evaluations*",
			"/company/communication/collaborators*",
			"/company/communication/contacts*",
			"/company/communication/contacts/detail*",
			"/company/communication/partners*",
			"/company/communication/chatbots*",
			"/company/communication/chatbots/detail*",
			"/company/prospect/quotes*",
			"/company/prospect/quotes/detail*",
			"/company/prospect/quotes/validate*",
			"/company/prospect/ads*",
			"/company/prospect/ads/detail*",
			"/company/prospect/ads/validate*",
			"/company/prospect/infos*",
			"/company/prospect/infos/detail*",
			"/company/prospect/infos/validate*",
			"/company/prospect/jobs*",
			"/company/prospect/jobs/detail*",
			"/company/prospect/jobs/validate*",
			"/company/feddback/communications*",
			"/company-user/feddback/communications*",
			"/company-user/easylist/**"
	};
	private static final String[] COMPANY_AUTOR_RESOURCE = new String[] {
			"/company/posts/new*",
			"/company/posts/update*",
			"/company/posts/categories/new*",
			"/company/posts/categories/update*",
			"/company/marketplace/ads/new*",
			"/company/marketplace/ads/update*",
			"/company/marketplace/jobs/new*",
			"/company/marketplace/jobs/update*",
			"/company/marketplace/promotes/new*",
			"/company/marketplace/promotes/update*",
			"/company/marketplace/promotes/order*",
			"/company/marketplace/promotes/order/**",
			"/company/marketplace/campaigns/new*",
			"/company/marketplace/campaigns/update*",
			"/company/marketplace/campaigns/order*",
			"/company/marketplace/campaigns/order/**",
			"/company/marketplace/campaigns/load/**",
			"/company/portfolio/works/new*",
			"/company/portfolio/works/update*",
			"/company/portfolio/actus/new*",
			"/company/portfolio/actus/update*",
			"/company/portfolio/events/new*",
			"/company/portfolio/events/update*",
			"/company/portfolio/faqs/new*",
			"/company/portfolio/faqs/update*",
			"/company/portfolio/partners/new*",
			"/company/portfolio/partners/update*",
			"/company/portfolio/partners/guest*"
	};
	private static final String[] COMPANY_EDIT_RESOURCE = new String[] {
			"/company/overview/**/**",
			"/company/newsletter/**/**",
			"/company/posts/edit*",
			"/company/posts/all/delete*",
			"/company/posts/categories/edit*",
			"/company/posts/categories/delete*",
			"/company/marketplace/ads/edit*",
			"/company/marketplace/ads/publish*",
			"/company/marketplace/ads/delete*",
			"/company/marketplace/jobs/edit*",
			"/company/marketplace/jobs/publish*",
			"/company/marketplace/jobs/delete*",
			"/company/marketplace/promotes/edit*",
			"/company/marketplace/promotes/delete*",
			"/company/marketplace/campaigns/edit*",
			"/company/marketplace/campaigns/delete*",
			"/company/portfolio/works/edit*",
			"/company/portfolio/works/delete*",
			"/company/portfolio/actus/edit*",
			"/company/portfolio/actus/delete*",
			"/company/portfolio/events/edit*",
			"/company/portfolio/events/delete*",
			"/company/portfolio/faqs/edit*",
			"/company/portfolio/faqs/delete*",
			"/company/portfolio/partners/edit*",
			"/company/portfolio/partners/delete*"
	};
	private static final String[] COMPANY_MANAGER_RESOURCE = new String[] {
			"/company/manage/**/**",
			"/company/tools/**/**",
			"/company/communication/notices/validate*",
			"/company/communication/notices/delete*",
			"/company/communication/appointments/edit*",
			"/company/communication/appointments/update*",
			"/company/communication/appointments/delete*",
			"/company/communication/collaborators/validate*",
			"/company/communication/collaborators/delete*",
			"/company/communication/contacts/delete*",
			"/company/communication/partners/validate*",
			"/company/communication/partners/delete*",
			"/company/communication/chatbots/delete*",
			"/company/prospect/quotes/delete*",
			"/company/prospect/ads/delete*",
			"/company/prospect/infos/delete*",
			"/company/prospect/jobs/delete*",
			"/company-user/feddback/communications/**"
	};
	private static final String[] COMPANY_ADMIN_RESOURCE = new String[] {
			"/company/delete/**",
			"/company/profile/**/**",
			"/company/team/**/**",
			"/company/dashboard/journal/delete*",
			"/company/dashboard/detect/delete*",
			"/company/communication/collaborators/new*"
	};
	
	private static final String[] SUPPORT_AUTOR_RESOURCE = new String[] {
			"/envelope/**",
			"/inbox/supports/**",
			"/feedback/admin/**",
			"/admin/dashboard*",
			"/admin/dashboard/home*",
			"/admin/dashboard/journal*",
			"/admin/companies/all*",
			"/admin/companies/profiles*",
			"/admin/companies/features*",
			"/admin/companies/customers*",
			"/admin/blog/all*",
			"/admin/blog/new*",
			"/admin/blog/edit*",
			"/admin/blog/update*",
			"/admin/blog/check-identify*",
			"/admin/blog/autors*",
			"/admin/blog/autors/new*",
			"/admin/blog/autors/edit*",
			"/admin/blog/autors/update*",
			"/admin/blog/autors/check-identify*",
			"/admin/blog/stats*",
			"/admin/data/activities*",
			"/admin/marketplace/promotes*",
			"/admin/marketplace/orders*",
			"/admin/marketplace/sponsores*",
			"/admin/marketplace/campaigns*",
			"/admin/marketplace/audiances*",
			"/admin/marketplace/ads*",
			"/admin/marketplace/jobs*",
			"/admin/feedback/reports*",
			"/admin/feedback/rates*",
			"/admin/feedback/locks*",
			"/admin/feedback/blocks*",
			"/admin/premium/subscribes*",
			"/admin/premium/orders*",
			"/admin/premium/budgets*",
			"/admin/realtime/companies*",
			"/admin/realtime/users*",
			"/admin/realtime/testimonials*",
			"/admin/realtime/assists*",
			"/admin/realtime/problems*",
			"/admin/realtime/contacts*",
			"/admin/realtime/chatbots*",
			"/admin/team/users*",
			"/admin/team/moderators*",
			"/admin/easylist/companies*",
			"/admin/easylist/documents*",
			"/admin-user/feedback/supports*",
			"/admin-user/feedback/responses*",
			
	};
	private static final String[] SUPPORT_MANAGER_RESOURCE = new String[] {
			"/admin/mailing/**",
			"/admin/companies/all/lock*",
			"/admin/companies/all/disable*",
			"/admin/companies/all/deactivate*",
			"/admin/blog/all/delete*",
			"/admin/blog/autors/delete*",
			"/admin/blog/stats/edit*",
			"/admin/blog/stats/update*",
			"/admin/data/activities/**",
			"/admin/data/identities/**",
			"/admin/data/postal/**",
			"/admin/data/social/**",
			"/admin/marketplace/promotes/**",
			"/admin/marketplace/orders/**",
			"/admin/marketplace/sponsores/**",
			"/admin/marketplace/campaigns/**",
			"/admin/marketplace/audiances/**",
			"/admin/feedback/reports/**",
			"/admin/feedback/rates/**",
			"/admin/feedback/locks/**",
			"/admin/feedback/blocks/**",
			"/admin/premium/subscribes/**",
			"/admin/premium/formule/**",
			"/admin/premium/orders/**",
			"/admin/premium/emailings*",
			"/admin/premium/emailings/**",
			"/admin/premium/budgets/**",
			"/admin/realtime/companies/**",
			"/admin/realtime/users/**",
			"/admin/realtime/testimonials/**",
			"/admin/realtime/assists/**",
			"/admin/realtime/problems/**",
			"/admin/realtime/contacts/**",
			"/admin/realtime/chatbots/**",
			"/admin/team/users/lock*",
			"/admin/easylist/companies/**",
			"/admin/easylist/documents/**",
			"/admin-user/feedback/supports/**",
			"/admin-user/feedback/responses/**",
			
	};
	private static final String[] SUPPORT_ADMIN_RESOURCE = new String[] {
			"/admin/dashboard/journal/delete*",
			"/admin/companies/all/delete*",
			"/admin/companies/profiles/delete*",
			"/admin/companies/features/**",
			"/admin/companies/customers/**",
			"/admin/team/users/new*",
			"/admin/team/users/save*",
			"/admin/team/users/delete*",
			"/admin/team/moderators/**",
			"/admin/team/managers*",
			"/admin/team/managers/**",
			"/admin/communication/**",
			
	};
	
	private static final String LOGIN_RESOURCE = "/users/login";
	
	private PasswordEncoder passwordEncoder;
	private UserDetailsService userDetailsService;
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	private LogoutSuccessHandler logoutSuccessHandler;
	private AuthenticationFailureHandler authenticationFailureHandler;
	private CustomWebAuthenticationDetailsSource authenticationDetailsSource;
	private CustomOidcUserService customOidcUserService;
	private CustomOAuth2UserService customOAuth2UserService;

	
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, AuthenticationSuccessHandler authenticationSuccessHandler,
			LogoutSuccessHandler logoutSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, CustomWebAuthenticationDetailsSource authenticationDetailsSource, 
			CustomOidcUserService customOidcUserService, CustomOAuth2UserService customOAuth2UserService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.logoutSuccessHandler = logoutSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.authenticationDetailsSource = authenticationDetailsSource;
		this.customOidcUserService = customOidcUserService;
		this.customOAuth2UserService = customOAuth2UserService;
	}
	
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}
	
	@Bean
	public RememberMeUser rememberMeServices() {
		final RememberMeUser rememberMeUser = new RememberMeUser("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
        return rememberMeUser;
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, IGNORED_RESOURCE);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers(PASSWORD_RESOURCE).hasAuthority("PASSWORD_PRIVILEGE")
			.antMatchers(ACCOUNT_RESOURCE).hasAuthority("ACCOUNT_PRIVILEGE")
			.antMatchers(VISITOR_RESOURCE).hasAuthority("VISITOR_PRIVILEGE")
			.antMatchers(COMPANY_VISIT_RESOURCE).hasAuthority("COMPANY_VISIT_PRIVILEGE")
			.antMatchers(COMPANY_AUTOR_RESOURCE).hasAuthority("COMPANY_AUTOR_PRIVILEGE")
			.antMatchers(COMPANY_EDIT_RESOURCE).hasAuthority("COMPANY_EDIT_PRIVILEGE")
			.antMatchers(COMPANY_MANAGER_RESOURCE).hasAuthority("COMPANY_MANAGER_PRIVILEGE")
			.antMatchers(COMPANY_ADMIN_RESOURCE).hasAuthority("COMPANY_ADMIN_PRIVILEGE")
			.antMatchers(SUPPORT_AUTOR_RESOURCE).hasAuthority("SUPPORT_AUTOR_PRIVILEGE")
			.antMatchers(SUPPORT_MANAGER_RESOURCE).hasAuthority("SUPPORT_MANAGER_PRIVILEGE")
			.antMatchers(SUPPORT_ADMIN_RESOURCE).hasAuthority("SUPPORT_ADMIN_PRIVILEGE")
			.anyRequest().permitAll()
			.and().exceptionHandling().accessDeniedPage("/403")
			.and().formLogin()
				.loginPage(LOGIN_RESOURCE)
				.defaultSuccessUrl("/user/dashboard?logSucc=true")
				.failureUrl(LOGIN_RESOURCE.concat("?error=true"))
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.authenticationDetailsSource(authenticationDetailsSource).permitAll()
			.and().sessionManagement()
				.maximumSessions(1).expiredUrl(LOGIN_RESOURCE.concat("?info=sessExpired"))
				.sessionRegistry(sessionRegistry())
			.and().sessionFixation().none()
			.and().logout()
				.logoutSuccessHandler(logoutSuccessHandler)
				.invalidateHttpSession(false)
				.logoutSuccessUrl("/?logSucc=true")
				.deleteCookies("JSESSIONID")
				.permitAll()
			.and().rememberMe().rememberMeServices(rememberMeServices()).key("theKey")
			.and().oauth2Login()
				.loginPage(LOGIN_RESOURCE)
				.failureHandler(authenticationFailureHandler)
				.failureUrl("/website/signin/popup/close?error=true")
				.defaultSuccessUrl("/website/signin/popup/close?logSucc=true")
				.userInfoEndpoint().oidcUserService(customOidcUserService)
				.userService(customOAuth2UserService)
			.and().tokenEndpoint().accessTokenResponseClient(authorizationCodeTokenResponseClient());
	}
	
	private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> authorizationCodeTokenResponseClient() {
		final OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
		tokenResponseHttpMessageConverter.setTokenResponseConverter(new OAuth2AccessTokenResponseConverterWithDefaults());
		final RestTemplate restTemplate = new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
		final DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
		tokenResponseClient.setRestOperations(restTemplate);
		return tokenResponseClient;
	}
	
}
