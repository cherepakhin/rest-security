package ru.perm.v.restsecurity.secutity;

public class SecurityConstants {
	// Срок жизни
	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	// Секретная фраза для шифрования
	static final String SECRET = "ThisIsASecret";
	// Префикс для токена.
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
}
