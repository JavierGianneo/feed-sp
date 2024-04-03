package com.feed.feedsp.config;

import com.feed.feedsp.security.JwtAuthenticationFilter;
import com.feed.feedsp.security.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Autowired
  private AuthenticationConfiguration authenticacionConfiguration;

  @Bean
  AuthenticationManager authenticationManager() throws Exception {
    return authenticacionConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.GET, "/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/posts/feed").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/posts/{id}").hasRole("ADMIN")
            .anyRequest().authenticated())
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtValidationFilter(authenticationManager()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
  }
}
