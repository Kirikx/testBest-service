package ru.testbest.rest;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.admin.UserDetailsDto;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.dto.test.UserTestQuestionDto;
import ru.testbest.exception.custom.CustomNotFoundException;
import ru.testbest.service.UserTestService;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user-test")
public class UserTestRestController {

  private final UserTestService userTestService;

  @GetMapping
  @ApiOperation(value = "Возвращает список всех пройденных (включая текущие) тестов", tags = "User test")
  public List<UserTestDto> getUserTests(Authentication authentication) {
    UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
    log.info("Get all user tests by userId {}", currentUser.getId());
    return userTestService.getUserTests(currentUser.getId());
  }

  @GetMapping("/active")
  @ApiOperation(value = "Возвращает последний активный тест, если нет то 404", tags = "User test")
  public UserTestDto getActiveUserTest(Authentication authentication) {
    UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
    log.info("Get active user test by userId {}", currentUser.getId());
    return userTestService.getActiveUserTest(currentUser.getId());
  }

  @GetMapping("/test/{id}")
  @ApiOperation(value = "Создает новый пользовательский тест. В карестве параметра принимает id теста", tags = "User test")
  public UserTestDto startUserTest(@PathVariable("id") String testId,
      Authentication authentication) {
    UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
    log.info("Start user test by testId {} and userId {}", testId, currentUser.getId());
    return userTestService.startUserTest(UUID.fromString(testId), currentUser.getId());
  }

  @PostMapping("/create-answer")
  @ApiOperation(value = "Сохраняет пользовательский ответ на вопрос из теста", tags = "User test")
  public QuestionDto createUserAnswer(@RequestBody UserTestQuestionDto userTestQuestionDto,
      Authentication authentication) {
    UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
    log.info("Create user answer {} by userId {}", userTestQuestionDto, currentUser.getId());
    return userTestService.createUserAnswer(userTestQuestionDto, currentUser.getId())
        .orElseThrow(CustomNotFoundException::new);
  }

  @GetMapping("/test/{id}/next-question")
  @ApiOperation(value = "Возвращает следующий вопрос теста по id или 404 если его нет", tags = "User test")
  public QuestionDto getNextQuestion(@PathVariable("id") UUID userTestId) {
    log.info("Next question by userTestId {}", userTestId);
    return userTestService.getNextQuestion(userTestId)
        .orElseThrow(CustomNotFoundException::new);
  }

  @GetMapping("/{id}/fails")
  @ApiOperation(value = "Возвращает список всех неверных пользовательских ответов на тест", tags = "User test")
  public List<UserTestQuestionDto> getFailQuestionsByUserTestId(
      @PathVariable("id") String userTestId) {
    log.info("Get fails question by userTestId {}", userTestId);
    return userTestService.getFailQuestionsByUserTestId(UUID.fromString(userTestId));
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Возвращает пользовательский тест по id, 404 если его нет", tags = "User test")
  public UserTestDto getUserTest(@PathVariable("id") UUID userTestId) {
    log.info("Get user test by id {}", userTestId);
    return userTestService.getUserTestById(userTestId);
  }

}
