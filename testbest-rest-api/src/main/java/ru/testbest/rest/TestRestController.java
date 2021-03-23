package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.QuestionFullDto;
import ru.testbest.dto.test.TestDto;
import ru.testbest.service.impl.common.TestServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestRestController {

    private final TestServiceImpl testService;

    @GetMapping("/tests")
    public List<TestDto> getQuestions(){
        log.info("Get all question");
        return testService.getTests();
    }

    @GetMapping("/tests/{id}")
    public TestDto getQuestion(@PathVariable("id") String id){
        log.info("Get question by id {}", id);
        return testService.getTestById(UUID.fromString(id));
    }

    @PutMapping("/tests")
    public TestDto editQuestion(@RequestBody TestDto testDto){
        log.info("Edit question {}", testDto);
        return testService.editTest(testDto);
    }

    @DeleteMapping("/tests/{id}")
    public void deleteQuestion(@PathVariable("id") String id){
        log.info("Delete question by id {}", id);
        testService.deleteTestById(UUID.fromString(id));
    }

    @PostMapping("/tests/create")
    public TestDto createQuestion(@RequestBody TestDto testDto){
        log.info("Create question {}", testDto);
        return testService.createTest(testDto);
    }

}
