package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.dto.aws.AwsCredentials;
import net.lerkasan.capstone.service.aws.StsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sts.model.Credentials;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(StsController.STS_ENDPOINT)
public class StsController {

    public static final String STS_ENDPOINT = "/api/v1/sts";
    public static final String ROLE_SESSION_NAME = "Role-Session-Name";
    private final StsService stsService;

    @Autowired
    public StsController(StsService stsService) {
        this.stsService = stsService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public AwsCredentials getChatResponse() {
        Credentials creds = stsService.assumeGivenRole(ROLE_SESSION_NAME);
        return new AwsCredentials(creds);
    }

}
