package ru.testbest.controller;

import org.springframework.web.bind.annotation.RestController;
//import ru.testbest.persistence.dao.QuestionAnswerDAO;
//import ru.testbest.persistence.dao.QuestionDAO;
//import ru.testbest.persistence.dao.QuestionTopicDAO;
//import ru.testbest.persistence.dao.StatisticDAO;
//import ru.testbest.persistence.dao.UserAnswerDAO;

@RestController
public class TestRestController {
//
//  @Autowired
//  private QuestionDAO questionDAO;
//
//  @Autowired
//  private QuestionAnswerDAO questionAnswerDAO;
//
//  @Autowired
//  private QuestionTopicDAO questionTopicDAO;
//
//  @Autowired
//  private UserAnswerDAO userAnswerDAO;
//
//  @Autowired
//  private StatisticDAO statisticDAO;
//
//  @GetMapping("/statistics")
//  public ResponseEntity<List<Statistic>> getStatistics(@AuthenticationPrincipal Userx user) {
//    List<Statistic> statistics = new ArrayList<>();
//
//    Statistic statProgress = statisticDAO.getUserProgress(user.getId());
//    statProgress.setName("Процент прохождения тестирования текущего пользователя");
//    statistics.add(statProgress);
//
//    System.out.println(user.getId());
//    Statistic allUsersWorse = statisticDAO.getCountUsersProgressWorse(user.getId());
//    allUsersWorse.setName("Cколько людей справилось с тестированием хуже");
//    statistics.add(allUsersWorse);
//
//    Statistic allUsersBetter = statisticDAO.getCountUsersProgressBetter(user.getId());
//    allUsersBetter.setName("Cколько людей справилось с тестированием лучше");
//    statistics.add(allUsersBetter);
//
//    ResponseEntity<List<Statistic>> retVal;
//    retVal = ResponseEntity.ok(statistics);
//    return retVal;
//  }
//
//  @GetMapping("/answers")
//  public ResponseEntity<List<UserAnswer>> getUserAnswers(@AuthenticationPrincipal Userx user) {
//    List<UserAnswer> userAnswer
//        = userAnswerDAO.getAllUserAnswers(user.getId());
//
//    if (userAnswer == null) {
//      userAnswer = new ArrayList<>();
//    }
//
//    ResponseEntity<List<UserAnswer>> retVal;
//    retVal = ResponseEntity.ok(userAnswer);
//    return retVal;
//  }
//
//  @GetMapping("/topics")
//  public ResponseEntity<List<QuestionTopic>> getTopics() {
//    List<QuestionTopic> questionTopics
//        = questionTopicDAO.getAllTopics();
//
//    if (questionTopics == null) {
//      questionTopics = new ArrayList<QuestionTopic>();
//    }
//
//    ResponseEntity<List<QuestionTopic>> retVal;
//    retVal = ResponseEntity.ok(questionTopics);
//    return retVal;
//  }
//
//
//  @GetMapping("/topics/{topic_id}")
//  public ResponseEntity<QuestionTopic> getTopics(@PathVariable Integer topic_id) {
//    QuestionTopic questionTopic
//        = questionTopicDAO.getQuestionTopicById(topic_id);
//
//    if (questionTopic == null) {
//      throw new NotFoundException(topic_id.toString());
//    }
//
//    ResponseEntity<QuestionTopic> retVal;
//    retVal = ResponseEntity.ok(questionTopic);
//    return retVal;
//  }
//
//  @GetMapping("/topics/{topic_id}/questions")
//  public ResponseEntity<List<Question>> getQuestionsByTopicId(@PathVariable Integer topic_id) {
//    List<Question> questions
//        = questionDAO.getAllQuestionsByTopicId(topic_id);
//
//    if (questions == null) {
//      questions = new ArrayList<>();
//    }
//
//    ResponseEntity<List<Question>> retVal;
//    retVal = ResponseEntity.ok(questions);
//    return retVal;
//  }
//
//
//  @GetMapping("/topics/{topic_id}/questions/{question_id}")
//  public ResponseEntity<Question> search(
//      @AuthenticationPrincipal Userx user,
//      @RequestParam Map<String, String> allRequestParams,
//      @PathVariable Integer topic_id,
//      @PathVariable Integer question_id) {
//    if (!allRequestParams.isEmpty()) {
//      if (!userAnswerDAO.validationCheckQuestion(user.getId(), question_id)) {
//        if (allRequestParams.containsKey("set")) {
//          allRequestParams.get("set");
//          Integer answerId;
//          try {
//            answerId = Integer.parseInt(allRequestParams.get("set"));
//            if (questionDAO.getQuestionById(question_id).getQuestion_type_id() == 1) {
//              UserAnswer userAnswer = new UserAnswer();
//
//              System.out.println(allRequestParams.get("set"));
//
//              userAnswer.setUser_id(user.getId());
//              userAnswer.setQuestion_id(question_id);
//              userAnswer.setAnswer_id(answerId);
//              userAnswer.setPoint(questionAnswerDAO.getQuestionAnswerById(answerId).getPoint());
//
//              userAnswerDAO.createUserAnswer(userAnswer);
//            }
//          } catch (NumberFormatException e) {
//            System.out.println("Uncorrected type!");
//          }
//        }
//        if (allRequestParams.containsKey("input")) {
//          String answerInput = allRequestParams.get("input");
//
//          if (questionDAO.getQuestionById(question_id).getQuestion_type_id() == 2) {
//            UserAnswer userAnswer = new UserAnswer();
//
//            System.out.println(allRequestParams.get("input"));
//
//            userAnswer.setUser_id(user.getId());
//            userAnswer.setQuestion_id(question_id);
//            userAnswer.setAnswer_text(answerInput);
//            userAnswer.setPoint(questionAnswerDAO.validationAnswerInput(question_id, answerInput));
//
//            userAnswerDAO.createUserAnswer(userAnswer);
//          }
//        }
//      }
//    }
//
//    Question questions
//        = questionDAO.getQuestionById(question_id);
//    if (questions == null) {
//      throw new NotFoundException(question_id.toString());
//    }
//
//    ResponseEntity<Question> retVal;
//    retVal = ResponseEntity.ok(questions);
//    return retVal;
//  }
//
//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  class NotFoundException extends RuntimeException {
//
//    public NotFoundException(String find) {
//      super("could not find  '" + find + "'.");
//    }
//  }
}
