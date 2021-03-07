package ru.testbest.persistence.dao;

import ru.testbest.dto.QuestionTopic;
import java.util.List;

public interface QuestionTopicDAO {

    QuestionTopic getQuestionTopicById(Integer id);

    List<QuestionTopic> getAllTopics();

    boolean deleteTopic(QuestionTopic questionTopic);

    boolean updateTopic(QuestionTopic questionTopic);

    boolean createTopic(QuestionTopic questionTopic);
}
