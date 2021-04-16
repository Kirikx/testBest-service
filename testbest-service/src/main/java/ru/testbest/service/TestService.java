package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.manage.TestFullDto;
import ru.testbest.dto.test.TestDto;

public interface TestService {

  List<TestDto> getTests();

  List<TestFullDto> getFullTests();

  TestDto getTestById(UUID uuid);

  TestFullDto getTestFullById(UUID uuid);

  void deleteTestById(UUID uuid);

  TestFullDto createTest(TestFullDto questionDto, UUID userId);

  TestFullDto editTest(TestFullDto questionDto);

  List<TestDto> getTestsByAuthorId(UUID authorId);

  List<TestDto> getTestsByTopicId(UUID topicId);
}
