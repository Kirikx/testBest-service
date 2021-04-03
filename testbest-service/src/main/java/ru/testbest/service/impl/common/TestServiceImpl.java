package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testbest.converter.impl.manage.TestFullConverter;
import ru.testbest.converter.impl.test.TestConverter;
import ru.testbest.dto.manage.TestFullDto;
import ru.testbest.dto.test.TestDto;
import ru.testbest.persistence.dao.TestDao;
import ru.testbest.persistence.entity.Test;
import ru.testbest.service.TestService;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  private final TestDao testDao;

  private final TestConverter testConverter;
  private final TestFullConverter testFullConverter;

  @Override
  @Transactional(readOnly = true)
  public List<TestDto> getTests() {
    return testDao.findAllByIsDeletedFalse().stream()
      .map(testConverter::convertToDto)
      .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<TestFullDto> getFullTests() {
    return testDao.findAll().stream()
      .map(testFullConverter::convertToDto)
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
  @Transactional(readOnly = true)
  public TestFullDto getTestFullById(UUID uuid) {
    return testDao.findById(uuid)
      .map(testFullConverter::convertToDto)
      .orElse(null);
  }

  @Override
  @Transactional
  public TestFullDto createTest(TestFullDto questionDto, UUID userId) {
    if (userId == null && questionDto.getId() != null) {
      throw new RuntimeException();
    }
    questionDto.setAuthorId(userId);
    return testFullConverter.convertToDto(
      testDao.save(
        testFullConverter.convertToEntity(questionDto)));
  }

  @Override
  @Transactional
  public TestFullDto editTest(TestFullDto questionDto) {
    Optional.ofNullable(questionDto.getId())
      .orElseThrow(RuntimeException::new);
    return testFullConverter.convertToDto(
      testDao.save(
        testFullConverter.convertToEntity(questionDto)));
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
