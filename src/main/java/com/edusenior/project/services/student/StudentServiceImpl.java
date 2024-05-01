package com.edusenior.project.services.student;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Mappings.StudentMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.student.StudentJpaRepository;
import com.edusenior.project.RestControllers.Student.StudentNotFoundException;
import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private StudentJpaRepository studentJpaRepository;
    private BcryptPasswordEncoder encoder;
    private CredentialsJpaRepository credentialsJpaRepository;


    @Autowired
    public StudentServiceImpl(StudentJpaRepository studentJpaRepository, BcryptPasswordEncoder encoder, CredentialsJpaRepository credentialsJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
        this.encoder = encoder;
        this.credentialsJpaRepository = credentialsJpaRepository;
    }

    @Override
    public Student fetchStudent(String id){
        Optional<Student> s = studentJpaRepository.findById(id);
        if(s.isEmpty()){
            throw new StudentNotFoundException("Student Not Found");
        }
        return s.get();
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> registerStudent(NewStudentDTO sDTO) throws DuplicateEntryException {
        if (credentialsJpaRepository.existsByEmail(sDTO.getEmail())){
            throw new DuplicateEntryException("Email already in use");
        }
        Student s = new Student();
        s = StudentMapper.INSTANCE.newStudentDtoToStudent(sDTO);

        Credentials c = new Credentials(true);
        c.setEmail(sDTO.getEmail());
        c.setHash(encoder.passwordEncoder().encode(sDTO.getPassword()));
        c.setRole("student");
        c.setUser(s);

        credentialsJpaRepository.save(c);
        ServerResponse response = new ServerResponse("success",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    public Student fetchStudentByEmail(String email){
//        Student s = studentDAO.findByEmail(email);
//        if(s == null){
//            throw new StudentNotFoundException("Student Not Found");
//        }
//        return s;
//    }
}
