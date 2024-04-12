package com.edusenior.project.services;

import com.edusenior.project.dataAccessObjects.UserDAO;
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
import java.util.Map;


@Service
public class LoginServiceImpl implements LoginService {
    private BcryptPasswordEncoder encoder;
    private JWTManager jwtManager;
    private Map<String, UserDAO> userDaos;

    @Autowired
    public LoginServiceImpl(JWTManager jwtManager, BcryptPasswordEncoder encoder, TeacherDAO teacherDAO, StudentDAO studentDAO) {
        this.jwtManager = jwtManager;
        this.encoder = encoder;
        this.userDaos = Map.of(
                "student", studentDAO,
                "teacher", teacherDAO
        );
    }

    public JwtDTO login(LoginDTO loginDTO) throws LoginException {
        String email = loginDTO.getEmail();
        String role = loginDTO.getRole();

        UserDAO userDAO = userDaos.get(role);
        if (userDAO == null) {
            throw new LoginException("Invalid role");
        }

        CredentialsDTO cDTO = userDAO.fetchCredentials(email);
        if (cDTO == null || !(encoder.passwordEncoder().matches(loginDTO.getPassword(), cDTO.getHash()))) {
            throw new LoginException("Invalid credentials");
        }

        return new JwtDTO(jwtManager.generateToken(email, role));
    }
}

