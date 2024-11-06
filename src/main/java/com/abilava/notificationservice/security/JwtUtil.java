package com.abilava.notificationservice.security;

import com.abilava.notificationservice.entities.UserCredential;
import com.abilava.notificationservice.exceptions.InvalidCredentialsException;
import com.abilava.notificationservice.exceptions.TokenExpiredException;
import com.abilava.utils.LoggerUtil;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private final String secretKey;
    private final long tokenTtl;
    private final JwtParser jwtParser;

    public JwtUtil(@Value("${security.token.secret-key}") @NonNull String secretKey,
                   @Value("${security.token.ttl}") long tokenTtl) {
        this.jwtParser = Jwts.parser().setSigningKey(secretKey);
        this.secretKey = secretKey;
        this.tokenTtl = tokenTtl;
    }

    public String createToken(UserCredential user) {
        var claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId().toString());
        var tokenValidity = new Date(new Date().getTime() + TimeUnit.MINUTES.toMillis(tokenTtl));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            var token = resolveToken(req);
            if (Objects.nonNull(token)) {
                return parseJwtClaims(token);
            }
            throw new InvalidCredentialsException();
        } catch (ExpiredJwtException e) {
            LoggerUtil.error(String.format("Exception: %s", e));
            throw new TokenExpiredException();
        } catch (Exception e) {
            LoggerUtil.error(String.format("Exception: %s", e));
            throw new InvalidCredentialsException();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        var bearerToken = request.getHeader(TOKEN_HEADER);
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            LoggerUtil.error(String.format("Exception: %s", e));
            throw new InvalidCredentialsException();
        }
    }

}
