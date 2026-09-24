package in.main.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String k = "hihellosfasdfghjkl;zxcvbnm,qwertyuiop";

	public SecretKey generateKey() {
		return Keys.hmacShaKeyFor(k.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String Username) {
		return Jwts.builder().subject(Username).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 10000)).signWith(generateKey()).compact();

	}

	public String extractUsername(String token) {
		return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload().getSubject();
	}
	
	public boolean isValid(String token,String Username) {
		return extractUsername(token).equals(Username);
	}
}
