package com.hamuksoft.emilytalks.modules.conversation.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationSetupDTO {
    String scenario;
    String toopic;
}
