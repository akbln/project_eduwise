package com.edusenior.project.RestControllers;

import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataTransferObjects.CourseDTO;
import com.edusenior.project.services.course.CourseService;
import jakarta.validation.Valid;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseRestController {

    private CourseService courseService;

    @Autowired
    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public ResponseEntity<ServerResponse> addCourse(@Valid @RequestBody CourseDTO cDTO){
        return courseService.addCourse(cDTO);
    }

}
