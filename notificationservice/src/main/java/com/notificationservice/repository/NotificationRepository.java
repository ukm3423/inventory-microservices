package com.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notificationservice.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
}
