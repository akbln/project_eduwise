package com.edusenior.project.services.teacher;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Mappings.TeacherMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import com.edusenior.project.dataTransferObjects.QuestionDTO;
import com.edusenior.project.entities.Question;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.Teacher;
import com.edusenior.project.services.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeacherServiceImpl implements TeacherService{

    private TeacherDAO teacherDAO;
    private BcryptPasswordEncoder encoder;
    private CredentialsJpaRepository credentialsJpaRepository;
    private QuestionService qService;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, BcryptPasswordEncoder encoder, CredentialsJpaRepository credentialsJpaRepository, QuestionService qService) {
        this.teacherDAO = teacherDAO;
        this.encoder = encoder;
        this.credentialsJpaRepository = credentialsJpaRepository;
        this.qService = qService;
    }

    @Transactional
    public ResponseEntity<ServerResponse> registerTeacher(NewTeacherDTO tDTO) {
        if (credentialsJpaRepository.existsByEmail(tDTO.getEmail())){
            throw new DuplicateEntryException("Email already in use");
        }
        Teacher t = new Teacher();
        t = TeacherMapper.INSTANCE.newTeacherDtoToTeacher(tDTO);

        Credentials c = new Credentials(true);
        c.setEmail(tDTO.getEmail());
        c.setHash(encoder.passwordEncoder().encode(tDTO.getPassword()));
        c.setRole("teacher");
        c.setUser(t);

        credentialsJpaRepository.save(c);
        ServerResponse response = new ServerResponse("success",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public ResponseEntity<ServerResponse> uploadQuestion (QuestionDTO questionDTO){
        return qService.uploadQuestion(questionDTO);
    }
}
