package com.hamuksoft.emilytalks.modules.conversation.application.service;

import com.hamuksoft.emilytalks.modules.conversation.domain.AgentResponse;

public interface ConversationalAgentClient {
    AgentResponse chat(String prompt);
}
