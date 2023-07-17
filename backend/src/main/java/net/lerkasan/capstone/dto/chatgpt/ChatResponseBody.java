package net.lerkasan.capstone.dto.chatgpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatResponseBody {
    private String id;
    private String object;
    private long created;
    private String model;
    private Usage usage;
    private List<Choice> choices;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Choice {
        private int index;
        private Message message;
//        private Delta delta;

        @JsonProperty("finish_reason")
        private String finishReason;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Usage {

        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;
    }

//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Data
//    public class Delta {
//        private String content;
//    }
}