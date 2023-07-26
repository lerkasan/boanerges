package net.lerkasan.capstone.service.deepgram;

import java.io.IOException;

public interface DeepgramTokenServiceI {
    String generateTemporaryToken() throws IOException;
}
