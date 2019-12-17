package ru.perm.v.restsecurity.secutity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Реализация JWT+Auth0 https://o7planning.org/ru/11677/secure-spring-boot-restful-service-using-auth0-jwt
 * Проверка: Получение JWT токена http  GET :8080/login username=="jerry" password=="123" http  -f
 * POST :8080/login username="jerry" password="123" Использование JWT токена http :8080/account
 * 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqZXJyeSIsImV4cCI6MTU3NzE4ODc0NH0.8B5exq1eYKilvIGauWoa6lz9ubzE4p4QQSTIWSZ9RoTk2ADkSNtt1LGUszrsQA0gKoFQBtXt7k_gfmQ4jGExXA'
 */
//@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(
			HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()

				.antMatchers("/").permitAll()

				.antMatchers(HttpMethod.POST, "/login").permitAll() //
				.antMatchers(HttpMethod.GET, "/error").permitAll() //
				.antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
				// Позволяю всем регистрироваться
				// Проверка: http -j PUT :8080/account username=aaa password=ppp
				.antMatchers(HttpMethod.PUT, "/account").permitAll()

				// Только роль USER_2 имеет доступ с /account
				.antMatchers(HttpMethod.GET, "/account")
				.hasRole("USER_2")

				// К /account/1 могут обращаться все
				.antMatchers("/account/1").permitAll()

				// Все остальные запросы должны быть аутетифицированы
				.anyRequest()
				.authenticated()

				// Убрал создание куки для сессии
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER)

				//
				.and()
				//
				// Получение токена по пути /login
				//
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				//
				// Извлечение токена из запросов и добавление Authentication
				// в SecurityContextHolder
				//
				.addFilterBefore(new JWTAuthorizationFilter(authenticationManager(),userDetailsService),
						UsernamePasswordAuthenticationFilter.class)
		;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}

