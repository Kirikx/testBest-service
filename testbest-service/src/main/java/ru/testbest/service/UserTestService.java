package ru.testbest.service;

import java.util.List;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.dto.test.UserTestQuestionDto;

public interface UserTestService {

  List<UserTestDto> getUserTests(String userId);

  List<QuestionDto> startUserTestByTestId(String testId);

  QuestionDto createUserAnswer(UserTestQuestionDto userTestQuestionDto);

}
