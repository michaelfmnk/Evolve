package com.evolvestage.api.security;

import com.evolvestage.api.properties.AuthProperties;
import com.evolvestage.api.utils.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_ID = "jti";

    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    private TimeProvider timeProvider;
    private final AuthProperties authProperties;
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {

        byte[] privateKeyBytes = Files.readAllBytes(ResourceUtils.getFile(authProperties.getPrivateKey()).toPath());
        byte[] publicKeyBytes = Files.readAllBytes(ResourceUtils.getFile(authProperties.getPublicKey()).toPath());

        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyBytes);

        privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateSpec);
        publicKey = KeyFactory.getInstance("RSA").generatePublic(publicSpec);
    }

    /**
     * Gets username from a given jwt-token
     * @param token jwt-token
     * @return Username from a token
     */
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public Integer getUserIdFromToken(String token) {
        return Integer.valueOf(getClaimFromToken(token, Claims::getId));
    }

    /**
     * @param token jwt-token
     * @param claimResolver function
     * @return claim by claimResolver
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }


    /**
     * @param token jwt-token
     * @return all Claims from a token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * Checks whether jwt-token is not expired.
     * Uses getExpirationDateFromToken() to get expiration date
     * Gets current time from TimeProvider and compares them
     *
     * @param token jwt-token
     * @return true if token is expired; false if token is not expired;
     */
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(timeProvider.getDate());
    }


    /**
     * @param token jwt-token
     * @return token's Expiration date
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    /**
     * Checks whether the jwt-token is valid for a given user;
     * Casts UserDetails to JwtUser, gets username and issued-date;
     *
     * Token is valid if username in token equals JwtUsers's username,
     * token is not expired (see isTokenExpired()) and jwt-token is created
     * before the previous password reset date
     * @param token jwt-token
     * @param userDetails JwtUser
     * @return true if token is valid, false - not valid
     */
    public Boolean validateToken(String token, JwtUser userDetails) {
        final String email = getEmailFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (
                Objects.equals(email, userDetails.getEmail())
                        && !isTokenExpired(token)
                        && userDetails.isEnabled()
                        && !isCreatedBeforeLastPasswordReset(created, userDetails.getLastPasswordResetDate())
        );
    }


    /**
     * @param token jwt-token
     * @return Date when token was created
     */
    private Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }


    /**
     * Checks whether the token was created before the lass password's reset date
     * @param created Date
     * @param lastPasswordReset Date
     * @return boolean
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }


    /**
     * Uses private method createToken() to generate new JWT-Token
     * Passes username & audience (as string) to createToken()
     * @param jwtUser JwtUser
     * @return String - jwt-token
     */
    public String generateToken(JwtUser jwtUser) {
        Map<String, Object> claims = generateClaims(jwtUser);
        return createToken(claims, jwtUser.getEmail());
    }


    /**
     * Creates new jwt token
     *
     * Uses calculateExpirationDate() to get expiration date, then uses
     * Uses JWTs builder to create new token.
     * Uses HS512 Signature algorithm;
     *
     * @param claims
     * @param subject username
     * @return String jwt-token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        final Date createdDate = timeProvider.getDate();
        final Date expirationDate = calculateExpirationDate(createdDate);
        System.out.println("createToken " + createdDate);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();

    }


    /**
     * Adds to the current date N (expiration) seconds;
     * @param createdDate Date now
     * @return expiration Date
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + authProperties.getExpiration() * 1000);
    }


    /**
     * Checks whether token can be refreshed;
     * Conditions:
     *  - is created after the last password reset
     *  - token is not expired OR expiration can be ignored
     * @param token jwt-token
     * @param lastPasswordResetDate
     * @return
     */
    public boolean canTokenBeRefreshed(String token, Date lastPasswordResetDate) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordResetDate) &&
                (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }


    /**
     * Checks whether expiration can be ignored while refreshing token
     * @param token jwt
     * @return boolean
     */
    private boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }


    /**
     * Gets Audience as a string from a given jwt-token
     * @param token jwt-token
     * @return String - audience
     */
    private String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }


    /**
     * Refreshes jwt-token;
     * creates claims based on the old token, changes issuedDate and expiration date,
     * then builds new token;
     * @param token jwt-token
     * @return new jwt-token
     */
    public String refreshToken(String token) {
        final Date createdDate = timeProvider.getDate();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    private Map<String, Object> generateClaims(JwtUser jwtUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ID, jwtUser.getId());
        return claims;
    }
}
