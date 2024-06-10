package com.demo.mssql.javaspringbootconnectmssqlsample.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.demo.mssql.javaspringbootconnectmssqlsample.model.User;
import com.demo.mssql.javaspringbootconnectmssqlsample.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadBinaryData() {
        // Retrieve binary data from the database or any other source
        List<User> userList = userRepository.findAll();
        byte[] binaryData = userList.get(0).getBinaryData(); // Assuming you want to download binary data of the first user

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "binaryData.bin"); // Change the filename as needed

        return new ResponseEntity<>(binaryData, headers, HttpStatus.OK);
    }

    @GetMapping("/upload")
    public ResponseEntity<String> uploadBinaryDataToS3() {
        try {
            List<User> userList = userRepository.findAll();
            if (userList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found in the database.");
            }

            byte[] binaryData = userList.get(0).getBinaryData();
            if (binaryData == null || binaryData.length == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Binary data is empty.");
            }

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-1") // Replace with your region
                    .build();

            String bucketName = "binary-upload"; // Replace with your bucket name
            String key = "binaryData.bin"; // Change the key as needed

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(binaryData);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(binaryData.length);

            s3Client.putObject(bucketName, key, byteArrayInputStream, metadata);

            return ResponseEntity.ok("Binary data uploaded to S3 successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload binary data to S3.");
        }
    }
}
