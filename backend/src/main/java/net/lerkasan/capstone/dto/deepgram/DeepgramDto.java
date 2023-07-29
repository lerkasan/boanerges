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

    public static final String TIME_TO_LIVE_IN_SECONDS = "time_to_live_in_seconds";
    private String comment;

    private String[] scopes;

    @JsonProperty(TIME_TO_LIVE_IN_SECONDS)
    @SerializedName(value = TIME_TO_LIVE_IN_SECONDS)
    private int timeToLiveInSeconds;
}
