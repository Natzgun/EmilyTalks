package com.hamuksoft.emilytalks.modules.conversation.application.service;

import java.io.File;

import com.hamuksoft.emilytalks.modules.conversation.domain.UserUtterance;

public interface SpeechToTextClient {
    UserUtterance transcribe(File audioFile);

}
