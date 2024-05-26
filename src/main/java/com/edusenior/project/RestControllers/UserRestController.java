package com.edusenior.project.RestControllers;

import com.edusenior.project.Exceptions.InvalidOperationException;
import com.edusenior.project.JpaRepositories.UserJpaRepo;
import com.edusenior.project.JpaRepositories.credentials.CredentialsJpaRepository;
import com.edusenior.project.ServerResponses.ServerResponse;
import com.edusenior.project.dataTransferObjects.ProfileDTO;
import com.edusenior.project.dataTransferObjects.UpdateProfileDTO;
import com.edusenior.project.entities.Users.Credentials;
import com.edusenior.project.entities.Users.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class UserRestController {

    private final UserJpaRepo userJpaRepo;
    private final CredentialsJpaRepository credentialsJpaRepository;

    @Autowired
    public UserRestController(UserJpaRepo userJpaRepo, CredentialsJpaRepository credentialsJpaRepository) {
        this.userJpaRepo = userJpaRepo;
        this.credentialsJpaRepository = credentialsJpaRepository;
    }

    @GetMapping("/profile")
    public ProfileDTO retrieveProfile(UsernamePasswordAuthenticationToken auth) {
        Map<String, String> details;
        try {
            details = (Map<String, String>) auth.getDetails();
        } catch (ClassCastException ex) {
            throw new InvalidOperationException(ex.getMessage());
        }
        final String id = details.get("id");
        User u = userJpaRepo.findById(id).orElseThrow(() -> new InvalidOperationException("Invalid User ID"));
        Credentials c = credentialsJpaRepository.findById(id).orElseThrow(() -> new InvalidOperationException("Invalid User ID"));
        ProfileDTO profile = new ProfileDTO();
        profile.setEmail(c.getEmail());
        profile.setName(u.getName());
        if (u.getProfilePicture() == null) {
            try {
                ClassPathResource resource = new ClassPathResource("default.png");
                byte[] defaultPictureBytes = Files.readAllBytes(resource.getFile().toPath());
                profile.setProfilePicture(defaultPictureBytes);
            } catch (IOException e) {
                throw new InvalidOperationException("Could not load default profile picture");
            }
        } else {
            try {
                profile.setProfilePicture(u.getProfilePicture());
            } catch (RuntimeException ex) {
                throw new InvalidOperationException("Error retrieving profile picture");
            }
        }
        return profile;
    }
    @PutMapping("/profile")
    @Transactional
    public ResponseEntity<ServerResponse> updateProfilePicture(UsernamePasswordAuthenticationToken auth, @RequestParam("profilePicture") MultipartFile profilePicture) {
        Map<String, String> details;
        try {
            details = (Map<String, String>) auth.getDetails();
        } catch (ClassCastException ex) {
            throw new InvalidOperationException(ex.getMessage());
        }
        final String id = details.get("id");
        User u = userJpaRepo.findById(id).orElseThrow(() -> new InvalidOperationException("Invalid User ID"));

        if (!profilePicture.isEmpty()) {
            try {
                Blob profilePictureBlob = new SerialBlob(profilePicture.getBytes());
                u.setProfilePicture(profilePictureBlob);
            } catch (SQLException | IOException e) {
                throw new InvalidOperationException("Error setting profile picture");
            }
        }

        try {
            userJpaRepo.saveAndFlush(u);
        } catch (RuntimeException ex) {
            throw new InvalidOperationException("Error saving profile picture");
        }

        return new ResponseEntity<>(new ServerResponse("success", new ArrayList<>()), HttpStatus.OK);
    }
}
