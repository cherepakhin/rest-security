package ru.perm.v.restsecurity.secutity;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {

	private final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// Получение объекта Authentication (с именем пользователя, ролями и.т.п)
		// по токену из request
		Authentication authentication = TokenAuthenticationService
				.getAuthentication((HttpServletRequest) servletRequest);
		logger.info("Authentication:" + authentication);
		logger.info(
				"SecurityContextHolder.getContext() before:" + SecurityContextHolder.getContext());
		// Привязка объекта Authentication к статическому SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		logger.info(
				"SecurityContextHolder.getContext() after:" + SecurityContextHolder.getContext());
		// Отправка текущего request для следующих фильтров
		filterChain.doFilter(servletRequest, servletResponse);
	}

}