import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Test} from "../_models/createTest/Test";
import {Global} from "../global";

const TEST_API = Global.PROD + '/api/tests/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TestService {
  constructor(private http: HttpClient) {
  }

  getTests(): Observable<any> {
    return this.http.get(TEST_API, httpOptions);
  }

  //TODO Не хватает!!!
  // getManagerTest(): Observable<any> {
  //   return this.http.get(TEST_API, httpOptions);
  // }

  getTest(test: Test): Observable<any> {
    return this.http.get(TEST_API + test.id, httpOptions);
  }

  editTest(test: Test): Observable<any> {
    return this.http.put(TEST_API,test, httpOptions);
  }

  deleteTest(test: Test): Observable<any> {
    return this.http.delete(TEST_API + test.id, httpOptions);
  }

  createTest(test:Test): Observable<any> {
    return this.http.post(TEST_API + 'create', test, httpOptions);
  }

}
