package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.service.QuestionTypeService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/type_questions")
@RestController
public class QuestionTypeRestController {

    private final QuestionTypeService questionTypeDto;

    @GetMapping()
    public List<QuestionTypeDto> getTypeQuestions(){
        log.info("Get all type question");
        return questionTypeDto.getQuestionTypes();
    }

    @GetMapping()
    public QuestionTypeDto getTypeQuestion(@RequestParam String id){
        log.info("Get question type by id {} ", id);
        return questionTypeDto.getQuestionTypeById(UUID.fromString(id));
    }

}
