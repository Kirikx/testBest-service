package ru.testbest.dto;

public class QuestionAnswer {

    private Integer id;
    private String answer_text;
    private Integer point;
    private Integer question_id;

    public QuestionAnswer() {
    }

    public QuestionAnswer(Integer id, String answer_text, Integer point, Integer question_id) {
        this.id = id;
        this.answer_text = answer_text;
        this.point = point;
        this.question_id = question_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }
}
