import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {UserService} from '../_services/user.service';
import {NavigationEnd, Router, RouterEvent} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {User} from "../_models/users/User";
import {DOCUMENT} from "@angular/common";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  //Переменные для Users
  user: User;
  users: Array<User>;

  showModal = false;
  errorMessage: string;

  constructor(private userService: UserService,
              @Inject(DOCUMENT) private document: Document,
              private renderer2: Renderer2,
              private router: Router,
              private tokenStorage: TokenStorageService) {
    this.user = new User();
  }

  ngOnInit(): void {
    const textScript = this.renderer2.createElement('script');
    textScript.src = 'assets/mbr-tabs/mbr-tabs.js';
    this.renderer2.appendChild(this.document.body, textScript);

    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }

    this.getUsers();

    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd)
    ).subscribe(() => {
      if (window.location.href.toString().includes("/admin")) {
        window.location.reload();
      }
    });

  }

  getUsers() {
    this.userService.getUsers().subscribe(
      data => {
        this.users = data;
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

  redirectToCreateUser(id: string): void {
    this.router.navigate(["/user/" + id])
  }

  //Обработка модальных окон
  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }
}
