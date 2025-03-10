package com.example.costumers_emails.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscribers")
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String source;

    private Subscriber() {
    }

    public Subscriber(String email, String ipAddress, String source){
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.ipAddress = ipAddress;
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }


    public String getSource(){
        return source;
    }

}
