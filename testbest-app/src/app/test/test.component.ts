import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  id

  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.tokenStorage.checkTokenPrivate(this.router);

    this.id = this.route.snapshot.paramMap.get('id');
  }

}
