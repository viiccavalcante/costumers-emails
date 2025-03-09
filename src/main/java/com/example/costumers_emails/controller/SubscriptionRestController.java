package com.example.costumers_emails.controller;

import com.example.costumers_emails.dto.SubscriptionRequestDTO;
import com.example.costumers_emails.model.Subscriber;
import com.example.costumers_emails.repository.SubscriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.Set;

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
        subscriptionRepository.addSubscriber(subscriber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Subscription successful", "subscriber", subscriber));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Set<Subscriber>> getAllSubscribers() {
        return ResponseEntity.ok(subscriptionRepository.getAllSubscribers());
    }
}

