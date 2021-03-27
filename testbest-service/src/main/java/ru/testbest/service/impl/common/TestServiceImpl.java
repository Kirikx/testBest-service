package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.test.TestConverter;
import ru.testbest.dto.test.TestDto;
import ru.testbest.persistence.dao.TestDao;
import ru.testbest.persistence.entity.Test;
import ru.testbest.service.TestService;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  private final TestDao testDao;
  private final TestConverter testConverter;

  @Override
  @Transactional(readOnly = true)
  public List<TestDto> getTests() {
    return testDao.findAllByIsDeletedFalse().stream()
        .map(testConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public TestDto getTestById(UUID uuid) {
    return testDao.findByIdAndIsDeletedFalse(uuid)
        .map(testConverter::convertToDto)
        .orElse(null);
  }

  @Override
  @Transactional
  public TestDto createTest(TestDto questionDto) {
    return testConverter.convertToDto(
        testDao.save(
            testConverter.convertToEntity(questionDto)));
  }

  @Override
  @Transactional
  public TestDto editTest(TestDto questionDto) {
    return testConverter.convertToDto(
        testDao.save(
            testConverter.convertToEntity(questionDto)));
  }

  @Override
  @Transactional
  public List<TestDto> getTestsByAuthorId(UUID authorId) {
    return testDao.findAllByAuthorId(authorId).stream()
        .map(testConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void deleteTestById(UUID uuid) {
    Optional<Test> oTest = testDao.findByIdAndIsDeletedFalse(uuid);
    if (oTest.isPresent()) {
      Test test = oTest.get();
      test.setIsDeleted(true);
      testDao.save(test);
    }
  }
}
