package com.example.mobileserver.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserAuthProvider userAuthProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null) {
      String[] elements = header.split(" ");
      if (elements.length == 2 && "Bearer".equals(elements[0])) {
        try {
          SecurityContextHolder.getContext().setAuthentication(
              userAuthProvider.validateToken(elements[1])
          );
        } catch (Exception e) {
          SecurityContextHolder.clearContext();
          throw new RuntimeException(e);
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
