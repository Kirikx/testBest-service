import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {UserService} from '../_services/user.service';
import {NavigationEnd, Router, RouterEvent} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {User} from "../_models/users/User";
import {DOCUMENT} from "@angular/common";
import {filter} from "rxjs/operators";
import {TreeviewConfig} from "ngx-treeview";
import {Role} from "../_models/users/Role";
import {RoleService} from "../_services/role.service";

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  //Переменные для Users
  user: User;

  users: Array<User>; // список пользователей
  roles: Array<Role>; // список всех ролей

  showModal = false;

  isEditUserFailed = false;
  errorMessage: string;

  isSubmitted = true; //нужна валидация форм?

  select = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: true,
    hasCollapseExpand: false,
    maxHeight: 300
  });

  countSelect: number;

  constructor(private userService: UserService,
              private roleService: RoleService,
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

    this.tokenStorage.checkTokenPrivate(this.router);

    this.getUsers();
    this.getRoles();

    this.router.events.pipe(
      filter((event: RouterEvent) => event instanceof NavigationEnd)
    ).subscribe(() => {
      if (window.location.href.toString().includes("/admin")) {
        window.location.reload();
      }
    });

  }

  getUser(user: User) {
    this.userService.getUser(user.id).subscribe(
      data => {
        this.user = data;
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

  getRoles() {
    this.roleService.getRoles().subscribe(
      data => {
        this.roles = data;
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

  editUser(): void {
    console.log(this.user);
    this.userService.editUser(this.user).subscribe(
      data => {
        if (data.id != null && data.id != '') {
          this.isEditUserFailed = false;
          this.closeModal();
          this.getUsers();
          this.user = new User();
        }
      },
      error => {
        this.isEditUserFailed = true;
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    );
  }

  resetPass() {
    this.editUser();
    this.user.password = 'password'; // default password
    this.userService.resetPassUser(this.user).subscribe(
      data => {
        if (data.id != null && data.id != '') {
          this.isEditUserFailed = false;
          this.closeModal();
          this.getUsers();
          this.user = new User();
        }
      },
      error => {
        this.isEditUserFailed = true;
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    );
  }

  deleteUser(): void {
    this.userService.deleteUser(this.user).subscribe(
      data => {
        this.isEditUserFailed = false;
        this.closeModal();
        this.getUsers();
        this.user = new User();
      },
      error => {
        this.isEditUserFailed = true;
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    );
  }

  openModalEditUser(id: String) {
    this.showModal = true;
    this.getUser(this.users.find(topic => topic.id === id));
  }

  //Обработка модальных окон
  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  // предобработка наименования роли
  getNamesRoles(roles: Array<Role>): String {
    return roles.map(role => role.name.substring(5)).join(", ");
  }

  isActiveRole(role: Role): boolean {
    return  this.user.roles.map(r => r.name).includes(role.name)
  }

  changeRole(changeRole: Role) {
    if (this.user.roles.map(role => role.name).includes(changeRole.name)) {
      this.user.roles = this.user.roles.filter(role => role.name != changeRole.name);
    } else {
      this.user.roles.push(changeRole);
    }
  }
}
