package com.hamuksoft.emilytalks.modules.conversation.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgentResponse {
    private final String text;
    private final Instant timestamp;

}
