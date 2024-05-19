package com.edusenior.project.dataTransferObjects;

import com.edusenior.project.Exceptions.InvalidOperationException;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;

import java.util.ArrayList;

public class CreateCompDTO {

    private ArrayList<String> questions;

    private String classId;

    private int timePerQuestionSeconds;

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getTimePerQuestionSeconds() {
        return timePerQuestionSeconds;
    }

    public void setTimePerQuestionSeconds(int timePerQuestionSeconds) {
        if(timePerQuestionSeconds > 0){
            this.timePerQuestionSeconds = timePerQuestionSeconds;
        }
        else{
            throw new InvalidOperationException("Invalid time per question");
        }
    }
}
