package com.edusenior.project.entities;


import com.edusenior.project.entities.Users.Student;
import com.edusenior.project.entities.Users.Teacher;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "comp")
public class Comp {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "comp_id", nullable = false, updatable = false)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "comp_questions_junction",
            joinColumns = @JoinColumn(name = "comp_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    @OneToMany(mappedBy = "comp")
    private List<CompSubmissions> compSubmissions;

    @OneToMany(mappedBy = "comp")
    private List<Student> students;


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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<CompSubmissions> getCompSubmissions() {
        return compSubmissions;
    }

    public void setCompSubmissions(List<CompSubmissions> compSubmissions) {
        this.compSubmissions = compSubmissions;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    public void addStudent(Student s){
        if(students == null){
            students = new ArrayList<>();
        }
        students.add(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comp comp = (Comp) o;
        return Objects.equals(getCompId(), comp.getCompId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCompId());
    }
}
