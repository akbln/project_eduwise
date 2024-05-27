package com.edusenior.project.services.schoolAdmin;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.*;
import org.springframework.http.ResponseEntity;

public interface SchoolAdminService {
    public FetchAllClassesDTO fetchAllClasses();
    public ResponseEntity<ServerResponse> setTeacherForClass (SetTeacherForClassDTO newInfo);
    public ResponseEntity<ServerResponse> createCourse(CourseDTO cDTO);
    public ResponseEntity<ServerResponse> addClassToCourse(AddClassToCourseDTO cDTO);
    public ResponseEntity<ServerResponse> addMultipleStudentsToClass(AddMultipleStudentsToClassDTO sDTO);
}
