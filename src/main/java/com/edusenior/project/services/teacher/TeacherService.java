package com.edusenior.project.services.teacher;

import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import org.springframework.http.ResponseEntity;

public interface TeacherService {
    public ResponseEntity<ServerResponse> registerTeacher(NewTeacherDTO tDTO);

}
