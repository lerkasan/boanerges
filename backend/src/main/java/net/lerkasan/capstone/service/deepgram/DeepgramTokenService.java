package net.lerkasan.capstone.service.deepgram;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lerkasan.capstone.dto.deepgram.DeepgramDto;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class DeepgramTokenService implements DeepgramTokenServiceI {

    @Value("${deepgram.project.id}")
    private String projectId;

    @Value("${deepgram.api.key}")
    private String apiKey;

    @Value("${deepgram.api.url}")
    private String apiUrl;

    @Value("${deepgram.key.ttl.seconds}")
    private int ttlSeconds;

    @Override
    public String generateTemporaryToken() throws IOException {

        DeepgramDto deepgramDto = new DeepgramDto("Temporarily generated token", new String[]{"member"}, ttlSeconds);
//        DeepgramDto deepgramDto = new DeepgramDto("Temporarily generated token", new String[]{"project:read", "project:write"}, ttlSeconds);
        String json = new Gson().toJson(deepgramDto);
        RequestBody body = RequestBody.create(json, okhttp3.MediaType.parse("application/json; charset=utf-8"));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Token " + apiKey)
                .build();

//        try (Response response = client.newCall(request).execute()) {
//            return response.body();
//        } catch (IOException e) {
//            log.error("Error while generating temporary token for Deepgram: {}", e.getMessage());
//        }
//        return ResponseBody.;

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
