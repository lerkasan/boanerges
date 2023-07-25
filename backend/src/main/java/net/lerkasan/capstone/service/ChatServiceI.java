package net.lerkasan.capstone.service;

public interface ChatServiceI {

    String START_INTERVIEW_PROMPT_TEMPLATE = "You are interviewing a candidate for a %s position. Please generate a list of %d questions about %s. Please prioritize unconventional questions, make the questions longer, do not repeat yourself. Please respond in json format according to provided structure example: {\"questions\": [{\"text\": \"What is a database?\"}, {\"text\": \"What is a transaction?\"}]}";
    String sendPrompt(String prompt);
//    Mono<String> sendPrompt(String prompt);
}
