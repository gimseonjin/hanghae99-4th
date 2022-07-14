package com.security.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.security.model.User;

import java.time.Instant;
import java.util.Optional;

public class JWTUtil {

    private static final String SECRET_KEY = "carrykim";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    private static final long AUTH_TIME = 60 * 60;

    public static String getToken(User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String verify(String token){
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return verify.getSubject();
        }catch(Exception ex){
            return "";
        }
    }
}
