package com.aiziyuer.outrage;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.aiziyuer.outrage.modules.core.models.User;

@Component
public class ApplicationContext {

	private static MessageSource messageSource;

	@Autowired
	private MessageSource _messageSource;

	@PostConstruct
	private void init() {
		messageSource = _messageSource;
	}

	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && (auth.getPrincipal() instanceof User)) {
			User u = (User) auth.getPrincipal();

			return u;
		}

		return null;
	}

	public static ResourceBundle i18n() {
		return ResourceBundle.getBundle("i18n.messages", LocaleContextHolder.getLocale());
	}

	public static String language(String key, Object[] args, String defaultValue) {
		return messageSource.getMessage(key, args, defaultValue, LocaleContextHolder.getLocale());
	}

	public static String language(String key, Object... args) {
		return language(key, args, null);
	}
}