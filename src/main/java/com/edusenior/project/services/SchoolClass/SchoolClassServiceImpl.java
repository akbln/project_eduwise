package com.edusenior.project.services.SchoolClass;

import com.edusenior.project.Exceptions.NotFound.SchoolClassNotFoundException;
import com.edusenior.project.Utility.ServerResponse;
import com.edusenior.project.dataAccessObjects.classes.SchoolClassJpaDAO;
import com.edusenior.project.dataAccessObjects.credentials.CredentialsJpaRepository;
import com.edusenior.project.dataAccessObjects.student.StudentDAO;
import com.edusenior.project.dataTransferObjects.AddMultipleStudentsToCourseDTO;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import com.edusenior.project.entities.SchoolClass;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.entities.Users.Teacher;
import com.edusenior.project.entities.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {

    private SchoolClassJpaDAO schoolClassJpaDAO;
    private CredentialsJpaRepository credentialsJpaRepository;
    private StudentDAO studentDAO;

    public SchoolClassServiceImpl(SchoolClassJpaDAO schoolClassJpaDAO, StudentDAO studentDAO,
                                  CredentialsJpaRepository credentialsJpaRepository) {
        this.schoolClassJpaDAO = schoolClassJpaDAO;
        this.studentDAO = studentDAO;
        this.credentialsJpaRepository = credentialsJpaRepository;
    }

    @Autowired






    @Override
    @Transactional
    public ResponseEntity<ServerResponse> setTeacherForClass(SetTeacherForClassDTO newInfo,Teacher t) {

        Optional<SchoolClass> schoolClassOptional = schoolClassJpaDAO.findById(newInfo.getClassId());
        if (schoolClassOptional.isEmpty()){
            throw new SchoolClassNotFoundException("Could not find requested class");
        }
        SchoolClass sc = schoolClassOptional.get();
        sc.setTeacher(t);
        t.addClass(sc);
        schoolClassJpaDAO.save(sc);
        return new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> addMultipleStudentsToClass(AddMultipleStudentsToCourseDTO studentsDTO){
        ArrayList<String> studentIds = (ArrayList<String>) credentialsJpaRepository.findUserIdsByEmailsAndRole(studentsDTO.getEmails(),"student");
        ArrayList<Student> students = studentDAO.findAllById(studentIds);
        for(User s : students){
            s.add
        }
    }
}
