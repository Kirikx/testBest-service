package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.dto.test.QuestionFullDto;
import ru.testbest.service.impl.common.QuestionServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/questions")
@RestController
public class QuestionRestController {

    private final QuestionServiceImpl questionService;

    @GetMapping()
    public List<QuestionDto> getQuestions(){
        log.info("Get all question");
        return questionService.getQuestions();
    }

    @GetMapping()
    public QuestionDto getQuestion(@RequestParam String id){
        log.info("Get question by id {}", id);
        return questionService.getQuestionById(UUID.fromString(id));
    }

    @GetMapping("/full")
    public List<QuestionFullDto> getFullQuestions() {
        log.info("Get all full question");
        return questionService.getFullQuestions();
    }

    @GetMapping("/full")
    public QuestionFullDto getFullQuestion(@RequestParam String id) {
        log.info("Get full question by id {}", id);
        return questionService.getQuestionFullById(UUID.fromString(id));
    }

    @PutMapping()
    public QuestionFullDto editQuestion(@RequestBody QuestionFullDto questionDto){
        log.info("Edit question {}", questionDto);
        return questionService.editQuestion(questionDto);
    }

    @DeleteMapping()
    public void deleteQuestion(@RequestParam String id){
        log.info("Delete question by id {}", id);
        questionService.deleteQuestionById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public QuestionFullDto createQuestion(@RequestBody QuestionFullDto questionDto){
        log.info("Create question {}", questionDto);
        return questionService.createQuestion(questionDto);
    }

}
