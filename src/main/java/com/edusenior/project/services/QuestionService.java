package com.edusenior.project.services;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.ChapterJpaRepository;
import com.edusenior.project.JpaRepositories.QuestionJpaRepository;
import com.edusenior.project.JpaRepositories.courses.CourseJpaDAO;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.student.StudentJpaRepository;
import com.edusenior.project.JpaRepositories.teacher.TeacherJpaDAO;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.Utility.JWTManager;
import com.edusenior.project.dataTransferObjects.GetQuestionDTO;
import com.edusenior.project.dataTransferObjects.LoadChapterQuestionsDTO;
import com.edusenior.project.dataTransferObjects.QuestionDTO;
import com.edusenior.project.entities.Chapter;
import com.edusenior.project.entities.ChapterQuestionSubmissions;
import com.edusenior.project.entities.Course;
import com.edusenior.project.entities.Question;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.entities.Users.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class QuestionService {

    private final StudentJpaRepository studentJpaRepository;
    private final ChapterJpaRepository chapterJpaRepository;
    private final CourseJpaDAO courseJpaDAO;
    private QuestionJpaRepository qJpa;
    private JWTManager jwtManager;
    private CredentialsJpaRepository cJPA;
    private TeacherJpaDAO tJpa;

    @Autowired
    public QuestionService(QuestionJpaRepository qJpa, JWTManager jwtManager, CredentialsJpaRepository cJPA, TeacherJpaDAO tJpa, StudentJpaRepository studentJpaRepository, ChapterJpaRepository chapterJpaRepository, CourseJpaDAO courseJpaDAO) {
        this.qJpa = qJpa;
        this.jwtManager = jwtManager;
        this.cJPA = cJPA;
        this.tJpa = tJpa;
        this.studentJpaRepository = studentJpaRepository;
        this.chapterJpaRepository = chapterJpaRepository;
        this.courseJpaDAO = courseJpaDAO;
    }
    @Transactional
    public ResponseEntity<ServerResponse> uploadQuestion(String tId,QuestionDTO qDTO){
        Teacher t = tJpa.findById(tId).orElseThrow(() -> new InvalidOperationException("Teacher ID Not Found"));
        final String courseName = qDTO.getCourseName();
        final int chapterNumber = qDTO.getChapterNumber();
        Chapter ch = null;
        if(chapterNumber > 0){
            if(courseName == null || courseName.isEmpty()){
                throw new InvalidOperationException("Invalid Course Name");
            }
            ch = chapterJpaRepository.findByCourseNameAndChapterNumber(courseName,chapterNumber).
                    orElseThrow(() -> new InvalidOperationException("Invalid Question Details"));
        }

        Question question = new Question();

        question.setQuestion(qDTO.getQuestion());
        question.setAnswer1(qDTO.getAnswer1());
        question.setAnswer2(qDTO.getAnswer2());
        question.setAnswer3(qDTO.getAnswer3());
        question.setAnswer4(qDTO.getAnswer4());
        question.setAnswerKey(qDTO.getAnswerKey().charAt(0)+"");

        question.setTeacher(t);

        if(ch!=null){
            question.setChapter(ch);
            ch.getQuestions().add(question);
        }
        try{
            qJpa.saveAndFlush(question);
        }catch (RuntimeException ex){
            throw new InvalidOperationException("Could not upload question");
        }
        return  new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()), HttpStatus.OK);
    }

    public LoadChapterQuestionsDTO fetchUnsolvedChapterQuestionsOfStudent(String chapterId, String student){
        Chapter c = chapterJpaRepository.findById(chapterId).orElseThrow(()->new InvalidOperationException("Invalid Chapter ID."));
        Student s = studentJpaRepository.findById(student).orElseThrow(() -> new InvalidOperationException("Invalid Student ID"));

        Set<Question> questions = studentJpaRepository.findUnsubmittedQuestionsByChapterAndStudent(chapterId, student);
        if(questions.isEmpty()) {
            LoadChapterQuestionsDTO empty = new LoadChapterQuestionsDTO();
            Set<GetQuestionDTO> emptySet = new HashSet<>();
            empty.setQuestions(emptySet);
            return empty;
        }
        return parseQuestionsToQuestionDTOs(questions);
    }
    private LoadChapterQuestionsDTO parseQuestionsToQuestionDTOs(Set<Question> questions) {
        LoadChapterQuestionsDTO questionsJson= new LoadChapterQuestionsDTO();
        Set<GetQuestionDTO> parsedQuestions = new HashSet<>();
        for(Question q : questions) {
            GetQuestionDTO gqDTO = new GetQuestionDTO();

            gqDTO.setId(q.getId());
            gqDTO.setQuestion(q.getQuestion());
            gqDTO.setAnswer1(q.getAnswer1());
            gqDTO.setAnswer2(q.getAnswer2());
            gqDTO.setAnswer3(q.getAnswer3());
            gqDTO.setAnswer4(q.getAnswer4());
            parsedQuestions.add(gqDTO);
        }
        questionsJson.setQuestions(parsedQuestions);
        return questionsJson;
    }
}
