package com.edusenior.project.services.schoolAdmin;

import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import org.springframework.http.ResponseEntity;

public interface SchoolAdminService {
    public ResponseEntity<ServerResponse> setTeacherForClass (SetTeacherForClassDTO newInfo);
}
