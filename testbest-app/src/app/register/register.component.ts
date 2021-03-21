import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {
    username: null,
    surname: null,
    email: null,
    login: null,
    password: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router, private tokenStorage: TokenStorageService) {
    if (tokenStorage.getToken() != null) router.navigate(["/home"])
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    //const {username, surname, email, login, password} = this.form;
    const {username, email, password} = this.form;

    this.authService.register(username, email, password).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else if (error.statusCode == "401") {
          this.errorMessage = "Incorrect logs or password";
        } else {
          this.errorMessage = error.message;
        }
        this.isSignUpFailed = true;
      }
    );
  }
}
