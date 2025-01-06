package project.globalservice.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import project.globalservice.config.JwtConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
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

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtConfig.getSecret()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
