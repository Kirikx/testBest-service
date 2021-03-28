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
import ru.testbest.dto.test.TestDto;
import ru.testbest.service.impl.common.TestServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestRestController {

    private final TestServiceImpl testService;

    @GetMapping("/tests")
    public List<TestDto> getTests() {
        log.info("Get all tests");
        return testService.getTests();
    }

    @GetMapping("/tests/{id}")
    public TestDto getTest(@PathVariable("id") String id) {
        log.info("Get test by id {}", id);
        return testService.getTestById(UUID.fromString(id));
    }

    @PutMapping("/tests")
    public TestDto editTest(@RequestBody TestDto testDto) {
        log.info("Edit test {}", testDto);
        return testService.editTest(testDto);
    }

    @DeleteMapping("/tests/{id}")
    public void deleteTest(@PathVariable("id") String id) {
        log.info("Delete test by id {}", id);
        testService.deleteTestById(UUID.fromString(id));
    }

    @PostMapping("/tests/create")
    public TestDto createTest(@RequestBody TestDto testDto) {
        log.info("Create test {}", testDto);
        return testService.createTest(testDto);
    }

}
