package com.edusenior.project.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.HashSet;

@Entity
@Table(name = "comp")
public class Comp {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "comp_id", nullable = false,updatable = false)
    private String compId;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "time_per_question")
    private int timePerQuestionSeconds;

    @Column(name = "number_of_questions")
    private int numberOfQuestions;

    @Column(name = "number_of_participants")
    private int numberOfParticipants;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @ManyToMany
    @JoinTable(
            name = "comp_questions_junction",
            joinColumns = @JoinColumn(name = "comp_id"),
            inverseJoinColumns = @JoinColumn (name = "question_id")
    )
    private HashSet<Question> questions;

    @OneToMany(mappedBy = "comp")
    private HashSet<CompSubmissions> compSubmissions;



    public String getCompId() {
        return compId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getTimePerQuestionSeconds() {
        return timePerQuestionSeconds;
    }

    public void setTimePerQuestionSeconds(int timePerQuestionSeconds) {
        this.timePerQuestionSeconds = timePerQuestionSeconds;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public HashSet<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashSet<Question> questions) {
        this.questions = questions;
    }

    public HashSet<CompSubmissions> getCompSubmissions() {
        return compSubmissions;
    }

    public void setCompSubmissions(HashSet<CompSubmissions> compSubmissions) {
        this.compSubmissions = compSubmissions;
    }
}
