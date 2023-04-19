package com.subway.member.domain;

import com.subway.config.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String ROLES = "roles";
    private final JwtProperties jwtProperties;

    public String createToken(String principal, List<String> roles) {
        return createToken(principal, roles, new Date());
    }

    public String createToken(String principal, List<String> roles, Date now) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date validity = new Date(now.getTime() + jwtProperties.getExpireLength());

        byte[] apiKeySecretBytes = Base64.getEncoder().encode(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        Key secretKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim(ROLES, roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPrincipal(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRoles(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody().get(ROLES, List.class);

    }

    public boolean validateToken(String token) {
        return validateToken(token, new Date());
    }

    public boolean validateToken(String token, Date now) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(now);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}