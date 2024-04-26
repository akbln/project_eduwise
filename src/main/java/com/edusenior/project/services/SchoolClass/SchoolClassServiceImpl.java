package com.edusenior.project.services.SchoolClass;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.Exceptions.NotFound.SchoolClassNotFoundException;
import com.edusenior.project.Exceptions.StudentEmailsNotFoundException;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataAccessObjects.classes.SchoolClassJpaRepository;
import com.edusenior.project.dataAccessObjects.credentials.CredentialsJpaRepository;
import com.edusenior.project.dataAccessObjects.student.StudentJpaDAO;
import com.edusenior.project.dataTransferObjects.AddMultipleStudentsToCourseDTO;
import com.edusenior.project.dataTransferObjects.SetTeacherForClassDTO;
import com.edusenior.project.entities.SchoolClass;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.entities.Users.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {

    private SchoolClassJpaRepository schoolClassJpaRepository;
    private CredentialsJpaRepository credentialsJpaRepository;
    private StudentJpaDAO studentJpaDAO;


    @Autowired
    public SchoolClassServiceImpl(SchoolClassJpaRepository schoolClassJpaRepository, StudentJpaDAO studentJpaDAO,
                                  CredentialsJpaRepository credentialsJpaRepository) {
        this.schoolClassJpaRepository = schoolClassJpaRepository;
        this.studentJpaDAO = studentJpaDAO;
        this.credentialsJpaRepository = credentialsJpaRepository;
    }


    @Override
    @Transactional
    public ResponseEntity<ServerResponse> setTeacherForClass(SetTeacherForClassDTO newInfo,Teacher t) {

        Optional<SchoolClass> schoolClassOptional = schoolClassJpaRepository.findById(newInfo.getClassId());
        if (schoolClassOptional.isEmpty()){
            throw new SchoolClassNotFoundException("Could not find requested class");
        }
        SchoolClass sc = schoolClassOptional.get();
        sc.setTeacher(t);
        t.addClass(sc);
        schoolClassJpaRepository.save(sc);
        return new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> addMultipleStudentsToClass(AddMultipleStudentsToCourseDTO sDTO){

        SchoolClass sc = findClassById(sDTO.getClassId());

        HashSet<String> foundEmails = credentialsJpaRepository.findExistingStudentEmailsByEmails(sDTO.getEmails());
        List<String> notFoundEmails = getEmailsNotFoundInDatabase(sDTO.getEmails(),foundEmails);
        if(!notFoundEmails.isEmpty()){
            throw new StudentEmailsNotFoundException("Could not find registered students with the following emails: ",(ArrayList<String>) notFoundEmails);
        }

        try {
            List<Student> students = getStudentsByEmails(foundEmails);
            sc.addMultipleStudents(students);
            schoolClassJpaRepository.save(sc);
        } catch (RuntimeException ex){
            throw new DuplicateEntryException(ex.getMessage());
        }

        return new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()),HttpStatus.OK);
    }
    private List<String> getEmailsNotFoundInDatabase(List<String> emails,HashSet<String> foundEmails){

        ArrayList<String> notFoundEmails = new ArrayList<>(100);

        for (String email : emails){
            if (!foundEmails.contains(email)){
                notFoundEmails.add(email);
            }
        }
        return notFoundEmails;
    }
    private List<Student> getStudentsByEmails(HashSet<String> emails){
        List<String> studentIds = credentialsJpaRepository.findUserIdsByEmailsAndRole(emails.stream().toList(),"student");
        return studentJpaDAO.findAllById(studentIds);
    }
    private SchoolClass findClassById(String id){
        Optional<SchoolClass> classOptional = schoolClassJpaRepository.findById(id);
        if (classOptional.isEmpty()){
            throw new SchoolClassNotFoundException("The specified class could not be found");
        }
        return classOptional.get();
    }
}
