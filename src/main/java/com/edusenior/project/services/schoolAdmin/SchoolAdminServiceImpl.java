package com.edusenior.project.services.schoolAdmin;

import com.edusenior.project.Exceptions.EmailNotFoundException;
import com.edusenior.project.Exceptions.SchoolClassNotFoundException;
import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataAccessObjects.classes.SchoolClassDAO;
import com.edusenior.project.dataAccessObjects.credentials.CredentialsDAO;
import com.edusenior.project.dataAccessObjects.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import com.edusenior.project.entities.SchoolClass;
import com.edusenior.project.entities.Users.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SchoolAdminServiceImpl implements SchoolAdminService{

    private CredentialsDAO credentialsDAO;
    private SchoolClassDAO schoolClassDAO;
    private TeacherDAO teacherDAO;


    @Autowired
    public SchoolAdminServiceImpl(CredentialsDAO credentialsDAO, SchoolClassDAO schoolClassDAO,TeacherDAO teacherDAO) {
        this.credentialsDAO = credentialsDAO;
        this.schoolClassDAO = schoolClassDAO;
        this.teacherDAO = teacherDAO;
    }




    @Override
    @Transactional
    public ResponseEntity<ServerResponse> setTeacherForClass(SetTeacherForClassDTO newInfo) {
        final String email = newInfo.getEmail();
        String teacherId = getTeacherId(email);

        if(teacherId == null){
            throw new EmailNotFoundException("Could not find teacher with the email specified");
        }
        final String role = getRoleByEmail(email);
        if(!"teacher".equals(role)){
            throw new EmailNotFoundException("Could not find teacher with the email specified");
        }
        SchoolClass sc = schoolClassDAO.getClassById(newInfo.getClassId());
        if (sc == null){
            throw new SchoolClassNotFoundException("Could not find requested class");
        }
        Teacher t = teacherDAO.fetchTeacherById(teacherId);
        sc.setTeacher(t);
        t.addClass(sc);
        schoolClassDAO.persistClass(sc);
        return new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()),HttpStatus.OK);
    }

    private String getTeacherId(String email){
        return credentialsDAO.getUserIdByEmail(email);
    }
    private String getRoleByEmail(String email){
        return credentialsDAO.getRoleByEmail(email);
    }
}
