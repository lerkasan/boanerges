package net.lerkasan.capstone.service.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.utils.ImmutableMap;

import java.io.*;
import java.net.URI;
import java.time.Duration;

@Slf4j
@Service
public class S3Service {

    public static final String ERROR_UPLOADING_TO_S3 = "Error uploading to S3: {}";
    public static final String HTTPS = "https://";
    public static final String S3_AMAZONAWS_COM = ".s3.amazonaws.com/";
    public static final String ERROR_COPYING_INPUT_STREAM_TO_FILE = "Error copying input stream to file: {}";
    public static final String PRESIGNED_URL = "Presigned URL: ";
    public static final String BUCKET = "bucket";
    public static final String KEY = "key";
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private final S3Utilities s3Utilities;


    @Autowired
    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.s3Utilities = s3Client.utilities();
    }

    public String uploadToS3(InputStream inputStream, String bucketName, String fileName) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromInputStream(inputStream, inputStream.available()));
        } catch (IOException e) {
            log.error(ERROR_UPLOADING_TO_S3, e.getMessage());
        }
        return HTTPS + bucketName + S3_AMAZONAWS_COM + fileName;
    }

    public void copyInputStreamToFile(InputStream input, File file) {
        try (OutputStream output = new FileOutputStream(file)) {
            input.transferTo(output);
        } catch (IOException e) {
            log.error(ERROR_COPYING_INPUT_STREAM_TO_FILE, e.getMessage());
        }
    }

    public String uploadToS3(File file, String bucketName, String fileName) {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                RequestBody.fromFile(file));


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

        log.info(PRESIGNED_URL + presignedGetObjectRequest.url());
        return presignedGetObjectRequest.url().toString();
    }

    public String convertPresignedS3UrlToS3Url(String presignedUrl) {
        URI uri = URI.create(presignedUrl);
        S3Uri s3Uri = s3Utilities.parseUri(uri);
        String bucketName = s3Uri.bucket().orElse("");
        String key = s3Uri.key().orElse("");
        return s3Client.utilities().getUrl(builder -> builder
                        .bucket(bucketName)
                        .key(key))
                .toString();
    }

    public ImmutableMap<String, String> convertS3UrlToBucketAndKey(String s3Url) {
        URI uri = URI.create(s3Url);
        S3Uri s3Uri = s3Utilities.parseUri(uri);
        String bucketName = s3Uri.bucket().orElse("");
        String key = s3Uri.key().orElse("");
        return ImmutableMap.of(BUCKET, bucketName, KEY, key);
    }
}
