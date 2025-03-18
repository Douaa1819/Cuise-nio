package com.youcode.cuisenio.features.recipe.service.impl;

import com.youcode.cuisenio.features.auth.util.FileStorageProperties;
import com.youcode.cuisenio.features.recipe.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Qualifier("fileStorageProperties") FileStorageProperties properties) {
        System.out.println("UploadDir: " + properties.getUploadDir()); // Debugging
        this.fileStorageLocation = Paths.get(properties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create directory for uploaded files.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null; // Return null if no file is provided
        }

        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Check for invalid characters
        if (originalFileName.contains("..")) {
            throw new FileStorageException("Filename contains invalid path sequence: " + originalFileName);
        }

        // Extract file extension
        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileExtension = originalFileName.substring(dotIndex);
        }

        // Generate a unique filename (UUID + original name without extension)
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName.replaceAll("\\s+", "_");

        try {
            // Define the target file location
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);

            // Copy the file to the target location (overwrite if exists)
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + uniqueFileName, ex);
        }
    }
}