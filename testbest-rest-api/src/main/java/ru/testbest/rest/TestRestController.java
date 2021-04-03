package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.admin.security.UserDetailsImpl;
import ru.testbest.dto.manage.TestFullDto;
import ru.testbest.dto.test.TestDto;
import ru.testbest.dto.test.UserTestDto;
import ru.testbest.service.UserTestService;
import ru.testbest.service.impl.common.TestServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/tests")
public class TestRestController {

    private final TestServiceImpl testService;
    private final UserTestService userTestService;

    @GetMapping
    public List<TestDto> getTests(){
        log.info("Get all tests");
        return testService.getTests();
    }

    @GetMapping("/{id}")
    public TestDto getTest(@PathVariable("id") String id){
        log.info("Get test by id {}", id);
        return testService.getTestById(UUID.fromString(id));
    }

    @PutMapping
    public TestFullDto editTest(@RequestBody TestFullDto testDto) {
        log.info("Edit test {}", testDto);
        return testService.editTest(testDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable("id") String id){
        log.info("Delete test by id {}", id);
        testService.deleteTestById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public TestFullDto createTest(@RequestBody TestFullDto testDto, Authentication authentication) {
        log.info("Create test {}", testDto);
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        return testService.createTest(testDto, currentUser.getId());
    }

    @GetMapping("/user/{id}")
    public List<UserTestDto> getAllTestOfUser(@PathVariable("id") UUID id) {
        log.info("Get all test for user {}", id);
        return userTestService.getUserTests(id);
    }

    @GetMapping("/user")
    public List<UserTestDto> getAllTestCurrentUser(Authentication authentication) {
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        log.info("Get all test for user {}", currentUser.getId());
        return userTestService.getUserTests(currentUser.getId());
    }

}
