package com.project.smartschool.security;

import com.project.smartschool.dto.CustomUserDetails;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.UserEntity;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${JWT_SECRET}")
	private String JWT_SECRET;

	@Value("${JWT_EXPIRATION}")
	private long JWT_EXPIRATION;

	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		return Jwts.builder().setSubject(userDetails.getUser().getUsername()).setIssuedAt(now).setExpiration(expiryDate)
				.claim("level", userDetails.getUser().getLevel().toString())
				.claim("roles", userDetails.getRoles())
				.claim("fullname", userDetails.getUser().getFullname())
				.claim("avatar", userDetails.getUser().getAvatar())
				.claim("id", userDetails.getUser().getId())
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}
	
	public String generateToken(UserResponse user) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(now).setExpiration(expiryDate)
				.claim("level", user.getLevel().toString())
				.claim("roles", user.getRoles())
				.claim("fullname", user.getFullname())
				.claim("avatar", user.getAvatar())
				.claim("id", user.getId())
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}

	public String getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

		return String.valueOf(claims.getSubject());
	}

	public boolean validateToken(String authToken)
			throws MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token ");
			throw ex;
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
			throw ex;
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
			throw ex;
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
			throw ex;
		}

	}
}
