import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {QuestionService} from "../_services/question.service";
import {QuestionTypeService} from "../_services/question-type.service";
import {UserTestService} from "../_services/user-test.service";
import {UserTest} from "../_models/playTest/UserTest";
import {Test} from "../_models/Test";
import {Question} from "../_models/Question";
import {UserQuestion} from "../_models/playTest/UserQuestion";

@Component({
  selector: 'app-user-test',
  templateUrl: './user-test.component.html',
  styleUrls: ['./user-test.component.css']
})
export class UserTestComponent implements OnInit {

  userTest: UserTest;
  passedQuestion: number = 0;
  test: Test;

  userQuestion: Array<UserQuestion>;

  errorMessage: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private tokenStorage: TokenStorageService,
    private testService: TestService,
    private topicService: TopicService,
    private questionService: QuestionService,
    private questionTypeService: QuestionTypeService,
    private userTestService: UserTestService,) {
  }

  ngOnInit(): void {
    this.tokenStorage.checkTokenPrivate(this.router);
    this.getUserTest();
  }

  getUserTest() {
    let userTest = new UserTest();
    userTest.id = this.route.snapshot.paramMap.get('id');
    this.userTestService.getUserTest(userTest).subscribe(
      data => {
        this.userTest = data;
        this.getTest();
        this.getFailAnswer()
        this.writePassedQuestion();
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    )
  }

  getTest() {
    let test = new Test();
    test.id = this.userTest.testId;
    this.testService.getTest(test).subscribe(
      data => {
        this.test = data;
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    )
  }

  writePassedQuestion() {
    for (let answer of this.userTest.userTestQuestions) {
      if (answer.isCorrect) this.passedQuestion++;
    }
  }

  getFailAnswer() {
    this.userTestService.getFailQuestionsByUserTestId(this.userTest).subscribe(
      data => {
        this.userQuestion = data;
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    )
  }

  checkFullAnswer(): boolean {
    return this.userTest.userTestQuestions.length == this.passedQuestion;
  }

  getQuestion(id: string): string {
    let value: string = "";
    for (let chapter of this.test.chapters) {
      for (let question of chapter.questions) {
        if (question.id == id) {
          return question.question;
        }
      }
    }
    return value;
  }

}
