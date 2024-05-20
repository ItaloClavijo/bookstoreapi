package com.todotic.bookstoreapi.controller;

import com.todotic.bookstoreapi.exception.ResourceNotFoundException;
import com.todotic.bookstoreapi.model.dto.UserFormDTO;
import com.todotic.bookstoreapi.model.entity.User;
import com.todotic.bookstoreapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private UserRepository userRepository;

    @GetMapping
    Page<User> paginate(@PageableDefault(sort = "fullName", size = 5) Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping
    User create(@RequestBody @Validated UserFormDTO dto) {
        return null;
    }

    @GetMapping("/{id}")
    User get(@PathVariable Integer id) {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping("/{id}")
    User update(
            @PathVariable Integer id,
            @RequestBody @Validated UserFormDTO dto
    ) {
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        userRepository.delete(user);
    }

}
