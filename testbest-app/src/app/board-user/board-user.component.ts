import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import { UserService } from '../_services/user.service';
import {DOCUMENT} from "@angular/common";
import {TokenStorageService} from "../_services/token-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css'],
})
export class BoardUserComponent implements OnInit {
  content?: string;

  // constructor(private userService: UserService) { }
  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private tokenStorage: TokenStorageService) {
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
}
