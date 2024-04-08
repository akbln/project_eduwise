package com.edusenior.project.services;

import com.edusenior.project.dataTransferObjects.JwtDTO;
import com.edusenior.project.dataTransferObjects.LoginDTO;

import javax.security.auth.login.LoginException;

public interface LoginService {
    // Returns a JWT token
    public JwtDTO login(LoginDTO loginDTO) throws LoginException;
//    public void logout();
}
