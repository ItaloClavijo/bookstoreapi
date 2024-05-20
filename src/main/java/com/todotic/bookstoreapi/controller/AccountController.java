package com.todotic.bookstoreapi.controller;

import com.todotic.bookstoreapi.model.dto.SignupFormDTO;
import com.todotic.bookstoreapi.model.dto.UserProfileDTO;
import com.todotic.bookstoreapi.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public UserProfileDTO signup(@RequestBody @Validated SignupFormDTO signupFormDTO) {
        return accountService.signup(signupFormDTO);
    }

}
