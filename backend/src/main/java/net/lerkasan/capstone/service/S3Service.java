package net.lerkasan.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadToS3(InputStream inputStream, String bucketName, String fileName) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromInputStream(inputStream, inputStream.available()));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        s3Client.close();
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }

    public void copyInputStreamToFile(InputStream input, File file) {
        try (OutputStream output = new FileOutputStream(file)) {
            input.transferTo(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    }

    public String uploadToS3(File file, String bucketName, String fileName) {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                RequestBody.fromFile(file));
        //        s3Client.close();
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }
}
