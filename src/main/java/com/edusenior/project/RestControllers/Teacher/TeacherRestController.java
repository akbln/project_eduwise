package com.edusenior.project.RestControllers.Teacher;

import com.edusenior.project.dataTransferObjects.NewTeacherDTO;
import com.edusenior.project.services.teacher.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherRestController {
    TeacherService teacherService;

    @Autowired
    public TeacherRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/register")
    public void registerTeacher(@Valid @RequestBody NewTeacherDTO tDTO) {
        teacherService.registerTeacher(tDTO);
    }
}
