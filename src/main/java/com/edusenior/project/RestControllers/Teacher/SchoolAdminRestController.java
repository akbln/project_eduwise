package com.edusenior.project.RestControllers.Teacher;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.AddClassToCourseDTO;
import com.edusenior.project.dataTransferObjects.AddMultipleStudentsToCourseDTO;
import com.edusenior.project.dataTransferObjects.CourseDTO;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import com.edusenior.project.services.schoolAdmin.SchoolAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schoolAdmins")
public class SchoolAdminRestController {
    private SchoolAdminService schoolAdminService;

    @Autowired
    public SchoolAdminRestController(SchoolAdminService schoolAdminService) {
        this.schoolAdminService = schoolAdminService;
    }


    @PutMapping("/classes/updateTeacher")
    public ResponseEntity<ServerResponse> updateClass(@Valid @RequestBody SetTeacherForClassDTO sDTO){
        return schoolAdminService.setTeacherForClass(sDTO);
    }

    @PutMapping("/courses")
    public ResponseEntity<ServerResponse> createCourse(@Valid @RequestBody CourseDTO cDTO){
        return schoolAdminService.createCourse(cDTO);
    }

    @PutMapping("/courses/addClass")
    public ResponseEntity<ServerResponse> addClassToCourse(@Valid @RequestBody AddClassToCourseDTO cDTO){
        return schoolAdminService.addClassToCourse(cDTO);
    }
    @PutMapping("/classes/students/add")
    public ResponseEntity<ServerResponse> addMultipleStudentsToClass(@RequestBody AddMultipleStudentsToCourseDTO sDTO){
        return schoolAdminService.addMultipleStudentsToClass(sDTO);
    }

}
