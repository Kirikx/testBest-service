package ru.testbest.service;

public enum QuestionTypeEnum {
  FREE("Вопрос со свободным ответом"),
  MALTY("Множественный"),
  SET("Одиночный");

  private String text;

  QuestionTypeEnum(String text) {
    this.text = text;
  }

  public String getText() {
    return this.text;
  }

  public static QuestionTypeEnum fromString(String text) {
    for (QuestionTypeEnum b : QuestionTypeEnum.values()) {
      if (b.text.equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }

}
