import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TestFull} from "../_models/createTest/TestFull";
import {Global} from "../global";
import {Test} from "../_models/Test";
import {User} from "../_models/users/User";

@Injectable({
  providedIn: 'root'
})
export class TestService {
  constructor(private http: HttpClient) {
  }

  getTests(): Observable<any> {
    return this.http.get(Global.TEST_API, Global.httpOptions);
  }

  getTest(test: Test): Observable<any> {
    return this.http.get(Global.TEST_API + test.id, Global.httpOptions);
  }

  getTestFull(test: TestFull): Observable<any> {
    return this.http.get(Global.TEST_API + test.id, Global.httpOptions);
  }

  editTestFull(test: TestFull): Observable<any> {
    return this.http.put(Global.TEST_API, test, Global.httpOptions);
  }

  deleteTestFull(test: TestFull): Observable<any> {
    return this.http.delete(Global.TEST_API + test.id, Global.httpOptions);
  }

  createTestFull(test: TestFull): Observable<any> {
    return this.http.post(Global.TEST_API + 'create', test, Global.httpOptions);
  }

  getUserTests(user: User): Observable<any> {
    return this.http.get(Global.TEST_API + 'user-test/' + user.id, Global.httpOptions);
  }

  getCurrentUserTests(): Observable<any> {
    return this.http.get(Global.TEST_API + 'user-test', Global.httpOptions);
  }

  getTestsByUserId(user: User): Observable<any> {
    return this.http.get(Global.TEST_API + 'user/' + user.id, Global.httpOptions);
  }

  getMyTests(): Observable<any> {
    return this.http.get(Global.TEST_API + 'user', Global.httpOptions);
  }

  getTestsByTopicId(id: String): Observable<any> {
    return this.http.get(Global.TEST_API + 'topic/' + id, Global.httpOptions);
  }

}
