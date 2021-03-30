import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {Test} from "../_models/createTest/Test";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-manager.component.html',
  styleUrls: ['./board-manager.component.css'],

})
export class BoardManagerComponent implements OnInit {
  //Переменные для Topic
  topic: Topic;
  buff: Topic;
  isCreateTopicFailed = false;
  newTopic = true;
  topics: Array<Topic>;

  //Переменные для Test
  test: Test;
  tests: Array<Test>;


  showModal = false;
  errorMessage: string;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private testService: TestService,
    private topicService: TopicService) {
    this.test = new Test();
    this.topic = new Topic();
  }

  ngOnInit(): void {
    const textScript = this.renderer2.createElement('script');
    textScript.src = 'assets/mbr-tabs/mbr-tabs.js';
    this.renderer2.appendChild(this.document.body, textScript);

    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }

    this.getTopics();
    this.getTest();
  }

  //Обработка вкладки Тематика тестирования
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

  getTopic(topic: Topic) {
    this.topicService.getTopic(topic).subscribe(
      data => {
        this.topic = data;
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
    this.topicService.createTopic(this.topic).subscribe(
      data => {
        if (data.id != null && data.id != '') {
          this.isCreateTopicFailed = false;
          this.closeModal();
          this.getTopics();
          this.topic = new Topic();
        }
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

  editTopic(): void {
    this.topicService.editTopic(this.topic).subscribe(
      data => {
        if (data.id != null && data.id != '') {
          this.isCreateTopicFailed = false;
          this.closeModal();
          this.getTopics();
          this.topic = new Topic();
        }
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
  getTest() {
    //TODO как сделаю метод на основании конкретного автора, переключиться
    this.testService.getTests().subscribe(
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
  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }
}
