package com.example.mobileserver.controller;

import com.example.mobileserver.config.UserAuthProvider;
import com.example.mobileserver.model.LoginRequest;
import com.example.mobileserver.model.UserAccount;
import com.example.mobileserver.model.dto.CredentialsDTO;
import com.example.mobileserver.model.dto.UserDTO;
import com.example.mobileserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final UserAuthProvider userAuthProvider;


  @PostMapping("/api/auth/login")
  public ResponseEntity<UserDTO> authenticateUser(@RequestBody CredentialsDTO credentialsDTO) {
    UserDTO user = authService.login(credentialsDTO);
    user.setToken(userAuthProvider.createToken(user.getUsername()));

    return ResponseEntity.ok(user);

  }


}
