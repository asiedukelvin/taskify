package com.taskify.taskify.controller;

import com.taskify.taskify.model.ProfilePic;
import com.taskify.taskify.service.ProfilePicService;
import com.taskify.taskify.utility.FileUploadUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/uploads")
public class ProfilePicController {

    @Autowired
    private ProfilePicService profilePicService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfilePic(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            FileUploadUtility.FileUploadResult result = FileUploadUtility.saveFile(file);
            // Optionally call your service to associate this file with the user
            ProfilePic updated = profilePicService.uploadPic(id, result.base64); // Assuming you save base64
            Map<String, String> response = new HashMap<>();
            response.put("userId", id);
            response.put("base64", result.base64);
            response.put("path", result.path);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfilePic(@PathVariable String id) {
        String base64Image = profilePicService.getProfilePicBase64(id);

        if (base64Image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Profile picture not found for user ID: " + id);
        }

        return ResponseEntity.ok(base64Image);
    }



}
