import {Component, OnInit} from '@angular/core';
import {UserService} from '../_services/user.service';
import {Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  content?: string;

  constructor(private userService: UserService,
              private router: Router,
              private tokenStorage: TokenStorageService) {

  }

  ngOnInit(): void {
    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }

    // this.userService.getAdminBoard().subscribe(
    //   data => {
    //     this.content = data;
    //   },
    //   err => {
    //     this.content = JSON.parse(err.error).message;
    //   }
    // );
  }
}
