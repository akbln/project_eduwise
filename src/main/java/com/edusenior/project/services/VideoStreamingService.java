package com.edusenior.project.services;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.ChapterJpaRepository;
import com.edusenior.project.JpaRepositories.VideoJpaRepository;
import com.edusenior.project.entities.Chapter;
import com.edusenior.project.entities.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoStreamingService {

    private ChapterJpaRepository chapterJpaRepository;
    private VideoJpaRepository videoJpaRepository;

    @Autowired
    public VideoStreamingService(VideoJpaRepository videoJpaRepository, ChapterJpaRepository chapterJpaRepository) {
        this.videoJpaRepository = videoJpaRepository;
        this.chapterJpaRepository = chapterJpaRepository;
    }

    public ResponseEntity<ResourceRegion> getVideo(HttpHeaders headers, String id) {
        Optional<Chapter> chapterOptional = chapterJpaRepository.findById(id);
        if(chapterOptional.isEmpty()){
            throw new InvalidOperationException("Invalid ID");
        }
        Video videoOptional = chapterOptional.get().getVideo();

        if(videoOptional == null){
            throw new InvalidOperationException("No Video Found");
        }

        final String videoId = videoOptional.getId();

        if(videoId == null || videoId.isEmpty()){
            throw new InvalidOperationException("No Video Found");
        }

        try{
            Resource video = new FileSystemResource("C:/Videos/"+videoId+".mp4");
            ResourceRegion region = getResourceRegion(video, headers);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaTypeFactory.getMediaType(video)
                            .orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(region);
        }catch (RuntimeException ex){
            throw new InvalidOperationException("IO ERROR");
        }


    }

    private ResourceRegion getResourceRegion(Resource video, HttpHeaders headers) {
        long contentLength = 0;
        try {
            contentLength = video.contentLength();
        } catch (IOException e) {
            throw new RuntimeException("IO Error", e);
        }
        List<HttpRange> ranges = headers.getRange();
        if (!ranges.isEmpty()) {
            HttpRange range = ranges.getFirst();
            long start = range.getRangeStart(contentLength);
            long end = range.getRangeEnd(contentLength);
            long rangeLength = Math.min(end - start + 1, contentLength);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = Math.min(1024 * 1024, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }
}
