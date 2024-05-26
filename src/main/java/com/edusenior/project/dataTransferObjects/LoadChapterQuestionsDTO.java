package com.edusenior.project.dataTransferObjects;

import java.util.Set;

public class LoadChapterQuestionsDTO {
    private Set<GetQuestionDTO> questions;

    public Set<GetQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<GetQuestionDTO> questions) {
        this.questions = questions;
    }
}
