package com.hamuksoft.emilytalks.modules.conversation.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationDTO {
    private final String userUtterance;
    private final String agentResponse;
}
