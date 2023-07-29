package net.lerkasan.capstone.service.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;
import software.amazon.awssdk.services.sts.model.StsException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Slf4j
@Service
public class StsService {

    public static final String THE_TOKEN = "The token ";
    public static final String EXPIRES_ON = "  expires on ";
    public static final String ERROR_ASSUMING_STS_ROLE = "Error assuming sts role: {}";
    private final StsClient stsClient;

    @Value("${aws.sts.roleArn}")
    private String roleArn;

    @Autowired
    public StsService(StsClient stsClient) {
        this.stsClient = stsClient;
    }

    public Credentials assumeGivenRole(String roleSessionName) {
        Credentials creds = null;
        try {
            AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
                    .roleArn(roleArn)
                    .roleSessionName(roleSessionName)
                    .build();

            AssumeRoleResponse roleResponse = stsClient.assumeRole(roleRequest);
            creds = roleResponse.credentials();
            // Display the time when the temp creds expire.
            Instant exTime = creds.expiration();
            String tokenInfo = creds.sessionToken();

            // Convert the Instant to readable date.
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                            .withLocale( Locale.US)
                            .withZone( ZoneId.systemDefault() );
            formatter.format( exTime );
            log.info(THE_TOKEN + tokenInfo + EXPIRES_ON + exTime );
        } catch (StsException e) {
            log.error(ERROR_ASSUMING_STS_ROLE, e.getMessage());
        }
        return creds;
    }


}
