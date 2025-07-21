package com.taskify.taskify.utility;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

public class FileUploadUtility {

    public static String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";

        // Null and empty check for file
        if (file == null || file.isEmpty()) {
            throw new IOException("Cannot upload empty file.");
        }

        // Clean and sanitize filename
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (originalFileName.contains("..") || originalFileName.contains("/") || originalFileName.contains("\\")) {
            throw new IOException("Invalid file name: " + originalFileName);
        }

        // Replace any sketchy characters just in case
        String safeFileName = UUID.randomUUID() + "-" + originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");

        // Normalize and secure the upload path
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        Path targetPath = uploadPath.resolve(safeFileName).normalize();

        // Path traversal protection
        if (!targetPath.toAbsolutePath().startsWith(uploadPath)) {
            throw new IOException("Attempt to store file outside designated directory.");
        }

        // Save the file
        //noinspection JvmTaintAnalysis
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + safeFileName;
    }
}
