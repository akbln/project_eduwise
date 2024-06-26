package com.edusenior.project.services.SchoolClass;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.AddMultipleStudentsToClassDTO;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import com.edusenior.project.entities.Users.Teacher;
import org.springframework.http.ResponseEntity;

public interface SchoolClassService {
    public ResponseEntity<ServerResponse> setTeacherForClass(SetTeacherForClassDTO newInfo,Teacher t);
    public ResponseEntity<ServerResponse> addMultipleStudentsToClass(AddMultipleStudentsToClassDTO studentsDTO);
}
