package com.aiziyuer.outrage.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.aiziyuer.outrage.ApplicationContext;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {

		// 忽略swagger的api
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-ui.html",
				"/swagger-resources", "/webjars/**", "/swagger-resources/configuration/security", "/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().headers().frameOptions().sameOrigin();

		http.exceptionHandling()
				.defaultAuthenticationEntryPointFor(restAuthenticationEntryPoint(),
						new AntPathRequestMatcher("/api/**"))
				.and().authorizeRequests().antMatchers("/error", "/api/core/users/login").permitAll() // 以上api不需要鉴权
				.anyRequest().authenticated();

		// custom JSON based authentication by POST of
		// {"username":"<name>","password":"<password>"} which sets the token header
		// upon authentication
		// httpSecurity.addFilterBefore(loginFilter(),
		// UsernamePasswordAuthenticationFilter.class);

		// custom Token based authentication based on the header previously given to the
		// client
		// httpSecurity.addFilterBefore(new
		// StatelessTokenAuthenticationFilter(tokenAuthenticationService),
		// UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public AuthenticationEntryPoint restAuthenticationEntryPoint() {

		AuthenticationEntryPoint point = new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {

				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						ApplicationContext.language("error.msgUnauthorized"));
			}
		};

		return point;
	}

}
