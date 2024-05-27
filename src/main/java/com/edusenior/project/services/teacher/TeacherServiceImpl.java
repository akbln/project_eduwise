package com.edusenior.project.services.teacher;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.CompJpaRepo;
import com.edusenior.project.JpaRepositories.QuestionJpaRepository;
import com.edusenior.project.JpaRepositories.classes.SchoolClassJpaRepository;
import com.edusenior.project.JpaRepositories.teacher.TeacherJpaDAO;
import com.edusenior.project.Mappings.TeacherMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.teacher.TeacherDAO;
import com.edusenior.project.dataTransferObjects.*;
import com.edusenior.project.entities.Comp;
import com.edusenior.project.entities.Question;
import com.edusenior.project.entities.SchoolClass;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.entities.Users.Teacher;
import com.edusenior.project.services.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherJpaDAO teacherJpaDAO;
    private final SchoolClassJpaRepository schoolClassJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final CompJpaRepo compJpaRepo;
    private TeacherDAO teacherDAO;
    private BcryptPasswordEncoder encoder;
    private CredentialsJpaRepository credentialsJpaRepository;
    private QuestionService qService;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO, BcryptPasswordEncoder encoder, CredentialsJpaRepository credentialsJpaRepository, QuestionService qService, TeacherJpaDAO teacherJpaDAO, SchoolClassJpaRepository schoolClassJpaRepository, QuestionJpaRepository questionJpaRepository, CompJpaRepo compJpaRepo) {
        this.teacherDAO = teacherDAO;
        this.encoder = encoder;
        this.credentialsJpaRepository = credentialsJpaRepository;
        this.qService = qService;
        this.teacherJpaDAO = teacherJpaDAO;
        this.schoolClassJpaRepository = schoolClassJpaRepository;
        this.questionJpaRepository = questionJpaRepository;
        this.compJpaRepo = compJpaRepo;
    }

    @Transactional
    public ResponseEntity<ServerResponse> registerTeacher(NewTeacherDTO tDTO) {
        final String email = tDTO.getEmail().toLowerCase();
        if (credentialsJpaRepository.existsByEmail(email)){
            throw new DuplicateEntryException("Email already in use");
        }
        Teacher t = new Teacher();
        t = TeacherMapper.INSTANCE.newTeacherDtoToTeacher(tDTO);

        Credentials c = new Credentials(true);
        c.setEmail(email);
        c.setHash(encoder.passwordEncoder().encode(tDTO.getPassword()));
        c.setRole("teacher");
        c.setUser(t);

        credentialsJpaRepository.save(c);
        ServerResponse response = new ServerResponse("success",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<ServerResponse> uploadQuestion (String tId,QuestionDTO questionDTO){
        return qService.uploadQuestion(tId,questionDTO);
    }
    public FetchAllClassesDTO fetchAllClasses(String id){
        Teacher t = teacherJpaDAO.findById(id)
                .orElseThrow(() -> new InvalidOperationException("A teacher with that ID doesn't exist"));

        ArrayList<SchoolClass> schoolClasses = new ArrayList<>(t.getSchoolClasses());
        ArrayList<ClassDTO> classDTOs = new ArrayList<>();

        for(SchoolClass  c : schoolClasses){
            ClassDTO cDTO = new ClassDTO();
            cDTO.setClassId(c.getId());
            cDTO.setCourseId(c.getCourse().getId());
            cDTO.setName(c.getName());
            classDTOs.add(cDTO);
        }

        FetchAllClassesDTO classJson = new FetchAllClassesDTO();
        classJson.setAllClasses(classDTOs);

        return classJson;
    }
    public FetchAllQuestionsOfTeacherDTO fetchAllQuestionsOfTeacher(String id){
        Teacher t = teacherJpaDAO.findById(id)
                .orElseThrow(() -> new InvalidOperationException("A teacher with that ID doesn't exist"));
        List<Question> questions = t.getAllQuestions();
        List<GetQuestionWithAnswerDTO> parsedQuestions = new ArrayList<>();
        for (Question q : questions){
            GetQuestionWithAnswerDTO questionDTO = new GetQuestionWithAnswerDTO();
            questionDTO.setId(q.getId());
            questionDTO.setQuestion(q.getQuestion());
            questionDTO.setAnswer1(q.getAnswer1());
            questionDTO.setAnswer2(q.getAnswer2());
            questionDTO.setAnswer3(q.getAnswer3());
            questionDTO.setAnswer4(q.getAnswer4());
            questionDTO.setAnswerKey(q.getAnswerKey());
            parsedQuestions.add(questionDTO);
        }
        FetchAllQuestionsOfTeacherDTO questionsJson = new FetchAllQuestionsOfTeacherDTO();
        questionsJson.setAllQuestions(parsedQuestions);

        return questionsJson;
    }

    @Transactional
    public ResponseEntity<ServerResponse> createComp(String tId,CreateCompDTO compDTO){
        Teacher t = teacherJpaDAO.findById(tId).orElseThrow(()-> new InvalidOperationException("Invalid ID"));
        if(compDTO.getQuestions().isEmpty()){
            throw new InvalidOperationException("Please Select Some Questions");
        }
        SchoolClass sc = schoolClassJpaRepository.findById(compDTO.getClassId()).orElseThrow(
                () -> new InvalidOperationException("A Class with that ID does not exist.")
        );
        Comp comp = new Comp();
        ArrayList<Question> questions = new ArrayList<>();

        for(String qId : compDTO.getQuestions()){
            Question q = questionJpaRepository.findById(qId).orElseThrow(
                    () -> new InvalidOperationException("Invalid question:"+qId)
            );
            questions.add(q);
        }
        int numberOfQuestions = questions.size();
        if(numberOfQuestions != compDTO.getQuestions().size()){
            throw new InvalidOperationException("An error has occurred during competition creation");
        }

        List<Student> classStudents = sc.getEnrolledStudents();


        long compTime = (long) compDTO.getTimePerQuestionSeconds() * numberOfQuestions;

        comp.setQuestions(questions);
        comp.setStartDate(new Timestamp(System.currentTimeMillis()));
        comp.setEndDate(new Timestamp(System.currentTimeMillis() + compTime + 60000));
        comp.setNumberOfQuestions(numberOfQuestions);
        comp.setNumberOfParticipants(classStudents.size());
        comp.setSchoolClass(sc);
        comp.setTimePerQuestionSeconds(compDTO.getTimePerQuestionSeconds());
        sc.getComps().add(comp);

        for(Student s : classStudents){
            s.setComp(comp);
            comp.addStudent(s);
        }
        t.setComp(comp);
        compJpaRepo.saveAndFlush(comp);
        teacherJpaDAO.saveAndFlush(t);
        return new ResponseEntity<ServerResponse>(new ServerResponse("success",new ArrayList<>()),HttpStatus.OK);
    }
}
