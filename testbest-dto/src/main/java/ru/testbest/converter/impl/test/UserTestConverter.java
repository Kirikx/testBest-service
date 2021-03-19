package ru.testbest.converter.impl.test;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.testbest.converter.ConverterTest;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.persistence.dao.TestDao;
import ru.testbest.persistence.dao.UserDao;
import ru.testbest.persistence.entity.UserTest;

@Component
@RequiredArgsConstructor
public class UserTestConverter implements ConverterTest<UserTest, UserTestDto> {

  private final UserTestQuestionConverter userTestQuestionConverter;
  private final TestDao testDao;
  private final UserDao userDao;

  @Override
  public UserTestDto convertToDto(UserTest entity) {
    UserTestDto userTestDto = new UserTestDto();
    userTestDto.setId(entity.getId());
    userTestDto.setStarted(entity.getStarted());
    userTestDto.setFinished(entity.getFinished());
    userTestDto.setScore(entity.getScore());
    userTestDto.setIsPassed(entity.getIsPassed());
    userTestDto.setTestId(entity.getTest() == null ? null : entity.getTest().getId());
    userTestDto.setUserId(entity.getUser() == null ? null : entity.getUser().getId());
    userTestDto.setUserTestQuestions(entity.getUserTestQuestions().stream()
        .map(userTestQuestionConverter::convertToDto)
        .collect(Collectors.toSet()));
    return userTestDto;
  }

  @Override
  public UserTest convertToEntity(UserTestDto dto) {
    UserTest userTest = new UserTest();
    userTest.setId(dto.getId());
    userTest.setStarted(dto.getStarted());
    userTest.setFinished(dto.getFinished());
    userTest.setScore(dto.getScore());
    userTest.setIsPassed(dto.getIsPassed());
    userTest.setTest(testDao.findById(dto.getUserId()).orElse(null));
    userTest.setUser(userDao.findById(dto.getUserId()).orElse(null));
    userTest.setUserTestQuestions(dto.getUserTestQuestions().stream()
        .map(userTestQuestionConverter::convertToEntity)
        .collect(Collectors.toSet()));
    return userTest;
  }
}
