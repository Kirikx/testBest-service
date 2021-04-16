import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {DOCUMENT} from "@angular/common";
import {TokenStorageService} from "../_services/token-storage.service";
import {NavigationEnd, Router, RouterEvent} from "@angular/router";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";
import {filter} from "rxjs/operators";
import {UserTestService} from "../_services/user-test.service";
import {UserTest} from "../_models/playTest/UserTest";
import {Test} from "../_models/Test";

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css'],
})
export class BoardUserComponent implements OnInit {

  tests: Array<Test>;
  topics: Array<Topic>;
  userTests: Array<UserTest>;

  buff:Test;

  errorMessage: string;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private testService: TestService,
    private topicService: TopicService,
    private userTestService: UserTestService,
    private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    const textScript = this.renderer2.createElement('script');
    textScript.src = 'assets/mbr-tabs/mbr-tabs.js';
    this.renderer2.appendChild(this.document.body, textScript);

    this.tokenStorage.checkTokenPrivate(this.router);

    this.getTopics();
    this.getTests();
    this.getUserTests();

    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd)).subscribe(() => {
      if (window.location.href.toString().includes("/tests")) {
        window.location.reload();
      } else if (window.location.href.toString().includes("/manager")) {
        window.location.reload();
      }
    });
  }

  //Обработка тематики тестирования
  getNameTopic(id: String): String {
    if (id !=null) {
      return this.topics.find(topic => topic.id === id).name;
    }
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

  //Обработка вкладки Тесты
  getTests() {
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

  //Обработка вкладки Тесты
  getUserTests() {
    this.userTestService.getUserTests().subscribe(
      data => {
        this.userTests = data;
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

  checkTest(idTest: string) {
    for (let buff of this.tests) {
      if (buff.id == idTest) this.buff = buff;
    }
  }

  redirectToTest(id: string): void {
    this.router.navigate(["/test/" + id + '/user'])
  }

  redirectToUserTest(id: string): void {
    this.router.navigate(["/user/test/" + id])
  }
}
