import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {TokenStorageService} from '../_services/token-storage.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) {
    if (tokenStorage.getToken() != null) router.navigate(["/home"])
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit(): void {
    const {username, password} = this.form;

    this.authService.login(username, password).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
     //   this.reloadPage();

    //    setTimeout(()=>{
     //     if (this.roles.length > 1) {
    //        this.reloadComponent("/home")
            this.router.navigate(["/home"]);
    /*      } else if (this.roles[0] == 'ROLE_USER') {
            this.reloadComponent("/tests")
          //  this.router.navigate(["/tests"]);
          } else if (this.roles[0] == 'ROLE_MANAGER') {
            this.reloadComponent("/manager")
            this.router.navigate(["/manager"]);
          } else if (this.roles[0] == 'ROLE_ADMIN') {
            this.router.navigate(["/admin"]);
     //       this.reloadComponent("/admin")
          }*/
     //   }, 5000);

      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage() {
    window.location.reload();
  }

  // reloadComponent(path) {
  //   this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  //   this.router.onSameUrlNavigation = 'reload';
  //   this.router.navigate([path]);
  // }
}
