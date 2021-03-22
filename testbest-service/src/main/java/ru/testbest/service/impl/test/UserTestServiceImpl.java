package ru.testbest.service.impl.test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.test.UserTestConverter;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.dto.test.UserTestQuestionDto;
import ru.testbest.persistence.dao.UserTestDao;
import ru.testbest.service.UserTestService;

@Service
@RequiredArgsConstructor
public class UserTestServiceImpl implements UserTestService {

  private final UserTestDao userTestDao;
  private final UserTestConverter userTestConverter;

  @Override
  public List<UserTestDto> getUserTests(String userId) {
    return userTestDao.findAllByUserId(UUID.fromString(userId)).stream()
        .map(userTestConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<QuestionDto> startUserTestByTestId(String testId) {
    return null;
  }

  @Override
  public QuestionDto createUserAnswer(UserTestQuestionDto userTestQuestionDto) {
    return null;
  }
}
