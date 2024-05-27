package com.edusenior.project.services;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.ChapterJpaRepository;
import com.edusenior.project.JpaRepositories.QuestionJpaRepository;
import com.edusenior.project.JpaRepositories.classes.SchoolClassJpaRepository;
import com.edusenior.project.JpaRepositories.courses.CourseJpaDAO;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.student.StudentJpaRepository;
import com.edusenior.project.JpaRepositories.teacher.TeacherJpaDAO;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.Utility.JWTManager;
import com.edusenior.project.dataTransferObjects.GetQuestionDTO;
import com.edusenior.project.dataTransferObjects.LoadChapterQuestionsDTO;
import com.edusenior.project.dataTransferObjects.QuestionDTO;
import com.edusenior.project.dataTransferObjects.ResultsDTO;
import com.edusenior.project.entities.*;
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
import java.util.*;

@Service
public class QuestionService {

    private final StudentJpaRepository studentJpaRepository;
    private final ChapterJpaRepository chapterJpaRepository;
    private final CourseJpaDAO courseJpaDAO;
    private final TeacherJpaDAO teacherJpaDAO;
    private final SchoolClassJpaRepository schoolClassJpaRepository;
    private QuestionJpaRepository qJpa;
    private JWTManager jwtManager;
    private CredentialsJpaRepository cJPA;
    private TeacherJpaDAO tJpa;

    @Autowired
    public QuestionService(QuestionJpaRepository qJpa, JWTManager jwtManager, CredentialsJpaRepository cJPA, TeacherJpaDAO tJpa, StudentJpaRepository studentJpaRepository, ChapterJpaRepository chapterJpaRepository, CourseJpaDAO courseJpaDAO, TeacherJpaDAO teacherJpaDAO, SchoolClassJpaRepository schoolClassJpaRepository) {
        this.qJpa = qJpa;
        this.jwtManager = jwtManager;
        this.cJPA = cJPA;
        this.tJpa = tJpa;
        this.studentJpaRepository = studentJpaRepository;
        this.chapterJpaRepository = chapterJpaRepository;
        this.courseJpaDAO = courseJpaDAO;
        this.teacherJpaDAO = teacherJpaDAO;
        this.schoolClassJpaRepository = schoolClassJpaRepository;
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

    public ResultsDTO countResultsOfCompetition(String cId,String tId){
        Teacher t = teacherJpaDAO.findById(tId).orElseThrow(()-> new InvalidOperationException("Invalid ID"));
        Comp c = t.getComp();
        if(c == null){
            throw new InvalidOperationException("Invalid Competition");
        }
        SchoolClass sc = schoolClassJpaRepository.findById(cId).orElseThrow(()-> new InvalidOperationException("Invalid Class ID"));
        if(!(c.getSchoolClass().equals(sc))){
            throw new InvalidOperationException("Invalid Class");
        }
        int countCorrect = 0;
        int countIncorrect = 0;
        List<CompSubmissions> submissions = c.getCompSubmissions();

        for (CompSubmissions sb : submissions){
            String ans = sb.getAnswer();
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
