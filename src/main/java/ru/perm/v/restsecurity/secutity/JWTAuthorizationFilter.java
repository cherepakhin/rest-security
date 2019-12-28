package ru.perm.v.restsecurity.secutity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.perm.v.restsecurity.secutity.SecurityConstants.HEADER_STRING;
import static ru.perm.v.restsecurity.secutity.SecurityConstants.TOKEN_PREFIX;

/**
 * Фильтр проверки токена.
 * Использована другая зависимость в pom.xml "com.auth0:java-jwt:3.4.0"
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
	UserDetailsServiceImpl userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String header = request.getHeader(HEADER_STRING);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		// Получение объекта Authentication (с именем пользователя, ролями и.т.п)
		// по токену из request
		logger.info(
				"SecurityContextHolder.getContext() BEFORE:" + SecurityContextHolder.getContext());
		Authentication authentication = TokenAuthenticationService
				.getAuthentication(request, userDetailsService);
		// Привязка объекта Authentication к статическому SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		logger.info(
				"SecurityContextHolder.getContext() AFTER:" + SecurityContextHolder.getContext());
		// Отправка текущего request для следующих фильтров
		chain.doFilter(request, response);
	}
}