package com.example.DATN.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "77397A244326462948404D635166546A576E5A7234753778214125442A472D4B";
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 15; // 30 ngày
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 15; // 30 ngày

    @Autowired
    private StringRedisTemplate redisTemplate; // Sử dụng Redis để lưu token bị vô hiệu hóa

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails, Long userId) {
        return generateToken(new HashMap<>(), userDetails, userId, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails, Long userId) {
        return generateToken(new HashMap<>(), userDetails, userId, REFRESH_TOKEN_EXPIRATION);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Long userId, long expirationTime) {
        extraClaims.put("userId", userId);
        extraClaims.put("authorities", userDetails.getAuthorities());
        System.out.println("Issued At: " + new Date(System.currentTimeMillis()));
        System.out.println("expirationTime At: " + new Date(expirationTime));
        System.out.println("Expires At: " + new Date(System.currentTimeMillis() + expirationTime));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (userDetails == null) {
            return false;
        }
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Vô hiệu hóa token bằng cách lưu vào Redis với TTL bằng thời gian còn lại của token.
     */
    public void invalidateToken(String token) {
        Date expiration = extractExpiration(token);
        long ttl = (expiration.getTime() - System.currentTimeMillis()) / 1000; // Tính TTL theo giây
        if (ttl > 0) {
            redisTemplate.opsForValue().set("blacklist:" + token, "invalid", ttl, TimeUnit.SECONDS);
        }
    }

    /**
     * Kiểm tra xem token có bị vô hiệu hóa không.
     */
    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey("blacklist:" + token);
    }
}
