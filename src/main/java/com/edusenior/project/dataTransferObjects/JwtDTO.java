package com.edusenior.project.dataTransferObjects;

import com.edusenior.project.Utility.ServerResponse;

public class JwtDTO extends ServerResponse{
    private String token;

    public JwtDTO(String token) {
        super("success", "Token generated successfully");
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
