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
    firstName: null,
    lastName: null,
    username: null,
    password: null,
    email: null,
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
    const {firstName, lastName, username, password, email} = this.form;

    this.authService.register(firstName, lastName, username, password, email).subscribe(
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
