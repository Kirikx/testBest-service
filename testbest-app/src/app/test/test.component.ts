import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {TestService} from "../_services/test.service";
import {Test} from "../_models/Test";
import {Topic} from "../_models/createTest/parameters/Topic";
import {TopicService} from "../_services/topic.service";
import {QuestionType} from "../_models/createTest/parameters/QuestionType";
import {QuestionTypeService} from "../_services/question-type.service";
import {UserTestService} from "../_services/user-test.service";
import {UserTest} from "../_models/playTest/UserTest";
import {UserQuestion} from "../_models/playTest/UserQuestion";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {QuestionService} from "../_services/question.service";
import {Question} from "../_models/Question";
import {Answer} from "../_models/Answer";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  timeLeft: number = 60;
  interval;

  test: Test;
  topics: Array<Topic>;
  questionTypes: Array<QuestionType>;

  userTest: UserTest;
  userQuestion: UserQuestion;

  question: Question;
  questionType: QuestionType;

  startTest: boolean = false;
  freeAnswer: string = null;
  errorMessage: string;

  formUserAnswer = new FormGroup({});
  formCheckboxAnswer: FormGroup;

  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute,
    private testService: TestService,
    private topicService: TopicService,
    private questionService: QuestionService,
    private questionTypeService: QuestionTypeService,
    private userTestService: UserTestService,
    private formBuilder: FormBuilder) {
    this.formCheckboxAnswer = this.formBuilder.group({
      checkArray: this.formBuilder.array([],)
    })
  }

  ngOnInit(): void {
    this.tokenStorage.checkTokenPrivate(this.router);
    this.getTest();
    this.getTopics();
    this.getQuestionTypes();
  }

  startTimer() {
    this.interval = setInterval(() => {
      if(this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        this.timeLeft = 60;
      }
    },1000)
  }

  pauseTimer() {
    clearInterval(this.interval);
  }

  updateDataQuestion(data) {
    this.userQuestion = new UserQuestion();
    this.question = data;
    for (let type of this.questionTypes) {
      if (this.question.questionTypeId != null && type.id == this.question.questionTypeId) {
        this.questionType = type;
        break;
      }
    }
    this.userQuestion.questionId = this.question.id;
    this.userQuestion.answers = new Array<Answer>();
    this.freeAnswer = null;
    if (this.userTest != null) this.userQuestion.userTestId = this.userTest.id;
    let checkArray: FormArray = this.formCheckboxAnswer.get('checkArray') as FormArray;
    checkArray.clear();
  }

  getQuestionTypes() {
    this.questionTypeService.getQuestionTypes().subscribe(
      data => {
        this.questionTypes = data;
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    );
  }

  getTopics() {
    this.topicService.getTopics().subscribe(
      data => {
        this.topics = data;
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

  getNameTopic(id: String): String {
    return this.topics.find(topic => topic.id === id).name;
  }

  getTest() {
    let test = new Test();
    test.id = this.route.snapshot.paramMap.get('id');
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

  playTest() {
    this.userTestService.startUserTest(this.test).subscribe(
      data => {
        this.updateDataQuestion(data);
        this.startTest = true;
        this.userTestService.getActiveUserTest().subscribe(
          data => {
            this.userTest = data;
            this.userQuestion.userTestId = this.userTest.id;
          },
          error => {
            if (error.statusText == "Unknown Error") {
              this.errorMessage = "Server is not responding";
            } else {
              this.errorMessage = error.message;
            }
          }
        )
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

  saveAnswer() {
    this.userTestService.createUserAnswer(this.userQuestion).subscribe(
      data => {
        this.updateDataQuestion(data);
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.startTest = false;
          this.router.navigate(["/user/test/" + this.userTest.id])
          //this.errorMessage = error.message;
        }
      }
    )
  }

  //Обработка вводимых значений
  changeFreeAnswer(event) {
    this.freeAnswer = event.target.value;
    this.userQuestion.freeAnswer = this.freeAnswer;
  }

  changeRadioButtonAnswer(event) {
    let userAnswer = Array<Answer>();
    for (let answer of this.question.answers) {
      if (answer.id == event.target.id) {
        userAnswer.push(answer);
        break;
      }
    }
    this.userQuestion.answers = userAnswer;
  }

  changeCheckboxAnswer(event) {
    let checkArray: FormArray = this.formCheckboxAnswer.get('checkArray') as FormArray;
    if (event.target.checked) {
      for (let answer of this.question.answers) {
        if (answer.id == event.target.content) {
          checkArray.push(new FormControl(answer));
          break;
        }
      }
    } else {
      let i: number = 0;
      checkArray.controls.forEach((item: FormControl) => {
        if (item.value.id == event.target.content) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
    let userAnswer = Array<Answer>();
    for (let check of checkArray.controls) {
      userAnswer.push(check.value);
    }
    this.userQuestion.answers = userAnswer;
    console.log(this.userQuestion);
  }
}
