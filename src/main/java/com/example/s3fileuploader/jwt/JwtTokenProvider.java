package com.example.s3fileuploader.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    final Map<String, String> env = System.getenv();

    @Value("${signing-key}")
    private String privateKeyB64;
    @Value("${app-jwt-expiration-milliseconds}")
    private int jwtExpirationDate;

    private static final Logger LOGGER = Logger.getLogger(JwtTokenProvider.class.getName());

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        LOGGER.info("This is the expiration date: " + expireDate.toString());


        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.RS256, privateKey())
                .compact();

        return jwtToken;
    }

    private PrivateKey privateKey(){
        try{
            LOGGER.info(privateKeyB64);
            byte[] privateKeyDecoded = Base64.getDecoder().decode(privateKeyB64);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyDecoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(privateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(privateKey())
                    .build()
                    .parse(token);
            return true;
        }
        catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return false;
    }
}
