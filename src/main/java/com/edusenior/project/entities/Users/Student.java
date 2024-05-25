package com.edusenior.project.entities.Users;

import com.edusenior.project.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "educational_level", length = 50, nullable = false, updatable = true)
    private String level;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "classes_students_junction",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private Set<SchoolClass> enrolledClasses;

    @ManyToOne
    @JoinColumn(name = "comp_id")
    private Comp comp;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<ChapterQuestionSubmissions> submissions;

    @OneToMany(mappedBy = "student")
    private List<CompSubmissions> compSubmissions;

    @OneToMany(mappedBy = "student")
    private List<SubmissionCounts> submissionCounts;

    public Student() {
        super();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<SchoolClass> getEnrolledClasses() {
        return enrolledClasses;
    }

    public void setEnrolledClasses(Set<SchoolClass> enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }

    public Comp getComp() {
        return comp;
    }

    public void setComp(Comp comp) {
        this.comp = comp;
    }

    public List<CompSubmissions> getCompSubmissions() {
        return compSubmissions;
    }

    public void setCompSubmissions(List<CompSubmissions> compSubmissions) {
        this.compSubmissions = compSubmissions;
    }

    public Set<ChapterQuestionSubmissions> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Set<ChapterQuestionSubmissions> submissions) {
        this.submissions = submissions;
    }

    public List<SubmissionCounts> getSubmissionCounts() {
        return submissionCounts;
    }

    public void setSubmissionCounts(List<SubmissionCounts> submissionCounts) {
        this.submissionCounts = submissionCounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
