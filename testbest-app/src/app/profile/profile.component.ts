import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {Router} from "@angular/router";
import {User} from "../_models/users/User";
import {UserService} from "../_services/user.service";
import {DOCUMENT} from "@angular/common";
import {Auth} from "../_models/users/Auth";
import {AuthService} from "../_services/auth.service";
import {RoleService} from "../_services/role.service";
import {Role} from "../_models/users/Role";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  errorMessage: string;

  currentUserToken: any;
  currentUser = new User();
  editUser = new User();

  managerRole = new Role();
  isManagerFlag = false;

  showModal = false;
  auth = new Auth();

  failConfirmPassword = false;
  isSubmitted = false;
  isEditUserFailed = false;

  showModalChangePassword = false;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private tokenStorage: TokenStorageService,
    private userService: UserService,
    private authService: AuthService,
    private roleService: RoleService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.tokenStorage.checkTokenPrivate(this.router);

    this.currentUserToken = this.tokenStorage.getUser();
    this.getCurrentUser()
    this.getManagerRole();
  }

  getCurrentUser() {
    this.userService.getUser(this.currentUserToken.id).subscribe(
      data => {
        this.currentUser = data;
        this.auth.username = data.username;
        this.isManager(this.currentUser);
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

  // Редакирование данных пользователя
  editUserProfile() {
      this.userService.editUser(this.editUser).subscribe(
        data => {
          this.closeModal();
          this.getCurrentUser()
        },
        error => {
          if (error.statusText == "Unknown Error") {
            this.errorMessage = "Server is not responding";
          } else {
            this.errorMessage = error.message;
          }
          this.isSubmitted = false;
        }
      );
  }

  // Подтверждение пароля c передачей управления методу редактирования
  confirmPasswordAndEditUserProfile() {
    this.authService.login(this.auth).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.failConfirmPassword = false;
        this.editUserProfile();
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
        this.failConfirmPassword = true;
      }
    );
  }

  //Обработка модальных окон
  openModal() {
    this.editUser.cloneUser(this.currentUser);
    this.showModal = true;
    this.isManager(this.editUser);
  }

  openModalChangePassword() {
    this.editUser.cloneUser(this.currentUser);
    this.editUser.password = '';
    this.showModalChangePassword = true;
  }

  closeModal() {
    this.showModal = false;
    this.showModalChangePassword = false
    this.auth.password = "";
    this.editUser = new User();
  }

  isManager(user: User)  {
    this.isManagerFlag = user.roles.map(role => role.name).includes("ROLE_MANAGER");
  }

  private getManagerRole() {
    this.roleService.getRoles().subscribe(
      data => {
        this.managerRole = data.filter(role => role.name == "ROLE_MANAGER")[0];
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

  changeManagerRole($event: Event) {
    if (this.editUser.roles.map(role => role.name).includes(this.managerRole.name)) {
      this.editUser.roles = this.editUser.roles.filter(role => role.name != this.managerRole.name);
    } else {
      this.editUser.roles.push(this.managerRole);
    }
  }

  confirmPasswordAndChangePassword() {
    this.authService.login(this.auth).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.failConfirmPassword = false;
        this.changePass();
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
        this.failConfirmPassword = true;
      }
    );
  }

  changePass() {
    this.userService.resetPassUser(this.editUser).subscribe(
      data => {
        if (data.id != null && data.id != '') {
          this.closeModal();
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
}
