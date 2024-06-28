package com.notificationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.basedomain.dto.StudentEvent;
import com.notificationservice.services.EmailService;

public class EmailController {
    
    @Autowired
    private EmailService emailService;

    /**
     * Subject : Sending email
     * API : http://localhost:5004/emailservice/api/send-mail
     * Request Body : {
     *      "fullname": "Dipanshu",
     *      "email": "dipanshuraj9723@gmail.com",
     *      "phone": 6201033951,
     *      "message": "Welcome to the hell"
     * }
     */
    @PostMapping("/send-mail")
    public StudentEvent sendEmail(@RequestBody StudentEvent user) {

        try {
            // Send welcome email to the user
            String emailText = String.format(
                    "Dear %s,\n\nWelcome to our service! We're glad to have you on board.\n\nBest regards,\nThe Team",
                    user.getFullname());
            emailService.sendEmail(user.getEmail(), "Welcome to Our Service", emailText);
        } catch (Exception e) {

        }

        return user;
    }

}
