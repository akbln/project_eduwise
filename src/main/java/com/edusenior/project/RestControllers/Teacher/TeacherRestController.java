package com.edusenior.project.RestControllers.Teacher;

import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import com.edusenior.project.services.teacher.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherRestController {
    TeacherService teacherService;

    @Autowired
    public TeacherRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PutMapping("/register")
    public ResponseEntity<ServerResponse> registerTeacher(@Valid @RequestBody NewTeacherDTO tDTO) {
        return teacherService.registerTeacher(tDTO);
    }

}
