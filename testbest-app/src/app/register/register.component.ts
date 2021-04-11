import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {User} from "../_models/users/User";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService,
              private router: Router,
              private tokenStorage: TokenStorageService) {
    this.tokenStorage.checkTokenPublic(this.router);
    this.user = new User();
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(this.user);
    this.authService.register(this.user).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
        this.isSignUpFailed = true;
      }
    );
  }
}
