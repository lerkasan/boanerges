package net.lerkasan.capstone.service.aws;


import net.lerkasan.capstone.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.Engine;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.InputStream;

@Service
public class PollyService implements SpeechService {

    private final PollyClient pollyClient;
    private final S3Client s3Client;

    @Autowired
    public PollyService(PollyClient pollyClient, S3Client s3Client) {
        this.pollyClient = pollyClient;
        this.s3Client = s3Client;
    }

    public InputStream synthesizeSpeech(String text, String voiceId, OutputFormat format) {
        SynthesizeSpeechRequest synthReq = SynthesizeSpeechRequest.builder()
                .text(text)
                .voiceId(voiceId)
                .outputFormat(format)
                .engine(Engine.NEURAL)
                .build();

        ResponseInputStream<SynthesizeSpeechResponse> synthRes = pollyClient.synthesizeSpeech(synthReq);
//        pollyClient.close();
        return synthRes;
    }
}
