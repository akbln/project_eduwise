package com.edusenior.project.services.student;

import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.dataAccessObjects.credentials.CredentialsDAO;
import com.edusenior.project.dataAccessObjects.student.StudentDAO;
import com.edusenior.project.RestControllers.Student.StudentNotFoundException;
import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Credentials;
import com.edusenior.project.entities.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private StudentDAO studentDAO;
    private CredentialsDAO credentialsDAO;
    private BcryptPasswordEncoder encoder;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO,CredentialsDAO credentialsDAO,BcryptPasswordEncoder encoder) {
        this.studentDAO = studentDAO;
        this.credentialsDAO = credentialsDAO;
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
    public void registerStudent(NewStudentDTO sDTO) {
//        final String id = studentDAO.createStudent(name,age,gender,level);
        Student s = new Student();
        s.setName(sDTO.getName());
        s.setAge(sDTO.getAge());
        s.setGender(sDTO.getGender().charAt(0));
        s.setLevel(sDTO.getLevel());
        Credentials credentials = new Credentials(sDTO.getEmail(),
                encoder.passwordEncoder().encode(sDTO.getPassword()),
                0,new java.sql.Timestamp(System.currentTimeMillis()));
        s.setCredentials(credentials);
        credentials.setStudent(s);
        studentDAO.createStudent(s);
    }

    public Student fetchStudentByEmail(String email){
        Student s = studentDAO.fetchStudentByEmail(email);
        if(s == null){
            throw new StudentNotFoundException("Student Not Found");
        }
        return s;
    }
}
