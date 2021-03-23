package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.dto.test.AnswerFullDto;
import ru.testbest.service.impl.common.AnswerServiceImpl;


@Slf4j
@RequiredArgsConstructor
@RestController
public class AnswerRestController {

    private final AnswerServiceImpl answerService;

    @PostMapping("/answers/create")
    public AnswerFullDto createAnswer(@RequestBody AnswerFullDto answerDto){
        log.info("Create answer {}", answerDto);
        return answerService.createAnswer(answerDto);
    }

    @PutMapping("/answers")
    public AnswerFullDto editAnswer(@RequestBody AnswerFullDto answerDto){
        log.info("Edit answer {}", answerDto);
        return answerService.editAnswer(answerDto);
    }

    @GetMapping("/answers/full")
    public List<AnswerFullDto> getListFullAnswers(){
        log.info("Get list answer");
        return answerService.getAnswersFull();
    }

    @GetMapping("/answers/full/{id}")
    public AnswerDto getAnswerFull(@PathVariable String id){
        log.info("Get full answer by id {}", id);
        return answerService.getAnswerById(UUID.fromString(id));
    }

    @GetMapping("/answers")
    public List<AnswerDto> getListAnswers(){
        log.info("Get full list answer");
        return answerService.getAnswers();
    }

    @GetMapping("/answers/{id}")
    public AnswerDto getAnswer(@PathVariable String id){
        log.info("Get answer by id {}", id);
        return answerService.getAnswerById(UUID.fromString(id));
    }
}
