import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Test} from "../_models/createTest/Test";
import {Global} from "../global";

@Injectable({
  providedIn: 'root'
})
export class TestService {
  constructor(private http: HttpClient) {
  }

  getTests(): Observable<any> {
    return this.http.get(Global.TEST_API, Global.httpOptions);
  }

  //TODO Не хватает!!!
  // getManagerTest(): Observable<any> {
  //   return this.http.get(TEST_API, httpOptions);
  // }

  getTest(test: Test): Observable<any> {
    return this.http.get(Global.TEST_API + test.id, Global.httpOptions);
  }

  editTest(test: Test): Observable<any> {
    return this.http.put(Global.TEST_API, test, Global.httpOptions);
  }

  deleteTest(test: Test): Observable<any> {
    return this.http.delete(Global.TEST_API + test.id, Global.httpOptions);
  }

  createTest(test: Test): Observable<any> {
    return this.http.post(Global.TEST_API + 'create', test, Global.httpOptions);
  }

}
