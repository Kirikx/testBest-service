import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {Test} from "../_models/test";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/topic";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-board-test',
  templateUrl: './board-test.component.html',
  styleUrls: ['./board-test.component.css']
})
export class BoardTestComponent implements OnInit {
  test: Test;
  tests: Array<Test>;
  topics: Array<Topic>;

  formTestCreate = new FormGroup({
    topicId: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    description: new FormControl(),
    duration: new FormControl('', [Validators.required, Validators.min(10), Validators.max(300)])
  });

  isCreateNewTest = false;
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
    const textScript = this.renderer2.createElement('script');
    textScript.src = 'assets/mbr-tabs/mbr-tabs.js';
    this.renderer2.appendChild(this.document.body, textScript);

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
  get validationForm() {
    return this.formTestCreate.controls;
  }

  changeSelect(event) {
    this.formTestCreate.get("topicId").setValue(event.target.value, {
      onlySelf: true
    })
    this.test.topicId = event.target.value.substring(3);
  }

  changeDescription(event) {
    this.test.description = event.target.value;
  }

  changeName(event) {
    this.test.name = event.target.value;
  }

  changeDuration(event) {
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
          this.isCreateNewTest = true;
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

  //Обработка модальных окон
  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }
}
