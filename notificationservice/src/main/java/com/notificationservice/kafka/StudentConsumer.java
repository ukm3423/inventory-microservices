package com.notificationservice.kafka;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basedomain.dto.StudentEvent;
import com.notificationservice.models.Notification;
import com.notificationservice.repository.NotificationRepository;
import com.notificationservice.services.EmailService;

@Service
@Transactional
public class StudentConsumer {

        @Autowired
        private NotificationRepository notiRepo;

        @Autowired
        private UserResponseProducer userResponse;

        @Autowired
        private EmailService emailService;

        private static final Logger LOGGER = LoggerFactory.getLogger(StudentConsumer.class);

        @KafkaListener(topics = "${spring.kafka.notify-topic.name}", groupId = "${spring.kafka.consumer.group-id}")
        public void consume(StudentEvent event) {

                LOGGER.info(String.format("Notification-Service received student event => %s ", event.toString()));

                /**
                 * SAVE THE DATA TO THE DATABASE
                 */
                Notification notification = Notification.builder()
                                .name(event.getFullname())
                                .email(event.getEmail())
                                .phone(event.getPhoneNumber())
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();

                notiRepo.save(notification); // SAVE DATA TO THE DATABASE

                // Send welcome email to the user...
                String emailText = String.format(
                                "Dear %s,\n\nYou have successfully completed your registration process.\n"
                                                +
                                                "Please login with your Registration Number and password to complete your application form.\n\n"
                                                +
                                                "Your Registration No : %s \nYour Password : your date of birth \n\nBest regards,\nThe Team",
                                notification.getName().toUpperCase(), notification.getEmail());

                emailService.sendEmail(notification.getEmail(), "Welcome to Learnify!", emailText);

                StudentEvent user = StudentEvent.builder()
                                .fullname(notification.getName())
                                .email(notification.getEmail())
                                .phoneNumber(notification.getPhone())
                                .message("Welcome Message is Send to the User Eamil...")
                                .build();

                // send response the to user-service...
                // userResponse.sendResponse(user);

        }

}
