package ru.testbest.dto;

public class UserAnswer {
    private Integer id;
    private String answer_text;
    private Integer point;
    private Integer question_id;
    private Integer answer_id;
    private Integer user_id;
    private Question question;

    public UserAnswer() {
    }

    public UserAnswer(Integer id, String answer_text, Integer point, Integer question_id, Integer answer_id, Integer user_id) {
        this.id = id;
        this.answer_text = answer_text;
        this.point = point;
        this.question_id = question_id;
        this.answer_id = answer_id;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "id=" + id +
                ", answer_text='" + answer_text + '\'' +
                ", point=" + point +
                ", question_id=" + question_id +
                ", answer_id=" + answer_id +
                ", user_id=" + user_id +
                '}';
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

    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }



}
