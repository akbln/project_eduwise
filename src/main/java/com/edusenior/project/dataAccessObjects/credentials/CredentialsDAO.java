package com.edusenior.project.dataAccessObjects.credentials;

import com.edusenior.project.entities.Credentials;

public interface CredentialsDAO {
    public Credentials fetchCredential(String email);
    public void createCredential(Credentials credentials);
}
