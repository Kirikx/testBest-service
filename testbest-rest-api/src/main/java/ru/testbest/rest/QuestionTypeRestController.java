package ru.testbest.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.testbest.dto.common.QuestionTypeDto;
import ru.testbest.service.QuestionTypeService;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/type_questions")
public class QuestionTypeRestController {

    private final QuestionTypeService questionTypeDto;

    @GetMapping
    public List<QuestionTypeDto> getTypeQuestions(){
        log.info("Get all type question");
        return questionTypeDto.getQuestionTypes();
    }

    @GetMapping("/{id}")
    public QuestionTypeDto getTypeQuestion(@PathVariable("id") String id){
        log.info("Get question type by id {} ", id);
        return questionTypeDto.getQuestionTypeById(UUID.fromString(id));
    }

}
