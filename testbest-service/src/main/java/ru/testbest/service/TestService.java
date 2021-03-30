package ru.testbest.service;

import java.util.List;
import java.util.UUID;
import ru.testbest.dto.test.TestDto;

public interface TestService {

  List<TestDto> getTests();

  TestDto getTestById(UUID uuid);

  void deleteTestById(UUID uuid);

  TestDto createTest(TestDto questionDto, UUID userId);

  TestDto editTest(TestDto questionDto);

  List<TestDto> getTestsByAuthorId(UUID authorId);

}
