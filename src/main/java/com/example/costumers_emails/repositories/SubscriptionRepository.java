package com.example.costumers_emails.repositories;

import com.example.costumers_emails.models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SubscriptionRepository extends JpaRepository<Subscriber, Long> {

    boolean existsByEmail(String email);
}
