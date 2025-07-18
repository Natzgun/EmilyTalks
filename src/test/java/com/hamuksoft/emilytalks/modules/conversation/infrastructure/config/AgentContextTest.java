package com.hamuksoft.emilytalks.modules.conversation.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentContextTest {

    private AgentContext agentContext;

    @BeforeEach
    void setUp() {
        agentContext = new AgentContext();
    }

    @Test
    void testConfigureAndGetSystemPrompt_WithScenarioAndTopic() {
        agentContext.configure("teacher", "travel");
        String prompt = agentContext.getSystemPrompt();

        assertTrue(prompt.contains("You are now acting as a teacher."));
        assertTrue(prompt.contains("The conversation should focus on the topic: travel."));
    }

    @Test
    void testConfigureAndGetSystemPrompt_WithScenarioOnly() {
        agentContext.configure("doctor", null);
        String prompt = agentContext.getSystemPrompt();

        assertTrue(prompt.contains("You are now acting as a doctor."));
        assertTrue(prompt.contains("The conversation can cover any topic suitable for the user's English level."));
    }

    @Test
    void testConfigureAndGetSystemPrompt_WithFreeTopic() {
        agentContext.configure("guide", "free");
        String prompt = agentContext.getSystemPrompt();

        assertTrue(prompt.contains("You are now acting as a guide."));
        assertTrue(prompt.contains("The conversation can cover any topic suitable for the user's English level."));
    }

    @Test
    void testClear() {
        agentContext.configure("teacher", "travel");
        agentContext.clear();

        String prompt = agentContext.getSystemPrompt();
        assertFalse(prompt.contains("You are now acting as a teacher."));
        assertFalse(prompt.contains("The conversation should focus on the topic: travel."));
        assertTrue(prompt.contains("The conversation can cover any topic suitable for the user's English level."));
    }

    @Test
    void testGetSystemPrompt_DefaultBehavior() {
        String prompt = agentContext.getSystemPrompt();

        assertTrue(prompt.contains("You are an English language practice assistant."));
        assertTrue(prompt.contains("The conversation can cover any topic suitable for the user's English level."));
    }
}