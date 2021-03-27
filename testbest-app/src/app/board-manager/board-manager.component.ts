import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {Test} from "../_models/test";
import {ManagerService} from "../_services/manager.service";

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-manager.component.html',
  styleUrls: ['./board-manager.component.css']
})
export class BoardManagerComponent implements OnInit {
  test: Test;
  showFirstModal = false;
  showSecondModal = false;
  errorMessage: string;
  tests: Array<Test>;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private managerService: ManagerService) {
    this.test = new Test()
  }

  ngOnInit(): void {
    const textScript = this.renderer2.createElement('script');
    textScript.src = 'assets/mbr-tabs/mbr-tabs.js';
    this.renderer2.appendChild(this.document.body, textScript);

    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }

    //TODO Будут запросы на получение данных:
    //      список тестов
    // this.userService.getUserBoard().subscribe(
    //   data => {
    //     this.content = data;
    //   },
    //   err => {
    //     this.content = JSON.parse(err.error).message;
    //   }
    // );
  }

  createTest(): void {
    this.managerService.createTest(this.test).subscribe(
      data => {
        if (data.statusCode == "200") {
          this.managerService.getManagerTest().subscribe(
            data => {
              console.log(data);
              console.log('==============');
              this.tests = data;;
              console.log(this.tests);
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

  openFirstModal() {
    this.showFirstModal = true;
  }

  closeFirstModal() {
    this.showFirstModal = false;
  }

  openSecondModal() {
    this.showSecondModal = true;
  }

  closeSecondModal() {
    this.showSecondModal = false;
  }
}
