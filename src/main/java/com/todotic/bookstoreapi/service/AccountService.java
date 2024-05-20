package com.todotic.bookstoreapi.service;

import com.todotic.bookstoreapi.exception.BadRequestException;
import com.todotic.bookstoreapi.exception.ResourceNotFoundException;
import com.todotic.bookstoreapi.model.dto.SignupFormDTO;
import com.todotic.bookstoreapi.model.dto.UserProfileDTO;
import com.todotic.bookstoreapi.model.entity.User;
import com.todotic.bookstoreapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AccountService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public UserProfileDTO signup(SignupFormDTO signupFormDTO) {
        boolean emailAlreadyExists = userRepository.existsByEmail(signupFormDTO.getEmail());

        if (emailAlreadyExists) {
            throw new BadRequestException("El email ya está siendo usado por otro usuario.");
        }

        User user = modelMapper.map(signupFormDTO, User.class);
        user.setPassword(passwordEncoder.encode(signupFormDTO.getPassword()));
        user.setRole(User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return modelMapper.map(user, UserProfileDTO.class);
    }

    public UserProfileDTO findByEmail(String email) {
        User user = userRepository
                .findOneByEmail(email)
                .orElseThrow(ResourceNotFoundException::new);

        return modelMapper.map(user, UserProfileDTO.class);
    }

}
