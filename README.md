```
├───main
│   ├───java
│   │   └───com
│   │       └───hamuksoft
│   │           └───emilytalks
│   │               │   EmilytalksApplication.java
│   │               │
│   │               ├───apps
│   │               │   └───backend
│   │               │           ConversationController.java
│   │               │           UserController.java
│   │               │
│   │               └───modules
│   │                   ├───conversation
│   │                   │   ├───application
│   │                   │   │   ├───dto
│   │                   │   │   │       ConversationDTO.java
│   │                   │   │   │       ConversationSetupDTO.java
│   │                   │   │   │       UserUtteranceDTO.java
│   │                   │   │   │
│   │                   │   │   └───service
│   │                   │   │           ConversationalAgentClient.java
│   │                   │   │           ConversationUseCase.java
│   │                   │   │           SpeechToTextClient.java
│   │                   │   │           TextToSpeechClient.java
│   │                   │   │
│   │                   │   ├───domain
│   │                   │   │       AgentResponse.java
│   │                   │   │       UserUtterance.java
│   │                   │   │
│   │                   │   └───infrastructure
│   │                   │       ├───client
│   │                   │       │       AssemblyAIClient.java
│   │                   │       │       AssemblyAISpeachToTextClient.java
│   │                   │       │       ConversationClient.java
│   │                   │       │       DeepseekAgentClient.java
│   │                   │       │       DeepseekClient.java
│   │                   │       │
│   │                   │       └───config
│   │                   │               AgentContext.java
│   │                   │
│   │                   ├───shared
│   │                   │   ├───config
│   │                   │   │       SecurityConfig.java
│   │                   │   │       WebConfig.java
│   │                   │   │
│   │                   │   └───security
│   │                   │           JwtUtil.java
│   │                   │
│   │                   └───user
│   │                       ├───application
│   │                       │   │   LoginUser.java
│   │                       │   │   RegisterUser.java
│   │                       │   │   SearchUser.java
│   │                       │   │
│   │                       │   └───dto
│   │                       │           UserDTO.java
│   │                       │
│   │                       ├───domain
│   │                       │       IUserRepository.java
│   │                       │       Role.java
│   │                       │       User.java
│   │                       │       UserId.java
│   │                       │
│   │                       └───infrastructure
│   │                               ISpringDataUserRepository.java
│   │                               JpaUserRepository.java
│   │                               UserEntity.java
│   │                               UserMapper.java
│   │
│   └───resources
│           application.yml
│
└───test
    └───java
        └───com
            └───hamuksoft
                └───emilytalks
                    │   EmilytalksApplicationTests.java
                    │
                    └───modules
                        ├───conversation
                        │   ├───application
                        │   │   └───service
                        │   │           ConversationUseCaseTest.java
                        │   │
                        │   └───infrastructure
                        │       ├───client
                        │       │       DeepseekAgentClientTest.java
                        │       │       DeepseekClientTest.java
                        │       │
                        │       └───config
                        │               AgentContextTest.java
                        │
                        └───user
                            ├───application
                            │       LoginUserTest.java
                            │       RegisterUserTest.java
                            │       SearchUserTest.java
                            │
                            └───infrastructure
                                    JpaUserRepositoryTest.java
```
