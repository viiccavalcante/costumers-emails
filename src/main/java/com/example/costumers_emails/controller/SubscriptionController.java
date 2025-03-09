package com.example.costumers_emails.controller;

import com.example.costumers_emails.model.Subscriber;
import com.example.costumers_emails.repository.SubscriptionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("subscriber", new Subscriber("", ""));
        return "home";
    }

    @PostMapping("/subscribe")
    public String submitForm(@Valid @ModelAttribute Subscriber subscriber, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "home";
        }

        if (subscriptionRepository.existsByEmail(subscriber.getEmail())) {
            model.addAttribute("error", "Ops! You already registered.");
            return "home";
        }

        subscriber = new Subscriber(subscriber.getEmail(), request.getRemoteAddr());
        subscriptionRepository.addSubscriber(subscriber);

        model.addAttribute("success", "Success!");
        return "home";
    }

    @GetMapping("/all-subscribers")
    public String getAll(Model model) {
        model.addAttribute("subscribers", subscriptionRepository.getAllSubscribers());
        return "all-subscribers";
    }
}
