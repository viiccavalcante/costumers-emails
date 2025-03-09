package com.example.costumers_emails.repository;

import com.example.costumers_emails.model.Subscriber;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class SubscriptionRepository {
    private final Set<Subscriber> subscribers = new HashSet<>();

    public boolean addSubscriber(Subscriber subscriber) {
        return subscribers.add(subscriber);
    }

    public boolean existsByEmail(String email) {
        return subscribers.stream().anyMatch(sub -> sub.getEmail().equals(email));
    }

    public Set<Subscriber> getAllSubscribers() {
        return Collections.unmodifiableSet(subscribers);
    }
}
