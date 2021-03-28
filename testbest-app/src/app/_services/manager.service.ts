import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Test} from "../_models/test";
import {Globals} from "../global";

const TEST_API = Globals.PROD + '/api/tests/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ManagerService {
  constructor(private http: HttpClient) {
  }

  createTest(test:Test): Observable<any> {
    return this.http.post(TEST_API + 'create', test, httpOptions);
  }

  getManagerTest(): Observable<any> {
    return this.http.get(TEST_API, httpOptions);
  }
}
