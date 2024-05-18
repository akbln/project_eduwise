package com.edusenior.project.entities;

import com.edusenior.project.entities.Users.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table (name = "comp_submissions")
public class CompSubmissions {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "submission_id", nullable = false,updatable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "comp_id")
    private Comp comp;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Comp getComp() {
        return comp;
    }

    public void setComp(Comp comp) {
        this.comp = comp;
    }


}
