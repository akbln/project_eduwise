package com.edusenior.project.RestControllers.Student;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.GetQuestionDTO;
import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Question;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.services.student.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
