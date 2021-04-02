package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.test.AnswerDto;
import ru.testbest.dto.test.AnswerFullDto;
import ru.testbest.service.impl.common.AnswerServiceImpl;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/answers")
@RestController
public class AnswerRestController {

    private final AnswerServiceImpl answerService;

    @PostMapping("/create")
    public AnswerFullDto createAnswer(@RequestBody AnswerFullDto answerDto){
        log.info("Create answer {}", answerDto);
        return answerService.createAnswer(answerDto);
    }

    @PutMapping()
    public AnswerFullDto editAnswer(@RequestBody AnswerFullDto answerDto){
        log.info("Edit answer {}", answerDto);
        return answerService.editAnswer(answerDto);
    }

    @GetMapping("/full")
    public List<AnswerFullDto> getListFullAnswers(){
        log.info("Get list answer");
        return answerService.getAnswersFull();
    }

    @GetMapping("/full")
    public AnswerFullDto getAnswerFull(@RequestParam String id) {
        log.info("Get full answer by id {}", id);
        return answerService.getAnswerFullById(UUID.fromString(id));
    }

    @GetMapping()
    public List<AnswerDto> getListAnswers(){
        log.info("Get full list answer");
        return answerService.getAnswers();
    }

    @GetMapping()
    public AnswerDto getAnswer(@RequestParam String id){
        log.info("Get answer by id {}", id);
        return answerService.getAnswerById(UUID.fromString(id));
    }
}
