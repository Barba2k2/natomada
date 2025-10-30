package com.barbatech.natomada.infrastructure.storage;

import com.barbatech.natomada.infrastructure.config.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/**
 * Service for S3 storage operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final AwsProperties awsProperties;

    /**
     * Upload a file to S3
     *
     * @param file      the file to upload
     * @param folder    the folder/prefix in S3 (e.g., "cars", "avatars")
     * @return the S3 key of the uploaded file
     */
    public String uploadFile(MultipartFile file, String folder) {
        try {
            String fileName = generateFileName(file.getOriginalFilename());
            String key = folder + "/" + fileName;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucketName())
                .key(key)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            log.info("File uploaded successfully to S3: {}", key);
            return key;

        } catch (IOException e) {
            log.error("Error uploading file to S3", e);
            throw new RuntimeException("Erro ao fazer upload do arquivo", e);
        }
    }

    /**
     * Delete a file from S3
     *
     * @param key the S3 key of the file to delete
     */
    public void deleteFile(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucketName())
                .key(key)
                .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("File deleted successfully from S3: {}", key);

        } catch (S3Exception e) {
            log.error("Error deleting file from S3: {}", key, e);
            throw new RuntimeException("Erro ao deletar arquivo do S3", e);
        }
    }

    /**
     * Generate a presigned URL for an S3 object (valid for 7 days)
     *
     * @param key the S3 key
     * @return the presigned URL
     */
    public String generatePresignedUrl(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucketName())
                .key(key)
                .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(7))
                .getObjectRequest(getObjectRequest)
                .build();

            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
            return presignedRequest.url().toString();

        } catch (S3Exception e) {
            log.error("Error generating presigned URL for key: {}", key, e);
            throw new RuntimeException("Erro ao gerar URL do arquivo", e);
        }
    }

    /**
     * Get public URL for an S3 object (if bucket allows public access)
     *
     * @param key the S3 key
     * @return the public URL
     */
    public String getPublicUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
            awsProperties.getS3().getBucketName(),
            awsProperties.getS3().getRegion(),
            key);
    }

    /**
     * Generate a unique file name
     */
    private String generateFileName(String originalFileName) {
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }
}
