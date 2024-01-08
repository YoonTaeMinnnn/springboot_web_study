package com.example.consumer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class FailedEvent {

    @Id
    @GeneratedValue
    @Column(name = "failed_event_id")
    private Long id;

    private Long userId;

    public FailedEvent() {

    }

    public FailedEvent(Long userId) {
        this.userId = userId;
    }
}
