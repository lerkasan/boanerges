package net.lerkasan.capstone.dto.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatResponseWithAudio {
    private String text;
    private String audioUrl;
}