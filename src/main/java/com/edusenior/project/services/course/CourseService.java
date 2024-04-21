package com.edusenior.project.services.course;

import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataTransferObjects.CourseDTO;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    public ResponseEntity<ServerResponse> addCourse(CourseDTO c);
}
