package com.todotic.bookstoreapi.model.dto;

import com.todotic.bookstoreapi.model.entity.User;
import lombok.Data;

@Data
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private User.Role role;
}
