package com.edusenior.project.services.student;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Mappings.StudentMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataAccessObjects.student.StudentDAO;
import com.edusenior.project.RestControllers.Student.StudentNotFoundException;
import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private StudentDAO studentDAO;
    private BcryptPasswordEncoder encoder;


    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO,BcryptPasswordEncoder encoder) {
        this.studentDAO = studentDAO;
        this.encoder = encoder;
    }

    @Override
    public Student fetchStudent(String id){
        Student s = studentDAO.fetchStudent(id);
        if(s == null){
            throw new StudentNotFoundException("Student Not Found");
        }
        return s;
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> registerStudent(NewStudentDTO sDTO) throws DuplicateEntryException {
        if (studentDAO.checkExistingEmail(sDTO.getEmail())){
            throw new DuplicateEntryException("Email already in use");
        }
        Student s = new Student();
        s = Mappers.getMapper(StudentMapper.class).newStudentDtoToStudent(sDTO);
        s.setHash(encoder.passwordEncoder().encode(sDTO.getPassword()));

        studentDAO.createStudent(s);
        ServerResponse response = new ServerResponse("success",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Student fetchStudentByEmail(String email){
        Student s = studentDAO.fetchStudentByEmail(email);
        if(s == null){
            throw new StudentNotFoundException("Student Not Found");
        }
        return s;
    }
}
