package com.studentservice.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.basedomain.dto.Role;
import com.basedomain.dto.StudentEvent;
import com.basedomain.dto.UserDTO;
import com.studentservice.dto.StudentDTO;
import com.studentservice.dto.StudentResponse;
import com.studentservice.handlers.StudentMapper;
import com.studentservice.kafka.StudentProducer;
import com.studentservice.models.City;
import com.studentservice.models.State;
import com.studentservice.models.Student;
import com.studentservice.repository.CityRepository;
import com.studentservice.repository.StateRepository;
import com.studentservice.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository stdRepo;

    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private StateRepository stateRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private StudentProducer stdProducer;

    public StudentResponse saveStudent(StudentDTO req) throws IOException {
        String photoFileName = null;
        if (req.getImage() != null) {
            photoFileName = fileStorageService.storeFile(req.getImage());
        }

        City city = cityRepo.findById(req.getCityId()).get();
        State state = stateRepo.findById(req.getStateId()).get();

        Student std = Student.builder()
                .regNo(req.getRegNo())
                .firstName(req.getFirstName())
                .middleName(req.getMiddleName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .phone(Long.parseLong(req.getPhone()))
                .streetAddress(req.getStreetAddress())
                .cityName(city.getCity())
                .stateName(state.getState())
                .zipCode(Long.parseLong(req.getZipcode()))
                .gender(req.getGender())
                .imagePath(photoFileName)
                .dob(req.getDob())
                .build();

        System.out.println(std);
        stdRepo.save(std);

        StudentResponse resp = StudentResponse.builder()
                .id(std.getId())
                .regNo(std.getRegNo())
                .fullname(std.getFirstName() + " " + std.getMiddleName() + " " + std.getLastName())
                .email(std.getEmail())
                .phoneNumber(std.getPhone())
                .dob(std.getDob())
                .build();

        UserDTO event = UserDTO.builder()
                .id(std.getId())
                .fullname(std.getFirstName())
                .email(std.getEmail())
                .dob(std.getDob())
                .phoneNo(std.getPhone())
                .build();
        stdProducer.sendMessage(event);
        return resp;
    }

    public Student searchByRegisterNo(String registerNo) {
        Student student = stdRepo.findByRegNo(registerNo);
        return student;
    }

    public Student searchByEmailId(String email) {
        return stdRepo.findByEmail(email);

    }

    public Page<StudentResponse> getStudentList(Pageable pageable) {
        Page<Student> studentPage = stdRepo.findAll(pageable);
        return StudentMapper.toStudentResponsePage(studentPage);
    }
}
