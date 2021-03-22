package ru.testbest.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.service.impl.common.AnswerServiceImpl;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class AnswerRestController {

    private final AnswerServiceImpl answerService;

    @PostMapping("/answers/create")
    public AnswerDto createAnswer(@RequestBody AnswerDto answerDto){
        log.info("Create answer {}", answerDto);
        return answerService.createAnswer(answerDto);
    }

    @PutMapping("/answers")
    public AnswerDto editAnswer(@RequestBody AnswerDto answerDto){
        log.info("Edit answer {}", answerDto);
        return answerService.editAnswer(answerDto);
    }

    @GetMapping("/answers")
    public List<AnswerDto> getListAnswers(){
        log.info("Get list answer");
        return answerService.getAnswers();
    }

    @GetMapping("/answers/{id}")
    public AnswerDto getAnswer(@PathVariable String id){
        log.info("Get answer by id {}", id);
        return answerService.getAnswerById(id);
    }
}
