package ru.testbest.service;

import java.util.List;
import java.util.Optional;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.dto.test.UserTestQuestionDto;

public interface UserTestService {

  List<UserTestDto> getUserTests(String userId);

  UserTestDto getActiveUserTestByUserId(String userId);

  Optional<QuestionDto> startUserTest(String testId, String userId);

  Optional<QuestionDto> createUserAnswer(UserTestQuestionDto userTestQuestionDto);

  UserTestDto finishUserTest(String userTestId);

}
