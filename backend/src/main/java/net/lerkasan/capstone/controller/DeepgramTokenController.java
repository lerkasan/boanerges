package net.lerkasan.capstone.controller;

import net.lerkasan.capstone.service.deepgram.DeepgramTokenServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(DeepgramTokenController.DEEPGRAM_TOKEN_ENDPOINT)
public class DeepgramTokenController {

    public static final String DEEPGRAM_TOKEN_ENDPOINT = "/api/v1/deepgram/token";
    private final DeepgramTokenServiceI deepgramTokenService;

    @Autowired
    public DeepgramTokenController(DeepgramTokenServiceI deepgramTokenService) {
        this.deepgramTokenService = deepgramTokenService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public String getChatResponse() throws IOException {
            return deepgramTokenService.generateTemporaryToken();
    }
}
