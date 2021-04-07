import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {UserService} from '../_services/user.service';
import {NavigationEnd, Router, RouterEvent} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {User} from "../_models/users/User";
import {DOCUMENT} from "@angular/common";
import {filter} from "rxjs/operators";
import {TreeviewConfig, TreeviewItem} from "ngx-treeview";
import {FormControl, FormGroup, Validators} from "@angular/forms";
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
  users: Array<User>;

  roles: Array<Role>;
  selectedRoles: Array<Role>;

  buff: User;
  isCreateTopicFailed = false;
  newUser = true;

  showModal = false;

  isCreateUserFailed = false;
  errorMessage: string;

  isSubmitted = true; //нужна валидация форм?

  select = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: true,
    hasCollapseExpand: false,
    maxHeight: 300
  });
  selectData: TreeviewItem[];
  countSelect: number;

  //Обработка user create
  formUserCreate = new FormGroup({
    rolesSelect: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    repeatPassword: new FormControl('', Validators.required),
  });

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

    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }

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
    this.userService.getUser(user).subscribe(
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

  //Обработка ролей
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

  createUser(): void {
    this.userService.createUser(this.user).subscribe(
      data => {
        if (data.id != null && data.id != '') {
          this.isCreateTopicFailed = false;
          this.closeModal();
          this.getUsers();
          this.user = new User();
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

  onSelectedChangeRole(event) {
    // if (this.countSelect > event.length) {
    //   this.user.roles = new Array<Role>();
    // }
    this.user.roles.forEach(role => {
      event.forEach(ev => {
        if (role.id == ev) {
          if (!this.user.roles.find(check => check.id == role.id)) {
            this.user.roles.push(role);
            this.setForValidation()
            this.formUserCreate.patchValue({rolesSelect: this.user.roles})
          }
        }
      })
    })
    this.countSelect = event.length;
  }

  setForValidation() {
    if (this.user.roles != null) {
      // this.questionTypes.forEach(value => {
      //   if (this.user.id != null) {
      //     if (value.id == this.question.questionTypeId && value.name == 'Вопрос со свободным ответом') {
      //       this.answer = this.question.answers[0];
      //       this.formUserCreate.setValue({
      //         rolesSelect: this.user.roles,
      //         questionTypeId: this.question.questionTypeId,
      //         question: this.question.question,
      //         answer: this.answer,
      //         answerTest: this.answer.answer
      //       })
      //     } else {
      //       this.formUserCreate.setValue({
      //         chaptersSelect: this.question.chapters,
      //         questionTypeId: this.question.questionTypeId,
      //         question: this.question.question,
      //         answer: this.question.answers,
      //         answerTest: this.answer.answer
      //       })
      //     }
      //   }
      // });
    } else {
      this.formUserCreate.setValue({
        rolesSelect: this.user.roles,
        username: this.user.username,
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email,
        password: this.user.password,
        repeatPassword: this.user.repeatPassword,

      });
    }
  }

  get validationUserForm() {
    return this.formUserCreate.controls;
  }

  editTopic(): void {
    this.userService.editUser(this.user).subscribe(
      data => {
        if (data.id != null && data.id != '') {
          this.isCreateTopicFailed = false;
          this.closeModal();
          this.getUsers();
          this.user = new User();
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
    this.userService.deleteUser(this.user).subscribe(
      data => {
        this.isCreateTopicFailed = false;
        this.closeModal();
        this.getUsers();
        this.user = new User();
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

  openModalEditUser(id: String) {
    this.showModal = true;
    this.newUser = false;
    this.getUser(this.users.find(topic => topic.id === id));
  }

  openModalNewUser() {
    this.showModal = true;
    this.newUser = true;
    this.user = new User();
  }

  //Обработка модальных окон
  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  getNamesRoles(roles: Array<Role>): String {
    return roles.map(role => role.name).join(" ");
  }
}
