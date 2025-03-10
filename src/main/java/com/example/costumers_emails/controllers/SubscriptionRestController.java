package com.example.costumers_emails.controllers;

import com.example.costumers_emails.dtos.SubscriptionRequestDTO;
import com.example.costumers_emails.models.Subscriber;
import com.example.costumers_emails.repositories.SubscriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SubscriptionRestController {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionRestController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriptionRequestDTO subscriptionDTO, HttpServletRequest httpRequest) {
        String email = subscriptionDTO.getEmail();

        if (subscriptionRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already registered"));
        }

        Subscriber subscriber = new Subscriber(email, httpRequest.getRemoteAddr(), "api");
        subscriptionRepository.save(subscriber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Subscription successful", "subscriber", subscriber));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        return ResponseEntity.ok(subscriptionRepository.findAll());
    }
}

