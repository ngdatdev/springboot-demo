package com.nguyendat.blog.security.jwt;

import com.nguyendat.blog.security.UserDetailsImp;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.time.Instant;
import java.util.Date;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${nguyen.dat.keysecret}")
    private String keySecret;

    @Value("${nguyen.dat.jwtExpirationMs}")
    private long jwtExpirationMs;


    public String generateJwtToken(Authentication authentication) {
        UserDetailsImp username = (UserDetailsImp) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(username.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(jwt);
            return true;
        }  catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;

    }
    private Date getExpiration() {
        Instant time = Instant.now().plusMillis(jwtExpirationMs);

        return Date.from(time);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(keySecret));
    }
}
