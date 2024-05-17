package com.edusenior.project.dataTransferObjects;

import com.edusenior.project.ServerResponses.ServerResponse;

import java.util.ArrayList;

public class JwtDTO extends ServerResponse{
    private String token;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
