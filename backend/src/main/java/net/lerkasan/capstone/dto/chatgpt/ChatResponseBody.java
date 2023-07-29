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
    public static final String FINISH_REASON = "finish_reason";
    public static final String PROMPT_TOKENS = "prompt_tokens";
    public static final String COMPLETION_TOKENS = "completion_tokens";
    public static final String TOTAL_TOKENS = "total_tokens";
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

        @JsonProperty(FINISH_REASON)
        private String finishReason;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Usage {

        @JsonProperty(PROMPT_TOKENS)
        private int promptTokens;

        @JsonProperty(COMPLETION_TOKENS)
        private int completionTokens;

        @JsonProperty(TOTAL_TOKENS)
        private int totalTokens;
    }
}