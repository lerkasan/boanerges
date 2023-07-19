package net.lerkasan.capstone.service.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.*;
import java.time.Duration;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Autowired
    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
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



//        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        return presignS3Url(bucketName, fileName);
    }

    public String presignS3Url(String bucketName, String fileName) {
        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();

        // Create a GetObjectPresignRequest to specify the signature duration
        GetObjectPresignRequest getObjectPresignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        // Generate the presigned request
        PresignedGetObjectRequest presignedGetObjectRequest =
                s3Presigner.presignGetObject(getObjectPresignRequest);

        // Log the presigned URL, for example.
        System.out.println("Presigned URL: " + presignedGetObjectRequest.url());
        return  presignedGetObjectRequest.url().toString();
    }
}
