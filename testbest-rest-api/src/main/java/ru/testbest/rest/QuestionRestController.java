package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.service.impl.common.QuestionServiceImpl;
import ru.testbest.dto.manage.QuestionFullDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/questions")
public class QuestionRestController {

    private final QuestionServiceImpl questionService;

    @GetMapping("/")
    public List<QuestionDto> getQuestions(){
        log.info("Get all question");
        return questionService.getQuestions();
    }

    @GetMapping("/{id}")
    public QuestionDto getQuestion(@PathVariable("id") String id){
        log.info("Get question by id {}", id);
        return questionService.getQuestionById(UUID.fromString(id));
    }

    @GetMapping("/full")
    public List<QuestionFullDto> getFullQuestions() {
        log.info("Get all full question");
        return questionService.getFullQuestions();
    }

    @GetMapping("/full/{id}")
    public QuestionFullDto getFullQuestion(@PathVariable("id") String id) {
        log.info("Get full question by id {}", id);
        return questionService.getQuestionFullById(UUID.fromString(id));
    }

    @PutMapping("/")
    public QuestionFullDto editQuestion(@RequestBody QuestionFullDto questionDto){
        log.info("Edit question {}", questionDto);
        return questionService.editQuestion(questionDto);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") String id){
        log.info("Delete question by id {}", id);
        questionService.deleteQuestionById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public QuestionFullDto createQuestion(@RequestBody QuestionFullDto questionDto){
        log.info("Create question {}", questionDto);
        return questionService.createQuestion(questionDto);
    }

}
