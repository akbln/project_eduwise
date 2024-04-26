package com.edusenior.project.services.schoolAdmin;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.AddClassToCourseDTO;
import com.edusenior.project.dataTransferObjects.AddMultipleStudentsToCourseDTO;
import com.edusenior.project.dataTransferObjects.CourseDTO;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import org.springframework.http.ResponseEntity;

public interface SchoolAdminService {
    public ResponseEntity<ServerResponse> setTeacherForClass (SetTeacherForClassDTO newInfo);
    public ResponseEntity<ServerResponse> createCourse(CourseDTO cDTO);
    public ResponseEntity<ServerResponse> addClassToCourse(AddClassToCourseDTO cDTO);
    public ResponseEntity<ServerResponse> addMultipleStudentsToClass(AddMultipleStudentsToCourseDTO sDTO);
}
