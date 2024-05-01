package com.edusenior.project.RestControllers;

import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.VideoUploadDTO;
import com.edusenior.project.services.VideoUploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/videos")
public class VideoUploadRestController {

    private VideoUploadService videoUploadService;

    @Autowired
    public VideoUploadRestController(VideoUploadService videoUploadService) {
        this.videoUploadService = videoUploadService;
    }

    @PutMapping("/upload")
    public ResponseEntity<ServerResponse> uploadVideo (@Valid @ModelAttribute VideoUploadDTO vDTO){
        return videoUploadService.storeVideo(vDTO);
    }
}
