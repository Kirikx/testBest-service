package ru.testbest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import ru.testbest.persistence.dao.QuestionAnswerDAO;
//import ru.testbest.persistence.dao.QuestionDAO;
//import ru.testbest.persistence.dao.QuestionTopicDAO;
//import ru.testbest.persistence.dao.StatisticDAO;
//import ru.testbest.persistence.dao.UserAnswerDAO;
//import ru.testbest.persistence.dao.UserxDAO;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminTestController {
//    @Autowired
//    private UserxDAO userDAO;
//
//    @Autowired
//    private QuestionDAO questionDAO;
//
//    @Autowired
//    private QuestionAnswerDAO questionAnswerDAO;
//
//    @Autowired
//    private QuestionTopicDAO questionTopicDAO;
//
//    @Autowired
//    private UserAnswerDAO userAnswerDAO;
//
//    @Autowired
//    private StatisticDAO statisticDAO;
//
//    @GetMapping("/statistics")
//    public ResponseEntity<List<Statistic>> getStatistics() {
//        List<Statistic> statistics = new ArrayList<>();
//
//        Statistic statUserRegistration = statisticDAO.getCountUserRegistration();
//        statUserRegistration.setName("Сколько всего пользователей зарегистрировано в системе");
//        statistics.add(statUserRegistration);
//
//        Statistic countCheckTest = statisticDAO.getCountUserCheckTestings();
//        countCheckTest.setName("Сколько пользователей прошли тестирование");
//        statistics.add(countCheckTest);
//
//        Statistic countUsersAnswerAll = statisticDAO.getCountUserAnswerAll();
//        countUsersAnswerAll.setName("Сколько пользователей ответили на все вопросы тестирования");
//        statistics.add(countUsersAnswerAll);
//
//        Statistic countUsersAnswerAllTrue = statisticDAO.getCountUserAnswerAllTrue();
//        countUsersAnswerAllTrue.setName("Сколько пользователей ответили на все вопросы тестирования правильно");
//        statistics.add(countUsersAnswerAllTrue);
//
//        ResponseEntity<List<Statistic>> retVal;
//        retVal = ResponseEntity.ok(statistics);
//        return retVal;
//    }
//
//    @GetMapping("/topics")
//    public ResponseEntity<List<QuestionTopic>> getTopics() {
//        List<QuestionTopic> questionTopics
//                = questionTopicDAO.getAllTopics();
//
//        if (questionTopics == null) {
//            questionTopics = new ArrayList<>();
//        }
//
//        ResponseEntity<List<QuestionTopic>> retVal;
//        retVal = ResponseEntity.ok(questionTopics);
//        return retVal;
//    }
//
//    @PostMapping("/topics")
//    public ResponseEntity<String> addTopic(HttpServletResponse response, @RequestBody QuestionTopic questionTopic) {
//        if (!questionTopicDAO.createTopic(questionTopic)) {
//            return new ResponseEntity<>("Topic " + questionTopic.getName() + " no created!!!", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Topic " + questionTopic.getName() + " created!", HttpStatus.CREATED);
//
//    }
//
//    @GetMapping("/topics/{topic_id}")
//    public ResponseEntity<QuestionTopic> getTopics(@PathVariable Integer topic_id) {
//        QuestionTopic questionTopic
//                = questionTopicDAO.getQuestionTopicById(topic_id);
//
//        if (questionTopic == null) {
//            throw new NotFoundException(topic_id.toString());
//        }
//
//        ResponseEntity<QuestionTopic> retVal;
//        retVal = ResponseEntity.ok(questionTopic);
//        return retVal;
//    }
//
//    @GetMapping("/topics/{topic_id}/questions")
//    public ResponseEntity<List<Question>> getQuestionsByTopicId(@PathVariable Integer topic_id) {
//        List<Question> questions
//                = questionDAO.getAllQuestionsByTopicId(topic_id);
//
//        if (questions == null) {
//            questions = new ArrayList<>();
//        }
//
//        ResponseEntity<List<Question>> retVal;
//        retVal = ResponseEntity.ok(questions);
//        return retVal;
//    }
//
//    @PostMapping("/topics{topic_id}/questions")
//    public ResponseEntity<String> addQuestion(@RequestBody Question question, @PathVariable Integer topic_id) {
//        question.setQuestion_topic_id(topic_id);
//        if (!questionDAO.createQuestion(question)) {
//            return new ResponseEntity<>("Question '" + question.getText() + "' no created!!!", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Question '" + question.getText() + "' created!", HttpStatus.CREATED);
//    }
//
//
//    @GetMapping("/topics/{topic_id}/questions/{question_id}")
//    public ResponseEntity<Question> search(
//            @PathVariable Integer question_id) {
//
//        Question questions
//                = questionDAO.getQuestionById(question_id);
//        if (questions == null) {
//            throw new NotFoundException(question_id.toString());
//        }
//
//        ResponseEntity<Question> retVal;
//        retVal = ResponseEntity.ok(questions);
//        return retVal;
//    }
//
//    @GetMapping("/topics/{topic_id}/questions/{question_id}/answers")
//    public ResponseEntity<List<QuestionAnswer>> getAnswers(@PathVariable Integer question_id) {
//        List<QuestionAnswer> questionAnswers
//                = questionAnswerDAO.getAllQuestionAnswersByQuestionId(question_id);
//
//        if (questionAnswers == null) {
//            questionAnswers = new ArrayList<>();
//        }
//
//        ResponseEntity<List<QuestionAnswer>> retVal;
//        retVal = ResponseEntity.ok(questionAnswers);
//        return retVal;
//    }
//
//    @PostMapping("/topics/{topic_id}/questions/{question_id}/answers")
//    public ResponseEntity<String> addQuestion(HttpServletResponse response, @RequestBody QuestionAnswer questionAnswer, @PathVariable Integer question_id) {
//        questionAnswer.setQuestion_id(question_id);
//        if (!questionAnswerDAO.createQuestionAnswer(questionAnswer)) {
//            return new ResponseEntity<>("Question answer '" + questionAnswer.getAnswer_text() + "' no created!!!", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Question answer '" + questionAnswer.getAnswer_text() + "' created!", HttpStatus.CREATED);
//    }
//
//    @GetMapping("/users")
//    public ResponseEntity<List<Userx>> getUsers(
//            @AuthenticationPrincipal Userx user) {
//        List<Userx> users
//                = userDAO.getAllUsers();
//
//        if (users == null) {
//            users = new ArrayList<>();
//        }
//
//        ResponseEntity<List<Userx>> retVal;
//        retVal = ResponseEntity.ok(users);
//        return retVal;
//    }
//
//    @GetMapping("/users/{user_id}")
//    public ResponseEntity<Userx> getUserId(
//            @PathVariable Integer user_id) {
//
//        Userx user = userDAO.getUserById(user_id);
//        if (user == null) {
//            throw new NotFoundException(user_id.toString());
//        }
//
//        ResponseEntity<Userx> retVal;
//        retVal = ResponseEntity.ok(user);
//        return retVal;
//    }
//
//    @GetMapping("/users/{user_id}/answers")
//    public ResponseEntity<List<UserAnswer>> getUserAnswer(
//            @PathVariable Integer user_id
//    ) {
//        List<UserAnswer> userAnswer
//                = userAnswerDAO.getAllUserAnswers(user_id);
//
//        if (userAnswer == null) {
//            userAnswer = new ArrayList<>();
//        }
//
//        ResponseEntity<List<UserAnswer>> retVal;
//        retVal = ResponseEntity.ok(userAnswer);
//        return retVal;
//    }
//
//    @GetMapping("/users/{user_id}/statistics")
//    public ResponseEntity<List<Statistic>> getStatistics(@PathVariable Integer user_id) {
//        List<Statistic> statistics = new ArrayList<>();
//
//        Statistic statProgress = statisticDAO.getUserProgress(user_id);
//        statProgress.setName("Процент прохождения тестирования текущего пользователя");
//        statistics.add(statProgress);
//
//        Statistic allUsersWorse = statisticDAO.getCountUsersProgressWorse(user_id);
//        allUsersWorse.setName("Cколько процентов людей справилось с тестированием хуже");
//        statistics.add(allUsersWorse);
//
//        Statistic allUsersBetter = statisticDAO.getCountUsersProgressBetter(user_id);
//        allUsersBetter.setName("Cколько процентов людей справилось с тестированием лучше");
//        statistics.add(allUsersBetter);
//
//        ResponseEntity<List<Statistic>> retVal;
//        retVal = ResponseEntity.ok(statistics);
//        return retVal;
//    }
//
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    class NotFoundException extends RuntimeException {
//        public NotFoundException(String find) {
//            super("could not find  '" + find + "'.");
//        }
//    }

}
