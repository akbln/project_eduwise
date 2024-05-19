package com.edusenior.project.dataTransferObjects;

import com.edusenior.project.ServerResponses.ServerResponse;

import java.sql.Timestamp;
import java.util.ArrayList;

public class JwtDTO extends ServerResponse{
    private String token;

    private Long expiry;


    public JwtDTO(String token) {
        super("success", new ArrayList<String>());
        this.token = token;
        expiry = System.currentTimeMillis()+60400000;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
