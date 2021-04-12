import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {TestService} from "../_services/test.service";
import {Test} from "../_models/Test";
import {Topic} from "../_models/createTest/parameters/Topic";
import {TopicService} from "../_services/topic.service";
import {QuestionType} from "../_models/createTest/parameters/QuestionType";
import {QuestionTypeService} from "../_services/question-type.service";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  test: Test;
  topics: Array<Topic>;
  questionTypes: Array<QuestionType>;

  errorMessage: string;

  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute,
    private testService: TestService,
    private topicService: TopicService,
    private questionTypeService: QuestionTypeService) {
  }

  ngOnInit(): void {
    this.tokenStorage.checkTokenPrivate(this.router);
    this.getTest();
    this.getTopics();
    this.getQuestionTypes();
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
    this.testService.getTest(test).subscribe(data => {
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

}
