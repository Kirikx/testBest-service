package ru.testbest.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.dto.test.UserTestQuestionDto;

public interface UserTestService {

  List<UserTestDto> getUserTests(UUID userId);

  UserTestDto getActiveUserTest(UUID userId);

  Optional<QuestionDto> startUserTest(UUID testId, UUID userId);

  Optional<QuestionDto> createUserAnswer(UserTestQuestionDto userTestQuestionDto, UUID userId);

  Optional<QuestionDto> getNextQuestion(UUID userTestId);

  List<UserTestQuestionDto> getFailQuestionsByUserTestId(UUID userTestId);

}
