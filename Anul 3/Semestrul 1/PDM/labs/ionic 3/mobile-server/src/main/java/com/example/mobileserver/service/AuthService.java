package com.example.mobileserver.service;

import com.example.mobileserver.exception.InvalidPasswordException;
import com.example.mobileserver.model.UserAccount;
import com.example.mobileserver.model.dto.CredentialsDTO;
import com.example.mobileserver.model.dto.UserDTO;
import com.example.mobileserver.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;


  public UserAccount findByUsername(String username)  {
    return userRepository.findByUsername(username).orElseThrow();
  }

  public UserDTO login(CredentialsDTO credentialsDTO){
    UserAccount userAccount = findByUsername(credentialsDTO.getUsername());

    if(credentialsDTO.getPassword().equals(userAccount.getPassword())){
      return UserDTO.builder()
          .id(userAccount.getId())
          .username(userAccount.getUsername())
          .build();
    }
    throw new InvalidPasswordException("Invalid password!", HttpStatus.BAD_REQUEST);
  }
}
