package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.TestDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.service.UserTestService;
import ru.testbest.service.impl.common.TestServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/tests")
@RestController
public class TestRestController {

    private final TestServiceImpl testService;
    private final UserTestService userTestService;

    @GetMapping()
    public List<TestDto> getQuestions(){
        log.info("Get all tests");
        return testService.getTests();
    }

    @GetMapping()
    public TestDto getQuestion(@RequestParam String id){
        log.info("Get test by id {}", id);
        return testService.getTestById(UUID.fromString(id));
    }

    @PutMapping()
    public TestDto editQuestion(@RequestBody TestDto testDto){
        log.info("Edit test {}", testDto);
        return testService.editTest(testDto);
    }

    @DeleteMapping()
    public void deleteQuestion(@RequestParam String id){
        log.info("Delete test by id {}", id);
        testService.deleteTestById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public TestDto createQuestion(@RequestBody TestDto testDto){
        log.info("Create test {}", testDto);
        return testService.createTest(testDto);
    }

    @GetMapping("/user")
    public List<UserTestDto> getAllTestOfUser(@RequestParam UUID id){
        log.info("Get all test for user {}", id);
        return userTestService.getUserTests(id);
    }

}
