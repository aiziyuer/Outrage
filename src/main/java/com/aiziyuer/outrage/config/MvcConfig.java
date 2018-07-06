package com.aiziyuer.outrage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(authenticationInterceptor);
		registry.addInterceptor(localeChangeInterceptor());
		super.addInterceptors(registry);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// documentation for API
		registry.addRedirectViewController("/docs/api", "/docs/api/index.html");

		// registry.addViewController("/login").setViewName("login");
	}

	// Languages
	@Bean
	public LocaleResolver localeResolver() {
		// Options: SessionLocaleResolver, AcceptHeaderLocaleResolver,
		// FixedLocaleResolver
		CookieLocaleResolver lr = new CookieLocaleResolver();
		lr.setCookieName("LOCALE"); // if not set, default
									// `org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE`
		return lr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("locale");
		return lci;
	}
}