package com.example.costumers_emails.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SubscriptionRequestDTO {
    @NotBlank(message = "The email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    public String getEmail() { return email; }
}
