package ru.perm.v.restsecurity.secutity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Генерация и проверка токена
 */
public class TokenAuthenticationService {

	// Срок жизни
	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	// Секретная фраза для шифрования
	static final String SECRET = "ThisIsASecret";
	// Префикс для токена.
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	private final static Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

	public static void addAuthentication(HttpServletResponse res, String username) {
		// Установка в токене поля subject: равным имени пользователя
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	/**
	 * Формирование объекта Authentication для SpringContextHolder. Из статического
	 * SpringContextHolder всегда можно достать Authentication, а следовательно и имя пользователя
	 *
	 * @param request - http запрос
	 * @return - объект Authentication для SpringContextHolder
	 */
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		logger.info("Token:" + token);
		// Если нет строки "Authorization", то будет переход по пути /error
		// Можно сделать отдельный контроллер (см. ru.perm.v.restsecurity.controller.ErrorController)
		// для обработки пути /error
		if (token != null) {
			// Извлечение из request раздела headers
			Claims body = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			// Из поля subject достаю имя пользователя
			String user = body
					.getSubject();
			logger.info("Body:" + body);
			// ХАК для примера. Генерю роли для пользователя.
			// В обычной системе их нужно доставать из базы
			GrantedAuthority role_user_1 = new SimpleGrantedAuthority("ROLE_USER_1");
			GrantedAuthority role_user_2 = new SimpleGrantedAuthority("ROLE_USER_2");
			// Назначаю РОЛИ пользователю из токена
			UsernamePasswordAuthenticationToken u =
					user != null ? new UsernamePasswordAuthenticationToken(user, "123",
							Arrays.asList(role_user_1, role_user_2)) : null;
//			logger.info("GrantedAuthority:" + role_user_2);
//			logger.info("User:" + u);
//			logger.info("User Credential:" + u.getCredentials());
//			logger.info("User Autorities:" + u.getAuthorities());
			return u;
		}
		return null;
	}

}