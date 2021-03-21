package ru.testbest.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.service.QuestionTypeService;

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
        log.info("Get question type by id {} ", id);
        return questionTypeDto.getQuestionTypeById(id);
    }

}
