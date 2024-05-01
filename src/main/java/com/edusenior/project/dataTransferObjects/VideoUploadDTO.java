package com.edusenior.project.dataTransferObjects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class VideoUploadDTO {
    @NotNull
    @ValidVideo
    private MultipartFile video;

    @NotNull
    @Size(min = 1,max =1000,message = "Invalid title")
//    @Pattern( regexp = "^(?! )[a-zA-Z0-9 ][a-zA-Z0-9](?<! )$",message = "Invalid title request")
    private String title;

    @Size(min = 0,max =1000,message = "Invalid d request")
//    @Pattern( regexp = "^(?! )[a-zA-Z0-9 ][a-zA-Z0-9](?<! )$",message = "Invalid d request")
    private String description;

    public VideoUploadDTO() {
    }


    public VideoUploadDTO(MultipartFile video, String title, String description) {
        this.video = video;
        this.title = title;
        this.description = description;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
