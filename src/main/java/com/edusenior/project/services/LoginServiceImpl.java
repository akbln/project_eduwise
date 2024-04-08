package com.edusenior.project.services;

import com.edusenior.project.dataAccessObjects.credentials.CredentialsDAO;
import com.edusenior.project.dataTransferObjects.JwtDTO;
import com.edusenior.project.dataTransferObjects.LoginDTO;
import com.edusenior.project.entities.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.edusenior.project.Utility.JWTManager;
import com.edusenior.project.Utility.BcryptPasswordEncoder;

import javax.security.auth.login.LoginException;


@Service
public class LoginServiceImpl implements LoginService{
    private BcryptPasswordEncoder encoder;
    private JWTManager jwtManager;
    private CredentialsDAO credentialsDAO;

    @Autowired
    public LoginServiceImpl(JWTManager jwtManager, BcryptPasswordEncoder encoder, CredentialsDAO credentialsDAO) {
        this.jwtManager = jwtManager;
        this.encoder = encoder;
        this.credentialsDAO = credentialsDAO;
    }

    public JwtDTO login (LoginDTO loginDTO) throws LoginException {
        final String email = loginDTO.getEmail();
        final String password = loginDTO.getPassword();

        Credentials credential = credentialsDAO.fetchCredential(email);
        if (credential == null){
            throw new LoginException("Invalid credentials DAO");
        }
        final String passwordHash = credential.getHash();
        final String role = credential.getRole();
        if(passwordHash == null){
            throw new LoginException("Invalid credentials");
        }
        if (!(encoder.passwordEncoder().matches(password, passwordHash))){
            throw new LoginException("Invalid credentials");
        }

        return new JwtDTO(jwtManager.generateToken(email, role));

    }
}
