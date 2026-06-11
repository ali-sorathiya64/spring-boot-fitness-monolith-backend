package com.example.jpademo.dto;


import com.example.jpademo.model.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank (message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password must required")
    private String password;

    private String firstName;
    private String lastName;
    private UserRoles roles;


}
