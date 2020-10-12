package es.udc.fi.dc.fd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.udc.fi.dc.fd.model.form.JwtInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGeneratorImpl implements JwtGenerator {

	@Value("Bu:GW8bgPlEw")
	private String signKey;
	// hikariConfig validationTimeout
	@Value("1440")
	private long expirationMinutes;

	@Override
	public String generate(JwtInfo info) {

		return Jwts.builder().claim("id", info.getId())
				.setExpiration(new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, signKey.getBytes()).compact();

	}

	@Override
	public JwtInfo getInfo(String token) {

		Claims claims = Jwts.parser().setSigningKey(signKey.getBytes()).parseClaimsJws(token).getBody();

		return new JwtInfo(((Integer) claims.get("id")), claims.getSubject());

	}

}
