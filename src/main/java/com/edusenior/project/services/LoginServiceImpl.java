package com.edusenior.project.services;

import com.edusenior.project.dataAccessObjects.student.StudentDAO;
import com.edusenior.project.dataAccessObjects.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.CredentialsDTO;
import com.edusenior.project.dataTransferObjects.JwtDTO;
import com.edusenior.project.dataTransferObjects.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edusenior.project.Utility.JWTManager;
import com.edusenior.project.Utility.BcryptPasswordEncoder;

import javax.security.auth.login.LoginException;


@Service
public class LoginServiceImpl implements LoginService{
    private BcryptPasswordEncoder encoder;
    private JWTManager jwtManager;
    private TeacherDAO teacherDAO;
    private StudentDAO studentDAO;

    @Autowired
    public LoginServiceImpl(JWTManager jwtManager, BcryptPasswordEncoder encoder, TeacherDAO teacherDAO, StudentDAO studentDAO) {
        this.jwtManager = jwtManager;
        this.encoder = encoder;
        this.teacherDAO = teacherDAO;
        this.studentDAO = studentDAO;
    }

    public JwtDTO login (LoginDTO loginDTO) throws LoginException {
        final String email = loginDTO.getEmail();
        final String password = loginDTO.getPassword();
        final String role = loginDTO.getRole();
        CredentialsDTO cDTO = null;
        switch (role){
            case "student":
                cDTO = studentDAO.fetchCredentials(email);
                if(cDTO == null){
                    throw new LoginException("Invalid credentials");
                }
                if (!(encoder.passwordEncoder().matches(password, cDTO.getHash()))){
                    throw new LoginException("Invalid credentials");
                }
                return new JwtDTO(jwtManager.generateToken(email, role));
            case "teacher":
                cDTO = teacherDAO.fetchCredentials(email);
                if(cDTO == null){
                    throw new LoginException("Invalid credentials");
                }
                if (!(encoder.passwordEncoder().matches(password, cDTO.getHash()))){
                    throw new LoginException("Invalid credentials");
                }
                return new JwtDTO(jwtManager.generateToken(email, role));
            default:
                throw new LoginException("Invalid role");
        }
    }
}
