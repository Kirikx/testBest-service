package ru.testbest.dto;

import java.util.HashSet;
import java.util.Set;

public class Question {
    private Integer id;
    private String text;
    private Integer question_topic_id;
    private Integer question_type_id;
    private Set<QuestionAnswer> questionAnswers = new HashSet<QuestionAnswer>();

    public Question() {
    }

    public Question(Integer id, String text, Integer question_topic_id, Integer question_type_id) {
        this.id = id;
        this.text = text;
        this.question_topic_id = question_topic_id;
        this.question_type_id = question_type_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getQuestion_topic_id() {
        return question_topic_id;
    }

    public void setQuestion_topic_id(Integer question_topic_id) {
        this.question_topic_id = question_topic_id;
    }

    public Integer getQuestion_type_id() {
        return question_type_id;
    }

    public void setQuestion_type_id(Integer question_type_id) {
        this.question_type_id = question_type_id;
    }

    public Set<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void addQuestionAnswers (QuestionAnswer questionAnswers) {
        this.questionAnswers.add(questionAnswers);
    }

    public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}
