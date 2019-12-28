package ru.perm.v.restsecurity.secutity;

import static ru.perm.v.restsecurity.secutity.SecurityConstants.EXPIRATIONTIME;
import static ru.perm.v.restsecurity.secutity.SecurityConstants.HEADER_STRING;
import static ru.perm.v.restsecurity.secutity.SecurityConstants.SECRET;
import static ru.perm.v.restsecurity.secutity.SecurityConstants.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Генерация и проверка токена
 */
public class TokenAuthenticationService {

	private final static Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

	public static void addAuthentication(HttpServletResponse res, String username) {
		// Установка в токене поля subject: равным имени пользователя
		// Использована другая зависимость в pom.xml "com.auth0:java-jwt:3.4.0"
		String token = JWT
				.create()
				.withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.sign(Algorithm.HMAC512(SECRET.getBytes()));
		logger.info("Token:" + token);
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}

	/**
	 * Формирование объекта Authentication для SpringContextHolder. Из статического
	 * SpringContextHolder всегда можно достать Authentication, а следовательно и имя пользователя
	 *
	 * @param request            - http запрос
	 * @param userDetailsService - сервис пользователей
	 * @return - объект Authentication для SpringContextHolder
	 */
	public static UsernamePasswordAuthenticationToken getAuthentication(
			HttpServletRequest request,
			UserDetailsServiceImpl userDetailsService) {
		String token = request.getHeader(HEADER_STRING);
		logger.info("Token:" + token);
		// Если нет строки "Authorization", то будет переход по пути /error
		// Можно сделать отдельный контроллер (см. ru.perm.v.restsecurity.controller.ErrorController)
		// для обработки пути /error
		if (token != null) {
			// Извлечение из request раздела headers
			// Из поля subject достаю имя пользователя
			String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
					.build()
					.verify(token.replace(TOKEN_PREFIX, ""))
					.getSubject();
			logger.info("Body:" + user);
			if (user != null) {
				// ХАК для примера. Генерю роли для пользователя.
				// В обычной системе их нужно доставать из базы
				UserDetails userDetails = userDetailsService
						.loadUserByUsername(user);
//				GrantedAuthority role_user_1 = new SimpleGrantedAuthority("ROLE_USER_1");
//				GrantedAuthority role_user_2 = new SimpleGrantedAuthority("ROLE_USER_2");
				// Назначаю РОЛИ пользователю из токена
				logger.debug("SET ROLES" + userDetails);
				return new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(),
						userDetails.getPassword(),
						userDetails.getAuthorities());
			}
			return null;

		}
		return null;
	}

}