package com.hospitalMgt.patiencemgt.dto;

import com.hospitalMgt.patiencemgt.validation.passwordValidation.Password;
import com.hospitalMgt.patiencemgt.validation.phoneNumberValidation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignupDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    @Password
    private String password;

    @NotBlank(message = "Phone number is required")
    @ValidPhoneNumber
    private String phoneNumber;
}
