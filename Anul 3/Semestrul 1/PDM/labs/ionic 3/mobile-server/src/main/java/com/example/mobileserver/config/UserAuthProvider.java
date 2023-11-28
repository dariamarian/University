package com.example.mobileserver.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mobileserver.model.UserAccount;
import com.example.mobileserver.service.AuthService;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

  @Value("${security.jwt.token.secret-key:secret-value}")
  private String secretKey;

  private final AuthService authService;


  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String login){
    Date now = new Date();
    Date validity = new Date(now.getTime() + 3_600_600);

    return JWT.create()
        .withIssuer(login)
        .withIssuedAt(now)
        .withExpiresAt(validity)
        .sign(Algorithm.HMAC256(secretKey));
  }

  public Authentication validateToken(String token) throws Exception{
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
    DecodedJWT decoded =  verifier.verify(token);

    UserAccount user = authService.findByUsername(decoded.getIssuer());

    return new UsernamePasswordAuthenticationToken(user.getUsername(),null, Collections.emptyList());
  }
}
