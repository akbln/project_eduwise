package com.edusenior.project.dataTransferObjects.DatabaseQueryObjects;

import com.edusenior.project.dataTransferObjects.GetQuestionDTO;

import java.util.ArrayList;

public class LoadCompDTO {

    private ArrayList<GetQuestionDTO> questions;
    private int timePerQuestions;


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
}
