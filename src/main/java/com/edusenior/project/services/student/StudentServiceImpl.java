package com.edusenior.project.services.student;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.classes.SchoolClassJpaRepository;
import com.edusenior.project.Mappings.StudentMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.student.StudentJpaRepository;
import com.edusenior.project.RestControllers.Student.StudentNotFoundException;
import com.edusenior.project.dataTransferObjects.*;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.ChapterDTO;
import com.edusenior.project.entities.Chapter;
import com.edusenior.project.entities.SchoolClass;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.services.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private StudentJpaRepository studentJpaRepository;
    private BcryptPasswordEncoder encoder;
    private CredentialsJpaRepository credentialsJpaRepository;
    private QuestionService questionService;
    private SchoolClassJpaRepository schoolClassJpaRepository;

    @Autowired
    public StudentServiceImpl(StudentJpaRepository studentJpaRepository, BcryptPasswordEncoder encoder, CredentialsJpaRepository credentialsJpaRepository, QuestionService questionService, SchoolClassJpaRepository schoolClassJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
        this.encoder = encoder;
        this.credentialsJpaRepository = credentialsJpaRepository;
        this.questionService = questionService;
        this.schoolClassJpaRepository = schoolClassJpaRepository;
    }

    @Override
    public Student fetchStudent(String id){
        Optional<Student> s = studentJpaRepository.findById(id);
        if(s.isEmpty()){
            throw new StudentNotFoundException("Student Not Found");
        }
        return s.get();
    }

    @Override
    @Transactional
    public ResponseEntity<ServerResponse> registerStudent(NewStudentDTO sDTO) throws DuplicateEntryException {
        if (credentialsJpaRepository.existsByEmail(sDTO.getEmail())){
            throw new DuplicateEntryException("Email already in use");
        }
        Student s = new Student();
        s = StudentMapper.INSTANCE.newStudentDtoToStudent(sDTO);

        Credentials c = new Credentials(true);
        c.setEmail(sDTO.getEmail());
        c.setHash(encoder.passwordEncoder().encode(sDTO.getPassword()));
        c.setRole("student");
        c.setUser(s);

        credentialsJpaRepository.save(c);
        ServerResponse response = new ServerResponse("success",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public GetQuestionDTO fetchQuestionByChapterIdAndIndex(String chapterId, int offset){
        return questionService.getQuestionByChapterIdAndIndex(chapterId,offset);
    }

    public FetchAllClassesDTO fetchAllClasses(String id){
        Student s = studentJpaRepository.findById(id)
                .orElseThrow(() -> new InvalidOperationException("A student with that ID doesn't exist"));

        ArrayList<SchoolClass> schoolClasses = new ArrayList<>(s.getEnrolledClasses());
        ArrayList<ClassDTO> schoolClassDTOs = new ArrayList<>();

        for(SchoolClass  c : schoolClasses){
            ClassDTO cDTO = new ClassDTO();
            cDTO.setClassId(c.getId());
            cDTO.setCourseId(c.getCourse().getId());
            cDTO.setName(c.getName());
            cDTO.setTeacherId(c.getTeacher().getId());
            schoolClassDTOs.add(cDTO);
        }

        FetchAllClassesDTO classJson = new FetchAllClassesDTO();
        classJson.setAllClasses(schoolClassDTOs);

        return classJson;
    }
    public FetchAllStudentChaptersDTO fetchAllChapters(String classId,String studentId){
        SchoolClass sc = schoolClassJpaRepository.findById(classId)
                .orElseThrow(() -> new InvalidOperationException("A class with that ID doesn't exist"));

        Student student = studentJpaRepository.findById(studentId)
                .orElseThrow(() -> new InvalidOperationException("A student with that ID doesn't exist"));
        if(!sc.getEnrolledStudents().contains(student)){
            throw new InvalidOperationException("Unauthorized request");
        }

        return getAllStudentChaptersDTO(sc);
    }

    private FetchAllStudentChaptersDTO getAllStudentChaptersDTO(SchoolClass sc) {
        ArrayList<Chapter> chapters = new ArrayList<>(sc.getCourse().getAllChapters());

        ArrayList<ChapterDTO> scChaptersDTO = new ArrayList<>();

        for(Chapter c : chapters){
            ChapterDTO cDTO = new ChapterDTO();
            cDTO.setId(c.getId());
            cDTO.setName(c.getName());
            cDTO.setNumber(c.getNumber());
            scChaptersDTO.add(cDTO);
        }

        FetchAllStudentChaptersDTO classJson = new FetchAllStudentChaptersDTO();
        classJson.setAllChapters(scChaptersDTO);
        return classJson;
    }

}
