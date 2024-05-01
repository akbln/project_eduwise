package com.edusenior.project.dataTransferObjects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoValidator implements ConstraintValidator<ValidVideo, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile video, ConstraintValidatorContext context) {
        if (video == null) return false;
        if(video.getOriginalFilename() == null || video.getOriginalFilename().isEmpty()) return false;
//        if(!isValidFileName(video.getOriginalFilename())) return  false;
        if (video.getSize() > 2000097152) return false;  // Size check
        String contentType = video.getContentType();
        return contentType != null && contentType.startsWith("video");
    }
    private static boolean isValidFileName(String fileName) {
        String nameRegex = "^[a-zA-Z0-9]$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
}
