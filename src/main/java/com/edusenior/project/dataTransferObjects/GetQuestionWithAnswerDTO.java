package com.edusenior.project.dataTransferObjects;

public class GetQuestionWithAnswerDTO extends GetQuestionDTO{
    private String answerKey;

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answer) {
        this.answerKey = answer;
    }
}
