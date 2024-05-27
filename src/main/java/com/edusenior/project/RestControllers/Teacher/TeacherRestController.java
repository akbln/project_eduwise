package com.edusenior.project.RestControllers.Teacher;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.*;
import com.edusenior.project.services.QuestionService;
import com.edusenior.project.services.teacher.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<ServerResponse> uploadQuestion( @Valid @RequestBody QuestionDTO qDTO,UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return questionService.uploadQuestion(details.get("id"),qDTO);
    }
    @GetMapping("/classes")
    public FetchAllClassesDTO fetchAllClasses(UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return teacherService.fetchAllClasses(details.get("id"));
    }
    @GetMapping("/questions")
    public FetchAllQuestionsOfTeacherDTO fetchAllQuestionsOfTeacher(UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return teacherService.fetchAllQuestionsOfTeacher(details.get("id"));
    }
    @PutMapping("/competitions")
    public ResponseEntity<ServerResponse> createComp(@RequestBody CreateCompDTO compDTO,UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return teacherService.createComp(details.get("id"),compDTO);
    }
    @GetMapping("/classes/{classId}/competition")
    public ResultsDTO createComp(UsernamePasswordAuthenticationToken auth, @PathVariable String classId){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return questionService.countResultsOfCompetition(classId,(details.get("id")));
    }

}
