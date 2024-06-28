package com.studentservice.helper;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.studentservice.repository.StudentRepository;

@Component
public class RegistrationNoGenerator {

    private static StudentRepository stdRepo;

    @Autowired
    public void setStudentRepository(StudentRepository stdRepo) {
        RegistrationNoGenerator.stdRepo = stdRepo;
    }
    
    public static String generateUniqueRegistrationNumber() {
        String registrationNumber;
        do {
            registrationNumber = generateRandomRegistrationNumber();
        } while (!isRegistrationNumberUnique(registrationNumber));
        return registrationNumber;
    }

    private static String generateRandomRegistrationNumber() {
        Random random = new Random();
        // You can adjust the length of the random number as needed
        String randomNumber = String.format("%010d", random.nextInt(1000000000));
        return "REG" + randomNumber;
    }

    private static boolean isRegistrationNumberUnique(String registrationNumber) {
        return stdRepo.findByRegNo(registrationNumber) == null;
    }
}
