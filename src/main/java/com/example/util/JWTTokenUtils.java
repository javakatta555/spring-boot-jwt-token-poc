package com.example.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JWTTokenUtils {

    private static final String jwtSecretKey="jwtSecretKey";

    public static final String   BEARER                    = "BEARER";
    public static final String   BEARER_LOWER              = "Bearer";

    // JWT Token Lifetime is set as	one minutes
    static final long expiryDate = System.currentTimeMillis() + (long) 1000 * 60;

    public static String generateToken( Map<String, String> keyValuePairs) {

        JwtBuilder builder = Jwts.builder().setIssuer("product").setExpiration(new Date(expiryDate));
        if (keyValuePairs != null) {
            for (String key : keyValuePairs.keySet()) {
                builder.claim(key, replaceNull(keyValuePairs.get(key)));
            }
        }
        return builder.signWith(SignatureAlgorithm.HS256,jwtSecretKey).compact();
    }

    public static String replaceNull(String input) {
        return input == null ? "" : input;
    }

    public static Boolean verifyToken(String token) throws Exception {
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private static Claims getClaims(String Token){
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(Token).getBody();
    }

    public static String getTokenFromAuthorizationHeader(String header) {
        header = header.replaceFirst(BEARER_LOWER, "").trim();
        return header.replaceFirst(BEARER, "").trim();
    }
}



