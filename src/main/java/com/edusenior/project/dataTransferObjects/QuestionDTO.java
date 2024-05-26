package com.edusenior.project.dataTransferObjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class QuestionDTO {


//    @Pattern( regexp = "^(?! )[a-zA-Z0-9 ][a-zA-Z0-9](?<! )$",message = "Invalid title request")
    private String courseName;

    private int chapterNumber;

    @NotNull
    @Size(min = 1,max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$")
    private String question;

    @NotNull
    @Size(min = 1,max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$")
    private String answer1;

    @NotNull
    @Size(min = 1,max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$")
    private String answer2;

    @NotNull
    @Size(min = 1,max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$")
    private String answer3;

    @NotNull
    @Size(min = 1,max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$")
    private String answer4;

    @NotNull
    @Size(min = 1,max = 100)
    @Pattern(regexp = "^[ABCD]$")
    private String answerKey;

    public @Size(min = 1, max = 1000, message = "Invalid title") String getCourseName() {
        return courseName;
    }

    public void setCourseName(@Size(min = 1, max = 1000, message = "Invalid title") String courseName) {
        this.courseName = courseName;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public @NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String getQuestion() {
        return question;
    }

    public void setQuestion(@NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String question) {
        this.question = question;
    }

    public @NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(@NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String answer1) {
        this.answer1 = answer1;
    }

    public @NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(@NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String answer2) {
        this.answer2 = answer2;
    }

    public @NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(@NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String answer3) {
        this.answer3 = answer3;
    }

    public @NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[ABCD]$") String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(@NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[ABCD]$") String answerKey) {
        this.answerKey = answerKey;
    }

    public @NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(@NotNull @Size(min = 1, max = 100) @Pattern(regexp = "^[a-zA-Z0-9.?\\s]+$") String answer4) {
        this.answer4 = answer4;
    }
}
