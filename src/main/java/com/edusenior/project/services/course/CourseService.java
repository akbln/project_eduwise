package com.edusenior.project.services.course;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.AddClassToCourseDTO;
import com.edusenior.project.dataTransferObjects.CourseDTO;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    public ResponseEntity<ServerResponse> createCourse(CourseDTO c);
    public ResponseEntity<ServerResponse> addClassToCourse(AddClassToCourseDTO cDTO);
}
