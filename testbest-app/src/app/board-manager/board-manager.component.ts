import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {NavigationEnd, Router, RouterEvent} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {TestFull} from "../_models/createTest/TestFull";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";
import {filter} from "rxjs/operators";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CheckFrom} from "../_helpers/checkFrom";

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-manager.component.html',
  styleUrls: ['./board-manager.component.css'],
})
export class BoardManagerComponent implements OnInit {
  checkForm = new CheckFrom();

  //Переменные для Topic
  topic: Topic;
  buff: Topic;
  isCreateTopicFailed = false;
  newTopic = true;
  topics: Array<Topic>;

  //Переменные для Test
  test: TestFull;
  tests: Array<TestFull>;

  isSubmitted = true; //нужна валидация форм?
  showModal = false;
  errorMessage: string;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private testService: TestService,
    private topicService: TopicService) {
    this.test = new TestFull();
    this.topic = new Topic();
  }

  ngOnInit(): void {
    const textScript = this.renderer2.createElement('script');
    textScript.src = 'assets/mbr-tabs/mbr-tabs.js';
    this.renderer2.appendChild(this.document.body, textScript);

    this.tokenStorage.checkTokenPrivate(this.router);

    this.getTopics();
    this.getMyTests();

    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd)
    ).subscribe(() => {
      if (window.location.href.toString().includes("/manager")) {
        window.location.reload();
      } else if (window.location.href.toString().includes("/tests")) {
        window.location.reload();
      } else if (window.location.href.toString().includes("/admin")) {
        window.location.reload();
      }
    });
  }

  //Обработка вкладки Тематика тестирования

  getNameCreateButton(): string {
    if (this.topic.id != null) {
      return "Редактировать";
    } else {
      return "Добавить";
    }
  }

  getNameUpdateButton(): string {
    if (this.topic.id != null) {
      return "Сохранить";
    } else {
      return "Создать";
    }
  }

  getNameTopic(id: String): String {
    return this.topics.find(topic => topic.id === id).name;
  }

  openModalNewTopic() {
    this.showModal = true;
    this.newTopic = true;
    this.topic = new Topic();
  }

  openModalEditTopic(id: String) {
    this.showModal = true;
    this.newTopic = false;
    this.getTopic(this.topics.find(topic => topic.id === id));
  }

  //Обработка разделов
  formTopicCreate = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl()
  });

  get validationTopicForm() {
    return this.formTopicCreate.controls;
  }

  changeTopicDescription(event) {
    this.topic.description = event.target.value;
  }

  changeTopicName(event) {
    this.topic.name = event.target.value;
  }

  clearFormCreateTopic() {
    this.topic = new Topic();
    this.formTopicCreate.setValue({
      name: this.topic.name,
      description: this.topic.description,
    })
  }

  getTopic(topic: Topic) {
    this.topicService.getTopic(topic).subscribe(
      data => {
        this.topic = data;
        this.formTopicCreate.setValue({
          name: this.topic.name,
          description: this.topic.description,
        })
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

  getTopics() {
    this.topicService.getTopics().subscribe(
      data => {
        this.topics = data;
        data.forEach(topic => this.getTestsByTopic(topic))
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

  createTopic(): void {
    if (!this.formTopicCreate.valid) {
      this.isSubmitted = false;
    } else {
      if (this.topic.id == null) {
        this.topicService.createTopic(this.topic).subscribe(
          data => {
              this.isCreateTopicFailed = false;
              this.closeModal();
              this.getTopics();
              // this.topic = new Topic();
          },
          error => {
            this.isCreateTopicFailed = true;
            if (error.statusText == "Unknown Error") {
              this.errorMessage = "Server is not responding";
            } else {
              this.errorMessage = error.message;
            }
          }
        );
      } else {
        this.editTopic();
      }
    }
  }

  editTopic(): void {
    this.topicService.editTopic(this.topic).subscribe(
      data => {
          this.isCreateTopicFailed = false;
          this.closeModal();
          this.getTopics();
          // this.topic = new Topic();
      },
      error => {
        this.isCreateTopicFailed = true;
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    );
  }

  deleteTopic(): void {
    this.topicService.deleteTopic(this.topic).subscribe(
      data => {
        this.isCreateTopicFailed = false;
        this.closeModal();
        this.getTopics();
        this.topic = new Topic();
      },
      error => {
        this.isCreateTopicFailed = true;
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    );
  }

  //Обработка вкладки Тест
  getMyTests() {
    this.testService.getMyTests().subscribe(
      data => {
        this.tests = data;
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

  redirectToCreateTest(id: string): void {
    this.router.navigate(["/test/" + id])
  }

  //Обработка модальных окон
  closeModal() {
    this.showModal = false;
    this.isSubmitted = true;
    this.clearFormCreateTopic();
  }

  getTestsByTopic(topic: Topic) {
    this.testService.getTestsByTopicId(topic.id).subscribe(
        data => {
          topic.tests = data;
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
