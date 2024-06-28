package com.studentservice.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.studentservice.dto.StudentDTO;
import com.studentservice.dto.StudentResponse;
import com.studentservice.helper.OTPGenerator;
import com.studentservice.helper.RegistrationNoGenerator;
import com.studentservice.models.Student;
import com.studentservice.services.EmailService;
import com.studentservice.services.StudentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * * ===========================================================================
 * * ======================== Module : StudentController =======================
 * * ======================== Created By : Umesh Kumar =========================
 * * ======================== Created On : 05-06-2024 ==========================
 * * ===========================================================================
 * * | Code Status : On
 */

@RestController
@CrossOrigin
@Validated
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private EmailService emailService;

    private static String otp = null;

    /*
     * Add a New Student
     * API : http://localhost:8080/api/student/add
     * 
     * @throws IOException
     */
    @PostMapping("/add")
    public ResponseEntity<Map<Object, Object>> addStudent(
            @RequestParam("firstName") @Valid @NotBlank(message = "First name is required") @Size(max = 50, message = "First name cannot be longer than 50 characters") String firstName,
            @RequestParam(value = "middleName", required = false) @Valid @Size(max = 50, message = "Middle name cannot be longer than 50 characters") String middleName,
            @RequestParam("lastName") @Valid @NotBlank(message = "Last name is required") @Size(max = 50, message = "Last name cannot be longer than 50 characters") String lastName,
            @RequestParam("dob") @Valid @NotNull(message = "Date of birth is required") @Past(message = "Date of birth must be in the past") LocalDate dob,
            @RequestParam("gender") @Valid @NotBlank(message = "Gender is required") @Pattern(regexp = "male|female|other", message = "Gender must be 'male', 'female', or 'other'") String gender,
            @RequestParam("streetAddress") @Valid @NotBlank(message = "Street address is required") @Size(max = 100, message = "Street address cannot be longer than 100 characters") String streetAddress,
            @RequestParam("city") @Valid @NotNull(message = "City is required") Long cityId,
            @RequestParam("state") @Valid @NotNull(message = "State is required") Long stateId,
            @RequestParam("zipcode") @Valid @NotBlank(message = "Zipcode is required") @Pattern(regexp = "\\d{6}", message = "Zipcode must be a 6-digit number") String zipCode,
            @RequestParam("email") @Valid @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
            @RequestParam("phone") @Valid @NotBlank(message = "Phone number is required") @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number") String phone,
            @RequestParam(value = "image", required = false) @Valid MultipartFile image,
            @RequestParam("otp") String reqOTP) throws IOException {

        if (otp != null && !otp.equals(reqOTP)) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        // Student checkRegNoExisting = studentService.searchByRegisterNo(regNo);
        // if (checkRegNoExisting != null)
        // throw new IllegalStateException("Register Number already exists !!");

        // Student checkEmailExisting = studentService.searchByEmailId(email);

        // if (checkEmailExisting != null)
        // throw new IllegalStateException("Email already exists !!");

        // Create Student entity
        StudentDTO student = StudentDTO.builder()
                .regNo(generateRegNo())
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .dob(dob)
                .gender(gender)
                .streetAddress(streetAddress)
                .cityId(cityId)
                .stateId(stateId)
                .zipcode(zipCode)
                .email(email)
                .phone(phone)
                .image(image)
                .build();

        StudentResponse data = studentService.saveStudent(student);

        Map<Object, Object> resp = new HashMap<>();
        resp.put("data", data);
        resp.put("message", "Student Added Successfully");
        resp.put("status", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /**
     * * Get All Student Details
     * * API : http://localhost:8080/api/student/get-list
     * 
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("/get-list")
    public ResponseEntity<Map<Object, Object>> getStudentList(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit) {

        Pageable pageable = PageRequest.of(offset, limit, Sort.by("id").descending());
        Page<StudentResponse> studentPage = studentService.getStudentList(pageable);

        List<StudentResponse> categoryList = studentPage.getContent();

        Map<Object, Object> resp = new HashMap<>();
        resp.put("message", "Retrieve All Courses");
        resp.put("data", categoryList);
        resp.put("currentPage", studentPage.getNumber());
        resp.put("totalItems", studentPage.getTotalElements());
        resp.put("totalPages", studentPage.getTotalPages());
        resp.put("status", true);

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping("/initiate-registration")
    public ResponseEntity<Map<Object, Object>> generateOTP(
            // @RequestParam(value="regNo", required = false) @Valid String regNo,
            @RequestParam("firstName") @Valid @NotBlank(message = "First name is required") @Size(max = 50, message = "First name cannot be longer than 50 characters") String firstName,
            @RequestParam(value = "middleName", required = false) @Valid @Size(max = 50, message = "Middle name cannot be longer than 50 characters") String middleName,
            @RequestParam("lastName") @Valid @NotBlank(message = "Last name is required") @Size(max = 50, message = "Last name cannot be longer than 50 characters") String lastName,
            @RequestParam("dob") @Valid @NotNull(message = "Date of birth is required") @Past(message = "Date of birth must be in the past") LocalDate dob,
            @RequestParam("gender") @Valid @NotBlank(message = "Gender is required") @Pattern(regexp = "male|female|other", message = "Gender must be 'male', 'female', or 'other'") String gender,
            @RequestParam("streetAddress") @Valid @NotBlank(message = "Street address is required") @Size(max = 100, message = "Street address cannot be longer than 100 characters") String streetAddress,
            @RequestParam("city") @Valid @NotNull(message = "City is required") Long cityId,
            @RequestParam("state") @Valid @NotNull(message = "State is required") Long stateId,
            @RequestParam("zipcode") @Valid @NotBlank(message = "Zipcode is required") @Pattern(regexp = "\\d{6}", message = "Zipcode must be a 6-digit number") String zipCode,
            @RequestParam("email") @Valid @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
            @RequestParam("phone") @Valid @NotBlank(message = "Phone number is required") @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number") String phone,
            @RequestParam(value = "image", required = false) @Valid MultipartFile image) throws IOException {

        // Student checkRegNoExisting = studentService.searchByRegisterNo(regNo);
        // if (checkRegNoExisting != null)
        // throw new IllegalStateException("Register Number already exists !!");

        Student checkEmailExisting = studentService.searchByEmailId(email);

        if (checkEmailExisting != null)
            throw new IllegalStateException("Email already exists !!");

        otp = OTPGenerator.generateOTP();
        emailService.sendOtpEmail(email, otp);

        Map<Object, Object> resp = new HashMap<>();
        // resp.put("data", data);
        resp.put("message", "OTP sent to your email, Check you email !!");
        resp.put("status", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    public static String generateRegNo() {
        return RegistrationNoGenerator.generateUniqueRegistrationNumber();
    }

}
