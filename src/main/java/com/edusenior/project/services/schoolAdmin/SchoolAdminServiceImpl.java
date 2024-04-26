package com.edusenior.project.services.schoolAdmin;


import com.edusenior.project.Exceptions.EmailNotFoundException;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataAccessObjects.credentials.CredentialsJpaRepository;
import com.edusenior.project.dataAccessObjects.teacher.TeacherJpaDAO;
import com.edusenior.project.dataTransferObjects.AddClassToCourseDTO;
import com.edusenior.project.dataTransferObjects.AddMultipleStudentsToCourseDTO;
import com.edusenior.project.dataTransferObjects.CourseDTO;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import com.edusenior.project.entities.Users.Teacher;
import com.edusenior.project.services.SchoolClass.SchoolClassService;
import com.edusenior.project.services.course.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SchoolAdminServiceImpl implements SchoolAdminService{

    private CredentialsJpaRepository credentialsJpaRepository;
    private CourseService courseService;
    private SchoolClassService schoolClassService;
    private TeacherJpaDAO teacherJpaDAO;

    @Autowired
    public SchoolAdminServiceImpl(CredentialsJpaRepository credentialsJpaRepository, CourseService courseService,
                                  SchoolClassService schoolClassService, TeacherJpaDAO teacherJpaDAO) {
        this.credentialsJpaRepository = credentialsJpaRepository;
        this.courseService = courseService;
        this.schoolClassService = schoolClassService;
        this.teacherJpaDAO = teacherJpaDAO;
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> addClassToCourse(AddClassToCourseDTO cDTO){
        return courseService.addClassToCourse(cDTO);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> createCourse(CourseDTO cDTO){
        return courseService.createCourse(cDTO);
    }


    @Override
    public ResponseEntity<ServerResponse> setTeacherForClass(SetTeacherForClassDTO newTeacherInfo) {
        final String email = newTeacherInfo.getEmail();
        final String teacherId = getTeacherId(email);
        final String role = getRoleByEmail(email);

        Optional<Teacher> teacherOptional = teacherJpaDAO.findById(teacherId);
        if(teacherOptional.isEmpty()){
            throw new EmailNotFoundException("Could not find teacher with the email specified");
        }
        Teacher t = teacherOptional.get();


        try{
            return schoolClassService.setTeacherForClass(newTeacherInfo,t);
        }catch (Exception ex){
            ArrayList<String> errors = new ArrayList<>();
            errors.add(ex.getMessage());
            return new ResponseEntity<ServerResponse>(new ServerResponse("failed",errors),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> addMultipleStudentsToClass(AddMultipleStudentsToCourseDTO sDTO){
        return schoolClassService.addMultipleStudentsToClass(sDTO);
    }

    private String getTeacherId(String email){
        String teacherId = credentialsJpaRepository.findUserIdByEmail(email);
        if(teacherId == null){
            throw new EmailNotFoundException("Could not find teacher with the email specified");
        }
        return teacherId;
    }
    private String getRoleByEmail(String email){
        final String role = credentialsJpaRepository.getRoleByEmail(email);
        if(!"teacher".equals(role)){
            throw new EmailNotFoundException("Could not find teacher with the email specified");
        }
        return role;
    }
}
