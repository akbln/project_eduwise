package com.edusenior.project.services;

import com.edusenior.project.dataAccessObjects.credentials.CredentialsJpaRepository;
import com.edusenior.project.dataTransferObjects.JwtDTO;
import com.edusenior.project.dataTransferObjects.LoginDTO;
import com.edusenior.project.entities.Users.Credentials;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edusenior.project.Utility.JWTManager;
import com.edusenior.project.Utility.BcryptPasswordEncoder;

import javax.security.auth.login.LoginException;
import java.sql.Timestamp;


@Service
public class LoginServiceImpl implements LoginService {
    // 1 Hour
    private final long timeoutInterval = 3600000;

    private BcryptPasswordEncoder encoder;
    private JWTManager jwtManager;
    private CredentialsJpaRepository credentialsJpaRepository;

    @Autowired
    public LoginServiceImpl(JWTManager jwtManager, BcryptPasswordEncoder encoder, CredentialsJpaRepository credentialsJpaRepository) {
        this.jwtManager = jwtManager;
        this.encoder = encoder;
        this.credentialsJpaRepository = credentialsJpaRepository;
    }
    @Transactional
    public JwtDTO login(LoginDTO loginDTO) throws LoginException {
        final String email = loginDTO.getEmail();

        Credentials credentials = credentialsJpaRepository.findByEmail(email);
        if (credentials == null){
            throw new LoginException("Invalid credentials");
        }
        if(isLockedOut(credentials)){
            throw new LoginException("You are not allowed to login at this time");
        }
        if (!validatePasswordAndUpdateFailures(credentials,loginDTO)){
            throw new LoginException("Invalid Credentials");
        }
        else{
            resetFailedAttempts(credentials);
        }

        final String id = credentials.getId();
        final String userRole = credentials.getRole();
        return new JwtDTO(jwtManager.generateToken(id, userRole));
    }
    private void resetFailedAttempts(Credentials credentials) {
        credentials.setFailed(0);
        credentialsJpaRepository.save(credentials);
    }
    private boolean validatePasswordAndUpdateFailures(Credentials credentials, LoginDTO loginDTO)  {
        if (!(encoder.passwordEncoder().matches(loginDTO.getPassword(), credentials.getHash()))) {
            int failed = credentials.addToFailed();
            if (failed >=10){
                credentials.setFailed(0);
                credentials.setLockout(new Timestamp(System.currentTimeMillis() + timeoutInterval));
            }
            credentialsJpaRepository.save(credentials);
            return false;
        }
        return true;
    }
    private boolean isLockedOut(Credentials credentials) throws LoginException {
        final Timestamp lockout = credentials.getLockout();
        return lockout != null && lockout.after(new Timestamp(System.currentTimeMillis()));
    }
}

