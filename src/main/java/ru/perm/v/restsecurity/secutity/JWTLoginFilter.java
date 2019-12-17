package ru.perm.v.restsecurity.secutity;

import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response)
			throws AuthenticationException {
		// Поля username и password д.б. определены
		// либо в теле POST запроса, либо в параметрах GET-запроса
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		logger.info(String.format("JWTLoginFilter.attemptAuthentication: username/password= %s,%s",
				username,
				password));
		return getAuthenticationManager()
				.authenticate(
						new UsernamePasswordAuthenticationToken(username, password,
								Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) {
		logger.info("JWTLoginFilter.successfulAuthentication:");
		logger.info("authResult.getName():" + authResult.getName());
		logger.info("authResult.getPrincipal():" + authResult.getPrincipal());
		// Запись токена в headers
		TokenAuthenticationService.addAuthentication(response, authResult.getName());
		String authorizationString = response.getHeader("Authorization");
		logger.info("Authorization String=" + authorizationString);
	}

}