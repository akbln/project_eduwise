package com.edusenior.project.RestControllers.SchoolClasses;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import com.edusenior.project.services.schoolAdmin.SchoolAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("classes")
@RestController
public class SchoolClassController {

    SchoolAdminService schoolAdminService;

    @Autowired
    public SchoolClassController(SchoolAdminService schoolAdminService) {
        this.schoolAdminService = schoolAdminService;
    }

    @PutMapping("/teacher")
    public ResponseEntity<ServerResponse> updateClass(@Valid @RequestBody SetTeacherForClassDTO sDTO){
        return schoolAdminService.setTeacherForClass(sDTO);
    }
}
