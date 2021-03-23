package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.manage.AnswerFullConverter;
import ru.testbest.converter.impl.test.AnswerConverter;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.dto.test.AnswerFullDto;
import ru.testbest.persistence.dao.AnswerDao;
import ru.testbest.persistence.entity.Answer;
import ru.testbest.service.AnswerService;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

  private final AnswerDao answerDao;
  private final AnswerConverter answerConverter;
  private final AnswerFullConverter answerFullConverter;

  @Override
  public List<AnswerDto> getAnswers() {
    return answerDao.findAllByIsDeletedFalse().stream()
        .map(answerConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public AnswerDto getAnswerById(UUID uuid) {
    return answerDao.findByIdAndIsDeletedFalse(uuid)
        .map(answerConverter::convertToDto)
        .orElse(null);
  }

  @Override
  public List<AnswerFullDto> getAnswersFull() {
    return answerDao.findAllByIsDeletedFalse().stream()
        .map(answerFullConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public AnswerFullDto getAnswerFullById(UUID uuid) {
    return answerDao.findByIdAndIsDeletedFalse(uuid)
        .map(answerFullConverter::convertToDto)
        .orElse(null);
  }

  @Override
  public AnswerFullDto createAnswer(AnswerFullDto answerDto) {
    return answerFullConverter.convertToDto(
        answerDao.save(
            answerFullConverter.convertToEntity(answerDto)));
  }

  @Override
  public AnswerFullDto editAnswer(AnswerFullDto answerDto) {
    return answerFullConverter.convertToDto(
        answerDao.save(
            answerFullConverter.convertToEntity(answerDto)));
  }

  @Override
  public void deleteAnswerById(UUID uuid) {
    Optional<Answer> oAnswer = answerDao.findByIdAndIsDeletedFalse(uuid);
    if (oAnswer.isPresent()) {
      Answer answer = oAnswer.get();
      answer.setIsDeleted(true);
      answerDao.save(answer);
    }
  }
}
