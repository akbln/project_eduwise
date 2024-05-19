package com.edusenior.project.RestControllers.Student;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.FetchAllStudentChaptersDTO;
import com.edusenior.project.dataTransferObjects.FetchAllClassesDTO;
import com.edusenior.project.dataTransferObjects.GetQuestionDTO;
import com.edusenior.project.dataTransferObjects.NewStudentDTO;
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


    @PutMapping("/register")
    public ResponseEntity<ServerResponse> createStudent(@Valid @RequestBody NewStudentDTO s){
        return studentService.registerStudent(s);
    }
    @GetMapping("/id={studentUUID}")
    public Student fetchStudentByUUID(@PathVariable String studentUUID){
        return studentService.fetchStudent(studentUUID);
    }
    @GetMapping("/chapters/{chapterId}/questions/{offSet}")
    public GetQuestionDTO fetchQuestionByChapterIdAndIndex(@PathVariable String chapterId, @PathVariable int offSet){
        return studentService.fetchQuestionByChapterIdAndIndex(chapterId,offSet);
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

}
