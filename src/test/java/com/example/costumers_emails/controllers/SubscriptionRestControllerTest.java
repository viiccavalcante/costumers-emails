package com.example.costumers_emails.controllers;

import com.example.costumers_emails.dtos.SubscriptionRequestDTO;
import com.example.costumers_emails.models.Subscriber;
import com.example.costumers_emails.repositories.SubscriptionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionRestControllerTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private SubscriptionRestController subscriptionRestController;

    @Test
    void testSubscribe_Success() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO("user@test.com");
        when(subscriptionRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        ResponseEntity<?> response = subscriptionRestController.subscribe(dto, request);
        assertEquals(CREATED, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("message"));
        verify(subscriptionRepository).save(any(Subscriber.class));
    }

    @Test
    void testSubscribe_DuplicateEmail() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO("user@test.com");
        when(subscriptionRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        ResponseEntity<?> response = subscriptionRestController.subscribe(dto, request);
        assertEquals(CONFLICT, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
        verify(subscriptionRepository, never()).save(any(Subscriber.class));
    }

    @Test
    void testGetAllSubscribers() {
        List<Subscriber> subscribers = List.of(
                new Subscriber("user1@test.com", "127.0.0.1", "api"),
                new Subscriber("user2@test.com", "127.0.0.2", "api")
        );
        when(subscriptionRepository.findAll()).thenReturn(subscribers);

        ResponseEntity<List<Subscriber>> response = subscriptionRestController.getAllSubscribers();
        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllSubscribers_EmptyList() {
        when(subscriptionRepository.findAll()).thenReturn(List.of());

        ResponseEntity<List<Subscriber>> response = subscriptionRestController.getAllSubscribers();
        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
