package com.edusenior.project.services;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.QuestionJpaRepository;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.JpaRepositories.teacher.TeacherJpaDAO;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.Utility.JWTManager;
import com.edusenior.project.dataTransferObjects.GetQuestionDTO;
import com.edusenior.project.dataTransferObjects.QuestionDTO;
import com.edusenior.project.entities.Chapter;
import com.edusenior.project.entities.Question;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class QuestionService {

    private QuestionJpaRepository qJpa;
    private JWTManager jwtManager;
    private CredentialsJpaRepository cJPA;
    private TeacherJpaDAO tJpa;

    @Autowired
    public QuestionService(QuestionJpaRepository qJpa, JWTManager jwtManager, CredentialsJpaRepository cJPA,TeacherJpaDAO tJpa) {
        this.qJpa = qJpa;
        this.jwtManager = jwtManager;
        this.cJPA = cJPA;
        this.tJpa = tJpa;
    }

    public ResponseEntity<ServerResponse> uploadQuestion(QuestionDTO qDTO){
        String teacherEmail;
        try{
            teacherEmail = jwtManager.getEmailFromToken(qDTO.getJwt());
            System.out.println(teacherEmail);
        }catch (Exception ex){
            throw new InvalidOperationException("Invalid Token");
        }

        Optional <Credentials> credOptional = cJPA.findByEmail(teacherEmail);

        // Not supposed to happen
        if(credOptional.isEmpty()){
            throw new InvalidOperationException("Could not process this request");
        }
        Credentials c = credOptional.get();

        //Trust it isn't null
        Teacher t = tJpa.findById(c.getId()).get();


        Question question = new Question();

        question.setQuestion(qDTO.getQuestion());
        question.setAnswer1(qDTO.getAnswer1());
        question.setAnswer2(qDTO.getAnswer2());
        question.setAnswer3(qDTO.getAnswer3());
        question.setAnswer4(qDTO.getAnswer4());
        question.setAnswerKey(qDTO.getAnswerKey().charAt(0)+"");

        question.setTeacher(t);

        try{
            qJpa.saveAndFlush(question);
        }catch (RuntimeException ex){
            throw new InvalidOperationException("Could not upload question");
        }

        return  new ResponseEntity<>(new ServerResponse("success",new ArrayList<>()), HttpStatus.OK);

    }

    public GetQuestionDTO getQuestionByChapterIdAndIndex(String chapterId, int index){
        Page<Question> page = qJpa.findByChapterIdAndIndex(chapterId, PageRequest.of(index,1));
        if(!page.hasContent()){
            throw new InvalidOperationException("Empty Question Set For Chapter");
        }
        Question q = page.getContent().getFirst();

        GetQuestionDTO questionJson = new GetQuestionDTO();

        questionJson.setId(q.getId());
        questionJson.setQuestion(q.getQuestion());
        questionJson.setAnswer1(q.getAnswer1());
        questionJson.setAnswer2(q.getAnswer2());
        questionJson.setAnswer3(q.getAnswer3());
        questionJson.setAnswer4(q.getAnswer4());

        return questionJson;

    }

}
