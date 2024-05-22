package com.edusenior.project.dataTransferObjects.DatabaseQueryObjects;

import com.edusenior.project.dataTransferObjects.GetQuestionDTO;

import java.util.ArrayList;

public class LoadCompDTO {

    private ArrayList<GetQuestionDTO> questions;
    private int timePerQuestions;
    private String compId;

    public ArrayList<GetQuestionDTO> getQuestions() {
        return questions;
    }
    public void setQuestions(ArrayList<GetQuestionDTO> questions) {
        this.questions = questions;
    }
    public int getTimePerQuestions() {
        return timePerQuestions;
    }
    public void setTimePerQuestions(int timePerQuestions) {
        this.timePerQuestions = timePerQuestions;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }
}
