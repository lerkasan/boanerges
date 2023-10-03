package net.lerkasan.capstone.dto.chatgpt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Component
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIncludeProperties({ "model", "messages", "temperature", "n", "stream", "presence_penalty", "frequency_penalty" })
public class ChatRequestBody {

    public static final String PRESENCE_PENALTY = "presence_penalty";
    public static final String FREQUENCY_PENALTY = "frequency_penalty";
    private String model;
    private List<Message> messages;

    @Min(0)
    @Max(2)
    private double temperature;

    @Min(1)
    @JsonProperty("n")
    private int numberOfChoices;

    private boolean stream;

    @JsonIgnore
    private int maxTokens;

    @Min(-2)
    @Max(2)
    @JsonProperty(PRESENCE_PENALTY)
    private double presencePenalty;

    @Min(-2)
    @Max(2)
    @JsonProperty(FREQUENCY_PENALTY)
    private double frequencyPenalty;

    @Autowired
    public ChatRequestBody(@Value("${openai.chatgpt.model}") final String model,
                           @Value("${openai.chatgpt.temperature}") final double temperature,
                           @Value("${openai.chatgpt.number_of_choices}") final int numberOfChoices,
                           @Value("${openai.chatgpt.stream}") final boolean stream,
                           @Value("${openai.chatgpt.max_tokens}") final int maxTokens,
                           @Value("${openai.chatgpt.presence_penalty}") final double presencePenalty,
                           @Value("${openai.chatgpt.frequency_penalty}") final double frequencyPenalty) {
        this.model = model;
        this.temperature = temperature;
        this.numberOfChoices = numberOfChoices;
        this.stream = stream;
        this.maxTokens = maxTokens;
        this.presencePenalty = presencePenalty;
        this.frequencyPenalty = frequencyPenalty;
        this.messages = new ArrayList<>();
    }
}
