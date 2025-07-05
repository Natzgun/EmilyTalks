package com.hamuksoft.emilytalks.modules.conversation.infrastructure.client;

import com.hamuksoft.emilytalks.modules.conversation.application.service.ConversationalAgentClient;
import com.hamuksoft.emilytalks.modules.conversation.domain.AgentResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DeepseekAgentClient implements ConversationalAgentClient {
    private final DeepseekClient deepseek;

    public DeepseekAgentClient(DeepseekClient deepseek) {
        this.deepseek = deepseek;
    }

    @Override
    public AgentResponse chat(String prompt) {
        String reply = deepseek.sendMessage(prompt);
        return new AgentResponse(reply, Instant.now());
    }
}
