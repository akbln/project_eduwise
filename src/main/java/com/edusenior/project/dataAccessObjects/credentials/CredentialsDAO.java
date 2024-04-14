package com.edusenior.project.dataAccessObjects.credentials;

import com.edusenior.project.entities.Credentials;

public interface CredentialsDAO {
    public String getUserIdByEmail(String email);
    public boolean checkIfEmailExists(String email);
    public Credentials getByEmail(String email);
    public void persistChange(Credentials credential);
}
