package com.edusenior.project.dataAccessObjects.credentials;

import com.edusenior.project.entities.Credentials;

public interface CredentialsDAO {
    public String fetchPasswordHash(String id);
    public void createCredential(Credentials credentials);
}
