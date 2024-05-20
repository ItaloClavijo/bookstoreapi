package com.todotic.bookstoreapi.model.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookFormDTO {
    @NotNull
    @Size(min = 3, max = 250)
    private String title;

    @NotNull
    @Pattern(regexp = "[a-z0-9-]+")
    private String slug;

    @NotBlank
    private String description;

    @NotNull
    @Min(0)
    private Float price;

    @NotBlank
    private String coverPath;

    @NotBlank
    private String filePath;
}
