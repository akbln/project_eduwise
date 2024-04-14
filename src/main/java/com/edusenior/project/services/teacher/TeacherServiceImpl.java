package com.edusenior.project.services.teacher;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Mappings.TeacherMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataAccessObjects.credentials.CredentialsDAO;
import com.edusenior.project.dataAccessObjects.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import com.edusenior.project.entities.Credentials;
import com.edusenior.project.entities.Teacher;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeacherServiceImpl implements TeacherService{

    private TeacherDAO teacherDAO;
    private BcryptPasswordEncoder encoder;
    private CredentialsDAO credentialsDAO;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, BcryptPasswordEncoder encoder, CredentialsDAO credentialsDAO) {
        this.teacherDAO = teacherDAO;
        this.encoder = encoder;
        this.credentialsDAO = credentialsDAO;
    }

    @Transactional
    public ResponseEntity<ServerResponse> registerTeacher(NewTeacherDTO tDTO) {
        if (credentialsDAO.checkIfEmailExists(tDTO.getEmail())){
            throw new DuplicateEntryException("Email already in use");
        }
        Teacher t = new Teacher();
        t = Mappers.getMapper(TeacherMapper.class).newTeacherDtoToTeacher(tDTO);

        Credentials c = new Credentials(true);
        c.setEmail(tDTO.getEmail());
        c.setHash(encoder.passwordEncoder().encode(tDTO.getPassword()));
        c.setUser(t);

        credentialsDAO.persistChange(c);
        ServerResponse response = new ServerResponse("success",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
