package com.edusenior.project.RestControllers;

import com.edusenior.project.dataTransferObjects.JwtDTO;
import com.edusenior.project.dataTransferObjects.LoginDTO;
import com.edusenior.project.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO loginDTO) throws LoginException {
        JwtDTO token = loginService.login(loginDTO);
        if (token == null) {
            throw new LoginException("Invalid credentials");
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


}
