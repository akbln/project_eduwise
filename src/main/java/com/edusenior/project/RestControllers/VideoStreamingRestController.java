package com.edusenior.project.RestControllers;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.services.VideoStreamingService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VideoStreamingRestController {

    private VideoStreamingService videoStreamingService;

    @Autowired
    public VideoStreamingRestController(VideoStreamingService videoStreamingService){
        this.videoStreamingService = videoStreamingService;
    }


    @GetMapping("/videos/{id}")
    public ResponseEntity<ResourceRegion> getVideoChunk(@RequestHeader HttpHeaders headers,@PathVariable("id")  String id){
        return videoStreamingService.getVideo(headers,id);
    }

}

