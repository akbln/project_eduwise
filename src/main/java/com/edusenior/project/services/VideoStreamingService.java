package com.edusenior.project.services;

import com.edusenior.project.Exceptions.InvalidOperationException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class VideoStreamingService {


    public ResponseEntity<ResourceRegion> getVideo(HttpHeaders headers,String id) {
        try{
            Resource video = new FileSystemResource("C:/Videos/"+id+".mp4");
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
