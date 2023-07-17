package net.lerkasan.capstone.dto.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private String role;
    private String content;
}



//WebClient.builder().filter((request, next) -> {
//        ClientRequest newReuqest = ClientRequest.from(request)
//        .header("Authorization", "YOUR_TOKEN")
//        .build();
//
//        return next.exchange(newRequest);
//        }).build();