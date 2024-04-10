package com.edusenior.project.dataTransferObjects;

import com.edusenior.project.Utility.ServerResponse;

import java.util.ArrayList;

public class JwtDTO extends ServerResponse{
    private String token;

    public JwtDTO(String token) {
        super("success", new ArrayList<String>());
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
