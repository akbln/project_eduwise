package com.edusenior.project.services;


import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.ChapterJpaRepository;
import com.edusenior.project.JpaRepositories.VideoJpaRepository;
import com.edusenior.project.JpaRepositories.courses.CourseJpaDAO;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.VideoUploadDTO;
import com.edusenior.project.entities.Chapter;
import com.edusenior.project.entities.Video;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoUploadService {
    @Value("${file.storage-location}")
    private Path rootLocation;

    private final CourseJpaDAO courseJpaDAO;
    private final VideoJpaRepository videoJpaRepository;
    private final ChapterJpaRepository chapterJpaRepository;

    @Autowired
    public VideoUploadService(CourseJpaDAO courseJpaDAO, VideoJpaRepository videoJpaRepository, ChapterJpaRepository chapterJpaRepository) {
        this.courseJpaDAO = courseJpaDAO;
        this.videoJpaRepository = videoJpaRepository;
        this.chapterJpaRepository = chapterJpaRepository;
    }

    @Transactional
    public ResponseEntity<ServerResponse> storeVideo(VideoUploadDTO vDTO) {
        Chapter chapter = validateAndGetChapter(vDTO);
        MultipartFile videoFile = vDTO.getVideo();
        validateFile(videoFile);
        String fileExtension = getFileExtension(videoFile);

        String uniqueFilename = getOrGenerateUniqueFilename(chapter);
        saveVideoFile(videoFile, uniqueFilename, fileExtension);
        Video videoEntity = updateOrSaveVideoEntity(chapter, uniqueFilename);

        return new ResponseEntity<>(new ServerResponse("success", new ArrayList<>()), HttpStatus.OK);
    }

    private Chapter validateAndGetChapter(VideoUploadDTO vDTO) {
        Optional<Chapter> chapterOptional = chapterJpaRepository.findByCourseNameAndChapterNumber(
                vDTO.getCourseName(), Integer.parseInt(vDTO.getChapterNumber()));
        if (chapterOptional.isEmpty()) {
            throw new InvalidOperationException("Could not find specified course");
        }
        return chapterOptional.get();
    }

    private void validateFile(MultipartFile videoFile) {
        if (videoFile.getOriginalFilename() == null || videoFile.isEmpty()) {
            throw new InvalidOperationException("File name can't be empty and file can't be null");
        }
        String fileExtension = getFileExtension(videoFile);
        if (!"mp4".equals(fileExtension)) {
            throw new InvalidOperationException("Can't upload anything other than mp4");
        }
    }

    private String getFileExtension(MultipartFile videoFile) {
        String fileExtension = StringUtils.getFilenameExtension(videoFile.getOriginalFilename());
        if (fileExtension == null || fileExtension.isEmpty()) {
            throw new InvalidOperationException("File extension cannot be empty");
        }
        return fileExtension;
    }

    private String getOrGenerateUniqueFilename(Chapter chapter) {
        if (chapter.getVideo() == null) {
            return generateUniqueFilename();
        } else {
            return chapter.getVideo().getId();
        }
    }

    private String generateUniqueFilename() {
        String uniqueFilename = UUID.randomUUID().toString();
        int attempts = 0;
        while (videoJpaRepository.existsById(uniqueFilename)) {
            if (attempts++ >= 5) {
                throw new IllegalStateException("Failed to generate a unique filename after multiple attempts.");
            }
            uniqueFilename = UUID.randomUUID().toString();
        }
        return uniqueFilename;
    }

    private void saveVideoFile(MultipartFile videoFile, String uniqueFilename, String fileExtension) {
        try {
            Path destinationFile = rootLocation.resolve(Paths.get(uniqueFilename + "." + fileExtension))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new InvalidOperationException("Cannot store file outside current directory.");
            }
            Files.copy(videoFile.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new InvalidOperationException("Failed to store file.");
        }
    }

    private Video updateOrSaveVideoEntity(Chapter chapter, String uniqueFilename) {
        Video videoEntity = chapter.getVideo() == null ? new Video() : chapter.getVideo();
        videoEntity.setId(uniqueFilename);
        videoEntity.setChapter(chapter);
        chapter.setVideo(videoEntity);
        return videoJpaRepository.saveAndFlush(videoEntity);
    }
}

