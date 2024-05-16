package com.edusenior.project.dataTransferObjects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class VideoUploadDTO {
    @NotNull
//    @ValidVideo
    private MultipartFile video;

    @NotNull
    @Size(min = 1,max =1000,message = "Invalid title")
//    @Pattern( regexp = "^(?! )[a-zA-Z0-9 ][a-zA-Z0-9](?<! )$",message = "Invalid title request")
    private String courseName;

    @NotNull
    @Pattern(regexp = "^[1-9]+$")
    private String chapterNumber;

    public VideoUploadDTO() {
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }
}
