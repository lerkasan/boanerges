package net.lerkasan.capstone.service.aws;


import net.lerkasan.capstone.service.SpeechServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.Engine;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;

import java.io.InputStream;

@Service
public class PollyService implements SpeechServiceI {

    private final PollyClient pollyClient;
    @Autowired
    public PollyService(PollyClient pollyClient) {
        this.pollyClient = pollyClient;
    }

    public InputStream synthesizeSpeech(String text, String voiceId, OutputFormat format) {
        SynthesizeSpeechRequest synthReq = SynthesizeSpeechRequest.builder()
                .text(text)
                .voiceId(voiceId)
                .outputFormat(format)
                .engine(Engine.NEURAL)
                .build();

        return pollyClient.synthesizeSpeech(synthReq);
    }
}
