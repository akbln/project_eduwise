package com.edusenior.project.services;

import com.edusenior.project.dataAccessObjects.credentials.CredentialsDAO;
import com.edusenior.project.dataAccessObjects.student.StudentDAO;
import com.edusenior.project.dataAccessObjects.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.JwtDTO;
import com.edusenior.project.dataTransferObjects.LoginDTO;
import com.edusenior.project.entities.Credentials;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edusenior.project.Utility.JWTManager;
import com.edusenior.project.Utility.BcryptPasswordEncoder;

import javax.security.auth.login.LoginException;
import java.sql.Timestamp;


@Service
public class LoginServiceImpl implements LoginService {
    private BcryptPasswordEncoder encoder;
    private JWTManager jwtManager;
    private CredentialsDAO credentialsDAO;

    @Autowired
    public LoginServiceImpl(JWTManager jwtManager, BcryptPasswordEncoder encoder, TeacherDAO teacherDAO, StudentDAO studentDAO,CredentialsDAO credentialsDAO) {
        this.jwtManager = jwtManager;
        this.encoder = encoder;
        this.credentialsDAO = credentialsDAO;
    }
    @Transactional
    public JwtDTO login(LoginDTO loginDTO) throws LoginException {
        final String email = loginDTO.getEmail();
        String role = loginDTO.getRole();

        if (!role.equals("teacher") && !role.equals("student")) {
            throw new LoginException("Invalid role");
        }

        Credentials credentials = credentialsDAO.getByEmail(email);
        if (credentials == null){
            throw new LoginException("Invalid credentials");
        }
        String id = credentials.getId();


        Timestamp lockout = credentials.getLockout();
        if (lockout == null){
            throw new LoginException("Error logging in, Contact System Admin");
        }
        if (lockout.after(new Timestamp(System.currentTimeMillis()))){
            throw new LoginException("You are not allowed to login at this time");
        }

        if (!(encoder.passwordEncoder().matches(loginDTO.getPassword(), credentials.getHash()))) {
            int failed = credentials.addToFailed();
            if (failed >=10){
                credentials.setFailed(0);
                credentials.setLockout(new Timestamp(System.currentTimeMillis() + 3600000));
            }
            credentialsDAO.persistChange(credentials);
            throw new LoginException("Invalid credentials");
        }

        return new JwtDTO(jwtManager.generateToken(id, role));
    }
}

