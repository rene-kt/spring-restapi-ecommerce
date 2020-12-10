package com.rene.ecommerce.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();

	}

	public String getEmail(String token) {
		
Claims claims = getClaims(token);
		
		if(claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	public boolean isTokenValid(String token) {
		Claims claims = getClaims(token);
		
		if(claims != null) {
			String email = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if(email != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
			return false;
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();

		} catch (Exception e) {
			return null;
		}
	}
}
