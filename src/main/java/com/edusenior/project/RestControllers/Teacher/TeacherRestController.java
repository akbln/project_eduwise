package com.edusenior.project.RestControllers.Teacher;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import com.edusenior.project.dataTransferObjects.QuestionDTO;
import com.edusenior.project.services.QuestionService;
import com.edusenior.project.services.teacher.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherRestController {
    private final QuestionService questionService;
    TeacherService teacherService;

    @Autowired
    public TeacherRestController(TeacherService teacherService, QuestionService questionService) {
        this.teacherService = teacherService;
        this.questionService = questionService;
    }

    @PostMapping("/register")
    public ResponseEntity<ServerResponse> registerTeacher(@Valid @RequestBody NewTeacherDTO tDTO) {
        return teacherService.registerTeacher(tDTO);
    }
    @PostMapping("/questions/upload")
    public ResponseEntity<ServerResponse> uploadQuestion( @Valid @RequestBody QuestionDTO qDTO){
        return questionService.uploadQuestion(qDTO);
    }

}
