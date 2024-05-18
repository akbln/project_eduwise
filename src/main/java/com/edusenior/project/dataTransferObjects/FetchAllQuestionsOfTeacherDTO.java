package com.edusenior.project.dataTransferObjects;

import java.util.ArrayList;
import java.util.List;

public class FetchAllQuestionsOfTeacherDTO {
    private List<GetQuestionWithAnswerDTO> AllQuestions;

    public List<GetQuestionWithAnswerDTO> getAllQuestions() {
        return AllQuestions;
    }

    public void setAllQuestions(List<GetQuestionWithAnswerDTO> allQuestions) {
        AllQuestions = allQuestions;
    }
}
