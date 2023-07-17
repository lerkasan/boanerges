package net.lerkasan.capstone.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.sts.model.Credentials;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsCredentials {
    private String accessKeyId;
    private String secretAccessKey;
    private String sessionToken;

//    @Value("${cloud.aws.region.static}")
//    private String region;

    public AwsCredentials(Credentials credentials) {
        this.accessKeyId = credentials.accessKeyId();
        this.secretAccessKey = credentials.secretAccessKey();
        this.sessionToken = credentials.sessionToken();
    }
}
