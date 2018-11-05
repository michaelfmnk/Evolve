package com.evolvestage.docsapi.security;


import com.evolvestage.docsapi.properties.AuthProperties;
import com.evolvestage.docsapi.utils.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class JwtTokenUtil implements Serializable {
    private static PublicKey publicKey;
    private final TimeProvider timeProvider;
    private final AuthProperties authProperties;

    @PostConstruct
    public void init() throws Exception {

        byte[] publicKeyBytes = Files.readAllBytes(ResourceUtils.getFile(authProperties.getPublicKey()).toPath());

        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyBytes);

        publicKey = KeyFactory.getInstance("RSA").generatePublic(publicSpec);
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    public Integer getUserIdFromToken(String token) {
        return Integer.valueOf(getClaimFromToken(token, Claims::getId));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(timeProvider.getDate());
    }


    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public UserAuthentication buildAuthenticationFromToken(String authToken) {
        return new UserAuthentication(
                JwtUser.builder()
                        .id(getUserIdFromToken(authToken))
                        .build()
        );
    }
}
