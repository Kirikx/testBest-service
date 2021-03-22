package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.testbest.converter.impl.test.AnswerConverter;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.persistence.dao.AnswerDao;
import ru.testbest.persistence.entity.Answer;
import ru.testbest.service.AnswerService;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

  private final AnswerDao answerDao;
  private final AnswerConverter answerConverter;

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
  public AnswerDto createAnswer(AnswerDto answerDto) {
    return answerConverter.convertToDto(
        answerDao.save(
            answerConverter.convertToEntity(answerDto)));
  }

  @Override
  public AnswerDto editAnswer(AnswerDto answerDto) {
    return answerConverter.convertToDto(
        answerDao.save(
            answerConverter.convertToEntity(answerDto)));
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
