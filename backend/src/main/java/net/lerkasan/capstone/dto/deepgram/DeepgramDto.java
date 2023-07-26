package net.lerkasan.capstone.dto.deepgram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DeepgramDto {

    private String comment;

    private String[] scopes;

    @JsonProperty("time_to_live_in_seconds")
    @SerializedName(value = "time_to_live_in_seconds")
    private int timeToLiveInSeconds;
}
