package top.mxzero.security.jwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import top.mxzero.security.jwt.dto.JwtProps;
import top.mxzero.security.jwt.dto.TokenDTO;
import top.mxzero.security.jwt.dto.TokenType;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peng
 * @since 2024/9/3
 */
public class JwtService {
    @Autowired
    private JwtProps jwtProps;

    @Autowired
    private ObjectMapper objectMapper;

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public long getAccessExpire() {
        return jwtProps.getExpire();
    }

    public long getRefreshExpire() {
        return jwtProps.getRefresh();
    }

    private SecretKey generaKey() {
        byte[] encodeKey = Base64.getDecoder().decode(Base64.getEncoder().encode(this.jwtProps.getSecret().getBytes()));
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
    }


    public String createToken(String tokenId, String subject) {
        try {
            return this.createToken(tokenId, subject, TokenType.ACCESS_TOKEN);
        } catch (Exception e) {
            return null;
        }
    }

    public String createToken(String tokenId, String subject, TokenType tokenType) {
        Date currentDate = new Date();
        Date expireDate;

        // access token
        if (tokenType == TokenType.ACCESS_TOKEN) {
            expireDate = new Date(currentDate.getTime() + jwtProps.getExpire() * 1000);
        } else {
            expireDate = new Date(currentDate.getTime() + jwtProps.getRefresh() * 1000);
        }
//         附加信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", tokenType.getValue());
//         头部信息
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        JwtBuilder jwtBuilder;
        jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setHeader(headers)
                .setId(tokenId)
                .setIssuedAt(currentDate)
                .setIssuer(jwtProps.getIssuer())
                .setSubject(subject)
                .signWith(SIGNATURE_ALGORITHM, generaKey())
                .setExpiration(expireDate);
        return jwtBuilder.compact();
    }


    public TokenDTO createAccessTokenAndRefreshToken(String accessTokenId, String refreshTokenId, String subject) {
        try {
            return new TokenDTO(createToken(accessTokenId, subject), createToken(refreshTokenId, subject, TokenType.REFRESH_TOKEN), getAccessExpire());
        } catch (Exception e) {
            return null;
        }
    }

    public Jws<Claims> parseToken(String token) throws JwtException {
        if (verifyToken(token)) {
            return Jwts.parser().setSigningKey(generaKey()).parseClaimsJws(token);
        }
        return null;
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(generaKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
