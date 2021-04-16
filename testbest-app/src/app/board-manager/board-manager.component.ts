import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {NavigationEnd, Router, RouterEvent} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {TestFull} from "../_models/createTest/TestFull";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";
import {filter} from "rxjs/operators";

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
  test: TestFull;
  tests: Array<TestFull>;


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
      }
    });
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

  getTestsTopicText(tests: Array<TestFull>): string {
    // TODO сделать как ссылки
    if (typeof tests !== "undefined") {
      return tests.map(test => test.name).join(', ');
    }
  }
}
