package com.edusenior.project.services.teacher;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.CreateCompDTO;
import com.edusenior.project.dataTransferObjects.FetchAllClassesDTO;
import com.edusenior.project.dataTransferObjects.FetchAllQuestionsOfTeacherDTO;
import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import org.springframework.http.ResponseEntity;

public interface TeacherService {
    public ResponseEntity<ServerResponse> registerTeacher(NewTeacherDTO tDTO);

    public FetchAllClassesDTO fetchAllClasses(String id);

    FetchAllQuestionsOfTeacherDTO fetchAllQuestionsOfTeacher(String id);

    ResponseEntity<ServerResponse> createComp(String id,CreateCompDTO compDTO);
}
