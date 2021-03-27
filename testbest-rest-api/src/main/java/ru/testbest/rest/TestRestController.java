package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.TestDto;
import ru.testbest.service.impl.common.TestServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/tests")
public class TestRestController {

    private final TestServiceImpl testService;

    @GetMapping("/")
    public List<TestDto> getTests(){
        log.info("Get all tests");
        return testService.getTests();
    }

    @GetMapping("/{id}")
    public TestDto getTest(@PathVariable("id") String id){
        log.info("Get test by id {}", id);
        return testService.getTestById(UUID.fromString(id));
    }

    @PutMapping("/")
    public TestDto editTest(@RequestBody TestDto testDto){
        log.info("Edit test {}", testDto);
        return testService.editTest(testDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable("id") String id){
        log.info("Delete test by id {}", id);
        testService.deleteTestById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public TestDto createTest(@RequestBody TestDto testDto){
        log.info("Create test {}", testDto);
        return testService.createTest(testDto);
    }

}
