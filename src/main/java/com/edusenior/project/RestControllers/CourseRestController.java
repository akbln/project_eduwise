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



}
