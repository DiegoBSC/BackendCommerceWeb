package com.backen.multicommerce.security.jwt;

import com.backen.multicommerce.security.entity.PrimaryUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.security}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        PrimaryUser primaryUser = (PrimaryUser) authentication.getPrincipal();
        return Jwts.builder().setSubject(primaryUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .claim("userName", primaryUser.getName())
                .claim("emailUser", primaryUser.getEmail())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getNameUserFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException me) {
            logger.error("token mal formado");
        } catch (UnsupportedJwtException ue) {
            logger.error("token no soportado");
        } catch (ExpiredJwtException ee) {
            logger.error("token expirado");
        } catch (IllegalArgumentException ie) {
            logger.error("token vacio");
        } catch (SignatureException se) {
            logger.error("token mal firmado");
        }
        return false;
    }
}
