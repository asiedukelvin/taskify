package com.taskify.taskify.utility;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public class FileUploadUtility {

    public static class FileUploadResult {
        public final String path;
        public final String base64;

        public FileUploadResult(String path, String base64) {
            this.path = path;
            this.base64 = base64;
        }
    }

    public static FileUploadResult saveFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";

        if (file == null || file.isEmpty()) {
            throw new IOException("Cannot upload empty file.");
        }

        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (originalFileName.contains("..") || originalFileName.contains("/") || originalFileName.contains("\\")) {
            throw new IOException("Invalid file name: " + originalFileName);
        }

        String safeFileName = UUID.randomUUID() + "-" + originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path targetPath = uploadPath.resolve(safeFileName).normalize();

        if (!targetPath.toAbsolutePath().startsWith(uploadPath)) {
            throw new IOException("Attempt to store file outside designated directory.");
        }

        byte[] fileBytes = file.getBytes(); // Get file as byte[]
        Files.write(targetPath, fileBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        String base64 = Base64.getEncoder().encodeToString(fileBytes);
        String mimeType = Files.probeContentType(targetPath);
        String dataUri = "data:" + (mimeType != null ? mimeType : "application/octet-stream") + ";base64," + base64;

        return new FileUploadResult("/uploads/" + safeFileName, dataUri);
    }
}
