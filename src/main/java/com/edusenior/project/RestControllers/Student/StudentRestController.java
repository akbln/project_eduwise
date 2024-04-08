package com.edusenior.project.RestControllers.Student;

import com.edusenior.project.dataAccessObjects.student.StudentDAO;
import com.edusenior.project.dataTransferObjects.NewStudentDTO;
import com.edusenior.project.entities.Student;
import com.edusenior.project.services.student.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentRestController {
    private StudentServiceImpl studentService;
    @Autowired
    public StudentRestController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/register")
    public void createStudent(@RequestBody NewStudentDTO s){
        studentService.registerStudent(s);
    }
    @GetMapping("/id={studentUUID}")
    public Student fetchStudentByUUID(@PathVariable String studentUUID){
        return studentService.fetchStudent(studentUUID);
    }
    @GetMapping("/email={studentEmail}")
    public Student fetchStudentByEmail(@PathVariable String studentEmail){
        return studentService.fetchStudentByEmail(studentEmail);
    }

}
