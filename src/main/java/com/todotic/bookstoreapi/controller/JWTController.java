package com.todotic.bookstoreapi.controller;

import com.todotic.bookstoreapi.model.dto.AuthRequest;
import com.todotic.bookstoreapi.model.dto.AuthResponse;
import com.todotic.bookstoreapi.model.dto.UserProfileDTO;
import com.todotic.bookstoreapi.model.entity.User;
import com.todotic.bookstoreapi.repository.UserRepository;
import com.todotic.bookstoreapi.security.TokenProvider;
import com.todotic.bookstoreapi.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class JWTController {

    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private TokenProvider tokenProvider;
    private AccountService accountService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> getAccessToken(@RequestBody AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(authentication);
        UserProfileDTO userProfileDTO = accountService.findByEmail(authRequest.getEmail());
        AuthResponse authResponse = new AuthResponse(accessToken, userProfileDTO);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(authResponse);
    }

}
