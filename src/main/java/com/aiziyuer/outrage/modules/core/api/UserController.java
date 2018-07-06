package com.aiziyuer.outrage.modules.core.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiziyuer.outrage.modules.core.models.User;
import com.aiziyuer.outrage.modules.core.services.UserService;

import io.swagger.annotations.Api;

@Api(value = "User API")
@RestController
@RequestMapping({ "/api/core/users", "/api/v1/core/users" })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(value = "/login")
	public boolean login(@RequestBody User user, HttpServletRequest request) {

		User u = this.userService.login(user.getUserName(), user.getPassowrd());

		if (u != null) {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					user.getUserName(), user.getPassowrd());

			// Authenticate the user
			Authentication authentication = authenticationManager.authenticate(authRequest);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);

			// Create a new session and add the security context.
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			return true;
		}

		return false;
	}

}
