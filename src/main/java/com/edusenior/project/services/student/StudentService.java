package com.edusenior.project.services.student;

import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Student;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    public Student fetchStudent(String id);
    public ResponseEntity<ServerResponse> registerStudent(NewStudentDTO sDTO);
}
