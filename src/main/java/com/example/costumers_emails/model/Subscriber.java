package com.example.costumers_emails.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class Subscriber {

    @NotBlank
    @Email
    public String email;

    public String ipAddress;

    public LocalDateTime createdAt;

    public String source;

    public Subscriber(String email, String ipAddress, String source){
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.ipAddress = ipAddress;
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
