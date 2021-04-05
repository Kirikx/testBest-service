package ru.testbest.rest;

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
import ru.testbest.service.UserTestService;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user-test")
public class UserTestRestController {

    private final UserTestService userTestService;

    @GetMapping
    public List<UserTestDto> getUserTests(Authentication authentication) {
      UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
        log.info("Get all user tests by userId {}", currentUser.getId());
        return userTestService.getUserTests(currentUser.getId());
    }

    @GetMapping("/active")
    public UserTestDto getActiveUserTest(Authentication authentication) {
      UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
        log.info("Get active user test by userId {}", currentUser.getId());
        return userTestService.getActiveUserTest(currentUser.getId());
    }

    @GetMapping("/test/{id}")
    public QuestionDto startUserTest(@PathVariable("id") String testId,
        Authentication authentication) {
      UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
        log.info("Start user test by testId {} and userId {}", testId, currentUser.getId());
        return userTestService.startUserTest(UUID.fromString(testId), currentUser.getId())
            .orElse(null);
    }

    @PostMapping("/create-answer")
    public QuestionDto createUserAnswer(@RequestBody UserTestQuestionDto userTestQuestionDto,
        Authentication authentication) {
      UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
        log.info("Create user answer {} by userId {}", userTestQuestionDto, currentUser.getId());
        return userTestService.createUserAnswer(userTestQuestionDto, currentUser.getId())
            .orElse(null);
    }

    @GetMapping("/next-question")
    public QuestionDto getNextQuestion(Authentication authentication) {
      UserDetailsDto currentUser = (UserDetailsDto) authentication.getPrincipal();
      UserTestDto activeUserTest = userTestService.getActiveUserTest(currentUser.getId());
        log.info("Next question by userTestId {} and userId {}", activeUserTest.getId(),
            currentUser.getId());
        return userTestService.getNextQuestion(activeUserTest.getId())
            .orElse(null);
    }

    @GetMapping("/{id}/fails")
    public List<UserTestQuestionDto> getFailQuestionsByUserTestId(
        @PathVariable("id") String userTestId) {
        log.info("Get fails question by userTestId {}", userTestId);
        return userTestService.getFailQuestionsByUserTestId(UUID.fromString(userTestId));
    }

}
