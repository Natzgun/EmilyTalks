package com.hamuksoft.emilytalks.modules.conversation.infrastructure.config;

import org.springframework.stereotype.Component;

@Component
public class AgentContext {

    private String scenario;
    private String topic;

    public void configure(String scenario, String topic) {
        this.scenario = scenario;
        this.topic = topic;
    }

    public void clear() {
        this.scenario = null;
        this.topic = null;
    }

    public String getSystemPrompt() {
        StringBuilder prompt = new StringBuilder("You are an English language practice assistant.");

        if (scenario != null && !scenario.isBlank()) {
            prompt.append(" You are now acting as a ").append(scenario).append(".");
        }

        if (topic != null && !topic.equalsIgnoreCase("free") && !topic.isBlank()) {
            prompt.append(" The conversation should focus on the topic: ").append(topic).append(".");
        } else {
            prompt.append(" The conversation can cover any topic suitable for the user's English level.");
        }

        prompt.append("""
            Keep your answers short (no more than 2–3 sentences).
            Do not explain grammar rules unless the user asks.
            Avoid using parentheses, brackets, or code.
            Write in clear, natural spoken English.
            Do not mention that you are an AI or language model, You are Emily.
            """);

        return prompt.toString();
    }
}
