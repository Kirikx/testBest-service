package ru.testbest.rest;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.manage.AnswerFullDto;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.service.impl.common.AnswerServiceImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/answers")
public class AnswerRestController {

    private final AnswerServiceImpl answerService;

    @PostMapping("/create")
    public AnswerFullDto createAnswer(@RequestBody AnswerFullDto answerDto) {
        log.info("Create answer {}", answerDto);
        return answerService.createAnswer(answerDto);
    }

    @PutMapping("/")
    public AnswerFullDto editAnswer(@RequestBody AnswerFullDto answerDto) {
        log.info("Edit answer {}", answerDto);
        return answerService.editAnswer(answerDto);
    }

    @GetMapping("/full")
    public List<AnswerFullDto> getListFullAnswers() {
        log.info("Get list answer");
        return answerService.getAnswersFull();
    }

    @GetMapping("/full/{id}")
    public AnswerFullDto getAnswerFull(@PathVariable String id) {
        log.info("Get full answer by id {}", id);
        return answerService.getAnswerFullById(UUID.fromString(id));
    }

    @GetMapping("/")
    public List<AnswerDto> getListAnswers() {
        log.info("Get full list answer");
        return answerService.getAnswers();
    }

    @GetMapping("/{id}")
    public AnswerDto getAnswer(@PathVariable String id) {
        log.info("Get answer by id {}", id);
        return answerService.getAnswerById(UUID.fromString(id));
    }

    @DeleteMapping("/{id}")
    public void deleteAnswer(@PathVariable("id") String id) {
        log.info("Delete test by id {}", id);
        answerService.deleteAnswerById(UUID.fromString(id));
    }
}
