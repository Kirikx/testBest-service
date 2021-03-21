package ru.testbest.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.dto.test.QuestionDto;
import ru.testbest.service.QuestionTypeService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class QuestionTypeRestController {

    private final QuestionTypeService questionTypeDto;

    @GetMapping("/type_questions")
    public List<QuestionTypeDto> getTypeQuestions(){
        log.info("Get all type question");
        return questionTypeDto.getQuestionTypes();
    }

    @GetMapping("/type_questions/{id}")
    public QuestionTypeDto getTypeQuestion(@PathVariable("id") String id){
        log.info("Get by id type question");
        return questionTypeDto.getQuestionTypeById(id);
    }

}
