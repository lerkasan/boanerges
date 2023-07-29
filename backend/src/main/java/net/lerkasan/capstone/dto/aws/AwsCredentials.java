package net.lerkasan.capstone.dto.aws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.sts.model.Credentials;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsCredentials {
    private String accessKeyId;
    private String secretAccessKey;
    private String sessionToken;

    public AwsCredentials(Credentials credentials) {
        this.accessKeyId = credentials.accessKeyId();
        this.secretAccessKey = credentials.secretAccessKey();
        this.sessionToken = credentials.sessionToken();
    }
}
