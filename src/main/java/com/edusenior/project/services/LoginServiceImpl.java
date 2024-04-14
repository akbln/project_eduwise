package com.edusenior.project.services;

import com.edusenior.project.dataAccessObjects.UserDAO;
import com.edusenior.project.dataAccessObjects.credentials.CredentialsDAO;
import com.edusenior.project.dataAccessObjects.student.StudentDAO;
import com.edusenior.project.dataAccessObjects.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.CredentialsDTO;
import com.edusenior.project.dataTransferObjects.JwtDTO;
import com.edusenior.project.dataTransferObjects.LoginDTO;
import com.edusenior.project.entities.Credentials;
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
    private CredentialsDAO credentialsDAO;

    @Autowired
    public LoginServiceImpl(JWTManager jwtManager, BcryptPasswordEncoder encoder, TeacherDAO teacherDAO, StudentDAO studentDAO,CredentialsDAO credentialsDAO) {
        this.jwtManager = jwtManager;
        this.encoder = encoder;
        this.userDaos = Map.of(
                "student", studentDAO,
                "teacher", teacherDAO
        );
        this.credentialsDAO = credentialsDAO;
    }

    public JwtDTO login(LoginDTO loginDTO) throws LoginException {
        String id = credentialsDAO.getUserIdByEmail(loginDTO.getEmail());
        String role = loginDTO.getRole();
        String email = loginDTO.getEmail();

        UserDAO userDAO = userDaos.get(role);
        if (userDAO == null) {
            throw new LoginException("Invalid role");
        }

        Credentials credentials = credentialsDAO.getByEmail(email);
        if (credentials == null || !(encoder.passwordEncoder().matches(loginDTO.getPassword(), credentials.getHash()))) {
            throw new LoginException("Invalid credentials");
        }

        return new JwtDTO(jwtManager.generateToken(id, role));
    }
}

