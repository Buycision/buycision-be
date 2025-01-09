package project.gatewayservice.auth.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.gatewayservice.auth.config.JwtConfig;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtConfig jwtConfig;

    public String createToken(String authentication) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + jwtConfig.getExpireTime() * 1000);

        return Jwts.builder()
                .setSubject(authentication)
                .signWith(generateKey(), SignatureAlgorithm.HS512)
                .setExpiration(expireTime)
                .compact();
    }

    private Key generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token);
    }

    public static <T> T decodePayload(String token, Class<T> clazz) {
        String jwtPayload = token.split("\\.")[1];
        Base64.Decoder base64Decoder = Base64.getUrlDecoder();
        String payload = new String(base64Decoder.decode(jwtPayload));
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(payload, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode payload", e);
        }
    }
}
