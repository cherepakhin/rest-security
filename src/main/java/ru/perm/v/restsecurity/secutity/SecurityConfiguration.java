package ru.perm.v.restsecurity.secutity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Реализация JWT+Auth0
 * https://o7planning.org/ru/11677/secure-spring-boot-restful-service-using-auth0-jwt
 * Проверка:
 * Получение JWT токена
 * http  GET :8080/login username=="jerry" password=="123"
 * http  -f POST :8080/login username="jerry" password="123"
 * Использование JWT токена
 * http :8080/account 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqZXJyeSIsImV4cCI6MTU3NzE4ODc0NH0.8B5exq1eYKilvIGauWoa6lz9ubzE4p4QQSTIWSZ9RoTk2ADkSNtt1LGUszrsQA0gKoFQBtXt7k_gfmQ4jGExXA'
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Override
	protected void configure(
			HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// No need authentication.
				.antMatchers("/").permitAll() //
				.antMatchers(HttpMethod.POST, "/login").permitAll() //
				.antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
				// Need authentication.
				.anyRequest().authenticated()
				//
				.and()
				//
				// Add Filter 1 - JWTLoginFilter
				//
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				//
				// Add Filter 2 - JWTAuthenticationFilter
				//
				.addFilterBefore(new JWTAuthenticationFilter(),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String password = "123";
		String encrytedPassword = this.passwordEncoder().encode(password);
		logger.info("Encoded password of 123=" + encrytedPassword);
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> //
				mngConfig = auth.inMemoryAuthentication();
		// Defines 2 users, stored in memory.
		// ** Spring BOOT >= 2.x (Spring Security 5.x)
		// Spring auto add ROLE_
		UserDetails u1 = User.withUsername("tom").password(encrytedPassword).roles("USER_1").build();
		UserDetails u2 = User.withUsername("jerry").password(encrytedPassword).roles("USER_2")
				.build();
		mngConfig.withUser(u1);
		mngConfig.withUser(u2);
	}
}
