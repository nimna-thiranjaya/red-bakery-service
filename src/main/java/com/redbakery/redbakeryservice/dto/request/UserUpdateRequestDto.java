package com.redbakery.redbakeryservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateRequestDto {
    @NotEmpty(message = "First Name is required")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    private String phoneNumber;

    private String profileImage;

    @NotEmpty(message = "Role is required")
    private String role;
}
