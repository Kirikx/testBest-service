import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Global} from "../global";
import {Test} from "../_models/Test";
import {UserQuestion} from "../_models/playTest/UserQuestion";
import {UserTest} from "../_models/playTest/UserTest";

@Injectable({
  providedIn: 'root'
})
export class UserTestService {

  constructor(private http: HttpClient) {
  }

  //Возвращает лист List<UserTest>
  getUserTests(): Observable<any> {
    return this.http.get(Global.USER_TEST_API, Global.httpOptions);
  }

  //Возвращает последний активный UserTest
  getActiveUserTest(): Observable<any> {
    return this.http.get(Global.USER_TEST_API + 'active', Global.httpOptions);
  }

  //Возвращает вопрос Question (первый вопрос по тесту, после его старта)
  startUserTest(test: Test): Observable<any> {
    return this.http.get(Global.USER_TEST_API + 'test/' + test.id, Global.httpOptions);
  }

  //Возвращает вопрос Question (следующий)
  createUserAnswer(userQuestion: UserQuestion): Observable<any> {
    return this.http.post(Global.USER_TEST_API + 'create-answer', userQuestion, Global.httpOptions);
  }

  //Возвращает следующий вопрос Question
  getNextQuestion(userTest: UserTest): Observable<any> {
    return this.http.get(Global.USER_TEST_API + 'test/'+ userTest.id + '/next-question/', Global.httpOptions);
  }

  //Возвращает лист List<UserQuestion>
  getFailQuestionsByUserTestId(userTest: UserTest): Observable<any> {
    return this.http.get(Global.USER_TEST_API + userTest.id + '/fails', Global.httpOptions);
  }

  //Возвращает UserTest
  getUserTest(userTest: UserTest): Observable<any> {
    return this.http.get(Global.USER_TEST_API + userTest.id, Global.httpOptions);
  }
}
