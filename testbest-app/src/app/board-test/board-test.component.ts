import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {Test} from "../_models/createTest/Test";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Chapter} from "../_models/createTest/Chapter";

@Component({
  selector: 'app-board-test',
  templateUrl: './board-test.component.html',
  styleUrls: ['./board-test.component.css']
})
export class BoardTestComponent implements OnInit {
  test: Test;
  chapter: Chapter;
  topics: Array<Topic>;

  formTestCreate = new FormGroup({
    topicId: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    description: new FormControl(),
    duration: new FormControl('', [Validators.required, Validators.min(10), Validators.max(300)])
  });

  formChapterCreate = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl()
  });

  // formQuestionCreate = new FormGroup({
  //   topicId: new FormControl('', Validators.required),
  //   name: new FormControl('', Validators.required),
  //   description: new FormControl(),
  //   duration: new FormControl('', [Validators.required, Validators.min(10), Validators.max(300)])
  // });

  isCreateTest = false; //записан ли тест
  isCreateChapter = false; //записан ли раздел
  isCreateQuestion = false; //записан ли вопрос

  isSubmitted = true;

  showModal = false;
  errorMessage: string;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private testService: TestService,
    private topicService: TopicService,
    private route: ActivatedRoute) {
    this.test = new Test();
  }

  ngOnInit(): void {
    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }

    this.getTest();
    this.getTopics();
  }

  //Обработка топика
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

  //Обработка теста
  get validationTestForm() {
    return this.formTestCreate.controls;
  }

  changeTestSelect(event) {
    this.formTestCreate.get("topicId").setValue(event.target.value, {
      onlySelf: true
    })
    this.test.topicId = event.target.value.substring(3);
  }

  changeTestDescription(event) {
    this.test.description = event.target.value;
  }

  changeTestName(event) {
    this.test.name = event.target.value;
  }

  changeTestDuration(event) {
    this.test.duration = event.target.value;
  }

  createTest(): void {
    if (!this.formTestCreate.valid) {
      this.isSubmitted = false;
    } else {
      this.testService.createTest(this.test).subscribe(
        data => {
          this.test = data;
          this.router.navigate(["/test/"+ this.test.id])
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
  }

  getTest() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id != '0') {
      this.test.id = id;
      this.testService.getTest(this.test).subscribe(
        data => {
          this.isCreateTest = true;
          this.test = data;
          this.formTestCreate.setValue({
            topicId: this.test.topicId,
            name: this.test.name,
            description: this.test.description,
            duration: this.test.duration
          })
          this.test = data;
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
  }

  //Обработка разделов
  openModalNewChapter() {
    this.showModal = true;
    this.chapter = new Chapter();
  }

  get validationChapterForm() {
    return this.formChapterCreate.controls;
  }

  changeChapterDescription(event) {
    this.chapter.description = event.target.value;
  }

  changeChapterName(event) {
    this.chapter.name = event.target.value;
  }

  createChapter(): void {
    if (!this.formChapterCreate.valid) {
      this.isSubmitted = false;
    } else {
      // this.testService.createTest(this.test).subscribe(
      //   data => {
      //     this.test = data;
      //     this.router.navigate(["/test/"+ this.test.id])
      //   },
      //   error => {
      //     if (error.statusText == "Unknown Error") {
      //       this.errorMessage = "Server is not responding";
      //     } else {
      //       this.errorMessage = error.message;
      //     }
      //   }
      // );
    }
  }

  //Обработка модальных окон
  openModalNewQuestion() {
    this.showModal = true;
  }
  //Обработка модальных окон
  closeModal() {
    this.showModal = false;
  }
}
