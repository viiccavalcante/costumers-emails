package com.example.costumers_emails.controllers;

import com.example.costumers_emails.models.Subscriber;
import com.example.costumers_emails.repositories.SubscriptionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Test
    void testShowForm() {
        String view = subscriptionController.showForm(model);
        assertEquals("home", view);
        verify(model).addAttribute(eq("subscriber"), any(Subscriber.class));
    }

    @Test
    void testSubscribe_Success() {
        Subscriber subscriber = new Subscriber("user@test.com", "", "");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(subscriptionRepository.existsByEmail(subscriber.getEmail())).thenReturn(false);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        String view = subscriptionController.subscribe(subscriber, bindingResult, model, request);
        assertEquals("home", view);
        verify(subscriptionRepository).save(any(Subscriber.class));
        verify(model).addAttribute("success", "Success!");
    }

    @Test
    void testSubscribe_DuplicateEmail() {
        Subscriber subscriber = new Subscriber("user@test.com", "", "");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(subscriptionRepository.existsByEmail(subscriber.getEmail())).thenReturn(true);

        String view = subscriptionController.subscribe(subscriber, bindingResult, model, request);
        assertEquals("home", view);
        verify(model).addAttribute("error", "Ops! You already registered.");
        verify(subscriptionRepository, never()).save(any(Subscriber.class));
    }

    @Test
    void testSubscribe_ValidationError() {
        Subscriber subscriber = new Subscriber("", "", "");
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = subscriptionController.subscribe(subscriber, bindingResult, model, request);
        assertEquals("home", view);
        verify(subscriptionRepository, never()).save(any(Subscriber.class));
    }

    @Test
    void testGetAllSubscribers() {
        List<Subscriber> subscribers = Arrays.asList(
                new Subscriber("user1@test.com", "127.0.0.1", "landing-page"),
                new Subscriber("user2@test.com", "127.0.0.2", "landing-page")
        );
        when(subscriptionRepository.findAll()).thenReturn(subscribers);

        String view = subscriptionController.getAll(model);
        assertEquals("all-subscribers", view);
        verify(model).addAttribute("subscribers", subscribers);
    }
}

