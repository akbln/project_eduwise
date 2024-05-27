package com.edusenior.project.RestControllers.Student;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.*;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.LoadCompDTO;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.SubmitChapterQuestionAnswer;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.SubmitQuestionDTO;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.services.student.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentRestController {
    private StudentServiceImpl studentService;
    @Autowired
    public StudentRestController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/register")
    public ResponseEntity<ServerResponse> createStudent(@Valid @RequestBody NewStudentDTO s){
        return studentService.registerStudent(s);
    }
    @GetMapping("/id={studentUUID}")
    public Student fetchStudentByUUID(@PathVariable String studentUUID){
        return studentService.fetchStudent(studentUUID);
    }
    @GetMapping("/classes/{classId}/chapters/{chapterId}/questions")
    public LoadChapterQuestionsDTO fetchUnsolvedChapterQuestionsOfStudent(@PathVariable String chapterId, UsernamePasswordAuthenticationToken auth, @PathVariable String classId){
        // TO-DO Rework to use classId
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return studentService.fetchUnsolvedChapterQuestionsOfStudent(chapterId,details.get("id"));
    }
    @GetMapping("/classes")
    public FetchAllClassesDTO fetchAllClasses(UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return studentService.fetchAllClasses(details.get("id"));
    }
    @GetMapping("/classes/{id}/chapters")
    public FetchAllStudentChaptersDTO fetchAllChapters(@PathVariable String id, UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return studentService.fetchAllChapters(id,details.get("id"));
    }
    @GetMapping("/competition")
    public LoadCompDTO fetchAllChapters(UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return studentService.fetchEntireCompQuestions(details.get("id"));
    }
    @PostMapping("/competition")
    public ResponseEntity<ServerResponse> submitQuestionResponse(@RequestBody SubmitQuestionDTO sqDTO,UsernamePasswordAuthenticationToken auth){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return studentService.submitQuestionResponse(sqDTO,details.get("id"));
    }
    @PostMapping("/classes/{classId}/chapters/{chapterId}/questions")
    public ResponseEntity<ServerResponse> submitChapterQuestion(@PathVariable String chapterId, @PathVariable String classId,
                                                     UsernamePasswordAuthenticationToken auth, @RequestBody SubmitChapterQuestionAnswer cqDTO){

        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return studentService.submitChapterQuestionAnswer(details.get("id"),classId,chapterId,cqDTO);
    }
    @GetMapping("/chapter/{chapterId}/results")
    public ResultsDTO getChapterResults(UsernamePasswordAuthenticationToken auth,@PathVariable String chapterId){
        Map<String, String> details;
        try{
            details = (Map<String, String>) auth.getDetails();
        }catch (ClassCastException ex){
            throw new InvalidOperationException(ex.getMessage());
        }
        return studentService.fetchChapterAnswersForStudent(chapterId,details.get("id"));
    }

}
