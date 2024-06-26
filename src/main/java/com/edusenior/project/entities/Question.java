package com.edusenior.project.entities;


import com.edusenior.project.entities.Users.Teacher;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "question_id", nullable = false, updatable = false)
    private String id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer_1", nullable = false)
    private String answer1;

    @Column(name = "answer_2", nullable = false)
    private String answer2;

    @Column(name = "answer_3")
    private String answer3;

    @Column(name = "answer_4")
    private String answer4;

    @Column(name = "answer_key", nullable = false)
    private String answerKey;

    @Column(name = "description")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id",nullable = true)
    private Chapter chapter;

    @ManyToMany
    @JoinTable(
            name = "comp_questions_junction",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn (name = "comp_id")
    )
    private List<Comp> comps;

    @OneToMany(mappedBy = "question")
    private List<CompSubmissions> compSubmissions;

    @OneToMany(mappedBy = "question")
    private Set<ChapterQuestionSubmissions> chapterQuestionSubmissions;


    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public List<Comp> getComps() {
        return comps;
    }

    public void setComps(List<Comp> comps) {
        this.comps = comps;
    }

    public List<CompSubmissions> getCompSubmissions() {
        return compSubmissions;
    }

    public void setCompSubmissions(List<CompSubmissions> compSubmissions) {
        this.compSubmissions = compSubmissions;
    }

    public Set<ChapterQuestionSubmissions> getChapterQuestionSubmissions() {
        return chapterQuestionSubmissions;
    }

    public void setChapterQuestionSubmissions(Set<ChapterQuestionSubmissions> chapterQuestionSubmissions) {
        this.chapterQuestionSubmissions = chapterQuestionSubmissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
