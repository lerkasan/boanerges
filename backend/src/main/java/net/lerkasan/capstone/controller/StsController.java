package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.service.StsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sts.model.Credentials;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/sts")
public class StsController {

    private final StsService stsService;

    @Autowired
    public StsController(StsService stsService) {
        this.stsService = stsService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public AwsCredentials getChatResponse() {
        Credentials creds = stsService.assumeGivenRole("Role-Session-Name");
        return new AwsCredentials(creds);
    }

}
