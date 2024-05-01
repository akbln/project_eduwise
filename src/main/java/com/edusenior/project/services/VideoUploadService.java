package com.edusenior.project.services;


import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.VideoJpaRepository;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.VideoUploadDTO;
import com.edusenior.project.entities.Video;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class VideoUploadService {
    @Value("${file.storage-location}")
    private Path rootLocation;

    private VideoJpaRepository videoJpaRepository;

    @Autowired
    public VideoUploadService(VideoJpaRepository videoJpaRepository) {
        this.videoJpaRepository = videoJpaRepository;
    }

    @Transactional
    public ResponseEntity<ServerResponse> storeVideo(VideoUploadDTO vDTO) {
        MultipartFile videoFile = vDTO.getVideo();
        try {
            if(videoFile.getOriginalFilename() == null){
                throw new InvalidOperationException("File name can't be empty");
            }
            if (videoFile.isEmpty()) {
                throw new InvalidOperationException("Failed to store empty file.");
            }
            String fileExtension = StringUtils.getFilenameExtension(videoFile.getOriginalFilename());
            if (fileExtension == null || fileExtension.isEmpty()){
                throw new InvalidOperationException("Failed to store empty file.");
            }
            if(!fileExtension.equals("mp4")){
                throw new InvalidOperationException("Can't upload anything other than mp4");
            }
            String uniqueFilename = UUID.randomUUID().toString();
            int attempts = 0;
            while (videoJpaRepository.existsById(uniqueFilename)) {
                if (attempts++ >= 5) {
                    throw new IllegalStateException("Failed to generate a unique filename after multiple attempts.");
                }
                uniqueFilename = UUID.randomUUID().toString();
            }


            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(uniqueFilename +"." + fileExtension))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new InvalidOperationException(
                        "Cannot store file outside current directory.");
            }

            Video videoEntity = new Video();
            videoEntity.setId(uniqueFilename);
            videoEntity.setTitle(vDTO.getTitle());
            videoEntity.setDescription(vDTO.getDescription());

            videoJpaRepository.saveAndFlush(videoEntity);

            try (InputStream inputStream = videoFile.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new InvalidOperationException("Failed to store file.");
        }
        catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Constraint violation - cannot save the entity.", e);
        } catch (PersistenceException e) {
            throw new RuntimeException("Persistence error occurred.", e);
        } catch (TransactionSystemException e) {
            throw new RuntimeException("Transaction system error occurred.", e);
        }
        return new ResponseEntity<ServerResponse>(new ServerResponse("success",new ArrayList<>()), HttpStatus.OK);
    }
}
