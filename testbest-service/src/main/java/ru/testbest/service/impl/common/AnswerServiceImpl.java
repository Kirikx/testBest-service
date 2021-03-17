package ru.testbest.service.impl.common;

import java.util.List;
import java.util.Optional;
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
    return answerDao.findAll().stream()
        .map(answerConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public AnswerDto getAnswerById(String uuid) {
    Answer answer = answerDao.findById(uuid).orElseThrow(RuntimeException::new);
    return answerConverter.convertToDto(answer);
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
  public void deleteAnswerById(String uuid) {
    Optional<Answer> oAnswer = answerDao.findById(uuid);
    if (oAnswer.isPresent()) {
      Answer answer = oAnswer.get();
      answer.setDeleted(true);
      answerDao.save(answer);
    }
  }
}
