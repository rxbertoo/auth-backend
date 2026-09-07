package com.github.rxbertoo.auth.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegisterDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    @Size(min = 5, max = 100, message = "Email must be less than 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 100, message = "Password must be less than 100 characters")
    private String password;
}
