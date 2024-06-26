package com.edusenior.project.services.student;

import com.edusenior.project.Exceptions.DuplicateEntryException;
import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.*;
import com.edusenior.project.JpaRepositories.classes.SchoolClassJpaRepository;
import com.edusenior.project.Mappings.StudentMapper;
import com.edusenior.project.Utility.BcryptPasswordEncoder;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.student.StudentJpaRepository;
import com.edusenior.project.RestControllers.Student.StudentNotFoundException;
import com.edusenior.project.dataTransferObjects.*;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.ChapterDTO;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.LoadCompDTO;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.SubmitChapterQuestionAnswer;
import com.edusenior.project.dataTransferObjects.DatabaseQueryObjects.SubmitQuestionDTO;
import com.edusenior.project.entities.*;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.Student;
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
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final CompJpaRepo compJpaRepo;
    private final CompSubmissionsJPA compSubmissionsJPA;
    private final QuestionJpaRepository questionJpaRepository;
    private final ChapterJpaRepository chapterJpaRepository;
    private final ChapterQuestionSubmissionsJpaRepo chapterQuestionSubmissionsJpaRepo;
    private StudentJpaRepository studentJpaRepository;
    private BcryptPasswordEncoder encoder;
    private CredentialsJpaRepository credentialsJpaRepository;
    private QuestionService questionService;
    private SchoolClassJpaRepository schoolClassJpaRepository;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    public StudentServiceImpl(StudentJpaRepository studentJpaRepository, BcryptPasswordEncoder encoder, CredentialsJpaRepository credentialsJpaRepository, QuestionService questionService, SchoolClassJpaRepository schoolClassJpaRepository, CompJpaRepo compJpaRepo, CompSubmissionsJPA compSubmissionsJPA, QuestionJpaRepository questionJpaRepository, ChapterJpaRepository chapterJpaRepository, ChapterQuestionSubmissionsJpaRepo chapterQuestionSubmissionsJpaRepo) {
        this.studentJpaRepository = studentJpaRepository;
        this.encoder = encoder;
        this.credentialsJpaRepository = credentialsJpaRepository;
        this.questionService = questionService;
        this.schoolClassJpaRepository = schoolClassJpaRepository;
        this.compJpaRepo = compJpaRepo;
        this.compSubmissionsJPA = compSubmissionsJPA;
        this.questionJpaRepository = questionJpaRepository;
        this.chapterJpaRepository = chapterJpaRepository;
        this.chapterQuestionSubmissionsJpaRepo = chapterQuestionSubmissionsJpaRepo;
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
        final String email = sDTO.getEmail().toLowerCase();
        if (credentialsJpaRepository.existsByEmail(email)){
            throw new DuplicateEntryException("Email already in use");
        }
        Student s = new Student();
        s = StudentMapper.INSTANCE.newStudentDtoToStudent(sDTO);

        Credentials c = new Credentials(true);
        c.setEmail(email);
        c.setHash(encoder.passwordEncoder().encode(sDTO.getPassword()));
        c.setRole("student");
        c.setUser(s);

        credentialsJpaRepository.save(c);
        ServerResponse response = new ServerResponse("success",new ArrayList<>());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public LoadChapterQuestionsDTO fetchUnsolvedChapterQuestionsOfStudent(String chapterId, String sId){
        return questionService.fetchUnsolvedChapterQuestionsOfStudent(chapterId,sId);
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

    public FetchAllStudentChaptersDTO getAllStudentChaptersDTO(SchoolClass sc) {
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
    public LoadCompDTO fetchEntireCompQuestions(String id){
        Student s = studentJpaRepository.findById(id).orElseThrow(() -> new InvalidOperationException("Student with that Id could not be found."));
        Comp comp = s.getComp();
        if (comp == null){
            throw new InvalidOperationException("No Active Competition");
        }
        if(new Timestamp(System.currentTimeMillis()).after(comp.getEndDate())){
            throw new InvalidOperationException("Expired");
        }
        List<Question> compQuestions = comp.getQuestions();
        ArrayList<GetQuestionDTO> getQuestionDTOS = new ArrayList<>();
        for(Question q : compQuestions){
            GetQuestionDTO qDTO = new GetQuestionDTO();
            qDTO.setId(q.getId());
            qDTO.setQuestion(q.getQuestion());
            qDTO.setAnswer1(q.getAnswer1());
            qDTO.setAnswer2(q.getAnswer2());
            qDTO.setAnswer3(q.getAnswer3());
            qDTO.setAnswer4(q.getAnswer4());
            getQuestionDTOS.add(qDTO);
        }
        LoadCompDTO compDTO = new LoadCompDTO();
        compDTO.setCompId(comp.getCompId());
        compDTO.setQuestions(getQuestionDTOS);
        compDTO.setTimePerQuestions(comp.getTimePerQuestionSeconds());

        return compDTO;
    }
    @Transactional
    public ResponseEntity<ServerResponse> submitQuestionResponse(SubmitQuestionDTO sqDTO, String id){
        Student s = studentJpaRepository.findById(id).orElseThrow(() -> new InvalidOperationException("A Student with that ID could not be found"));
        final String compId = sqDTO.getCompId();
        Comp comp = compJpaRepo.findById(compId).orElseThrow(() -> new InvalidOperationException("A Competition with that ID could not be found"));
        if(new Timestamp(System.currentTimeMillis()).after(comp.getEndDate())){
            compJpaRepo.deleteById(comp.getCompId());
            compJpaRepo.flush();
            ArrayList<String> arr =  new ArrayList<>();
            arr.add("Expired Competition");
            return new ResponseEntity<>(new ServerResponse("failed",arr),HttpStatus.BAD_REQUEST);
        }
        if(!comp.getStudents().contains(s)){
            throw new InvalidOperationException("A Competition with that ID could not be found");
        }
        Question q = questionJpaRepository.findById(sqDTO.getQuestionId()).orElseThrow(() ->
                new InvalidOperationException("A question with that ID could not be found"));
        if(!comp.getQuestions().contains(q)){
            throw new InvalidOperationException("A question with that ID could not be found");
        }
        int num = compSubmissionsJPA.getNumberOfSubmissionsOfStudentForQuestion(sqDTO.getQuestionId(),id,compId);
        if(num > 0){
            throw new InvalidOperationException("Already Submitted Question");
        }
        CompSubmissions submission = new CompSubmissions();
        submission.setAnswer(sqDTO.getSubmittedAnswer());
        submission.setQuestion(q);
        submission.setStudent(s);
        submission.setComp(comp);
        compSubmissionsJPA.saveAndFlush(submission);
        return new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()),HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<ServerResponse> submitChapterQuestionAnswer(String sId,String classId,String chapterId, SubmitChapterQuestionAnswer cqDTO){
        Student s = studentJpaRepository.findById(sId).orElseThrow(()-> new InvalidOperationException("Invalid Student ID"));
        SchoolClass sc = schoolClassJpaRepository.findById(classId).orElseThrow(()-> new InvalidOperationException("Invalid Class ID"));
        Chapter c = chapterJpaRepository.findById(chapterId).orElseThrow(()-> new InvalidOperationException("Invalid Chapter ID"));
        Question q = questionJpaRepository.findById(cqDTO.getQuestionId()).orElseThrow(() -> new InvalidOperationException("Invalid Question ID"));
        if(!sc.getEnrolledStudents().contains(s)){
            throw new InvalidOperationException("Not Registered In Class");
        }
        if(!c.getQuestions().contains(q)) {
            throw new InvalidOperationException("Not Valid Question");
        }
        ChapterQuestionSubmissions submission = new ChapterQuestionSubmissions();
        submission.setQuestion(q);
        submission.setStudent(s);
        submission.setSubmission(cqDTO.getSubmission());

        chapterQuestionSubmissionsJpaRepo.saveAndFlush(submission);

        return new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()),HttpStatus.OK);
    }
    public ResultsDTO fetchChapterAnswersForStudent(String chapterId,String sId){
        Student s = studentJpaRepository.findById(sId).orElseThrow(()-> new InvalidOperationException("Invalid Student ID"));
        Chapter c = chapterJpaRepository.findById(chapterId).orElseThrow(()-> new InvalidOperationException("Invalid Chapter ID"));
        
        List<ChapterQuestionSubmissions> questionSubmissions = new ArrayList<>(chapterQuestionSubmissionsJpaRepo.findByChapterIdAndStudentId(chapterId,sId));

        return getResultsDTO(questionSubmissions);
    }

    private ResultsDTO getResultsDTO(List<ChapterQuestionSubmissions> questionSubmissions) {
        int countCorrect = 0;
        int countIncorrect = 0;

        for (ChapterQuestionSubmissions sb : questionSubmissions){
            String ans = sb.getSubmission();
            Question q = sb.getQuestion();
            if(q != null && ans != null){
                if(ans.equals(q.getAnswerKey())){
                    countCorrect++;
                }else{
                    countIncorrect++;
                }
            }
        }
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setCorrect(countCorrect);
        resultsDTO.setIncorrect(countIncorrect);
        return resultsDTO;
    }

}

