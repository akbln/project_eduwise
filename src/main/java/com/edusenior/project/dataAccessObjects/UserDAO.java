package com.edusenior.project.dataAccessObjects;

import com.edusenior.project.dataTransferObjects.CredentialsDTO;

public interface UserDAO {
    public CredentialsDTO fetchCredentials(String email);
    public boolean checkExistingEmail(String email);
}
