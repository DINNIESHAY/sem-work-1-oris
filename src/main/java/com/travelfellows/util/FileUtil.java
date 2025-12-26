package com.travelfellows.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUtil {

    private FileUtil() {}

    public static String processUploadedImage(Part filePart, String uploadDirectory, Long userId) throws IOException, ServletException {

        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }

        String contentType = filePart.getContentType();
        if (!contentType.startsWith("image/")) {
            throw new ServletException("Разрешены только изображения");
        }

        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = "";
        if (originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = "avatar_" + userId + "_" + UUID.randomUUID() + fileExtension;

        Path filePath = uploadPath.resolve(fileName);
        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }
}