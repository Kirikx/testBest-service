import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Global} from "../global";
import {Question} from "../_models/Question";
import {QuestionFull} from "../_models/createTest/QuestionFull";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) {
  }

  getQuestions(): Observable<any> {
    return this.http.get(Global.QUESTION_API, Global.httpOptions);
  }

  getQuestion(question: Question): Observable<any> {
    return this.http.get(Global.QUESTION_API + question.id, Global.httpOptions);
  }

  getFullQuestions(): Observable<any> {
    return this.http.get(Global.QUESTION_API + 'full', Global.httpOptions);
  }

  getFullQuestion(questionFull: QuestionFull): Observable<any> {
    return this.http.get(Global.QUESTION_API + 'full/' + questionFull.id, Global.httpOptions);
  }

  editQuestion(questionFull: QuestionFull): Observable<any> {
    return this.http.put(Global.QUESTION_API, questionFull, Global.httpOptions);
  }

  deleteQuestion(questionFull: QuestionFull): Observable<any> {
    return this.http.delete(Global.QUESTION_API + questionFull.id, Global.httpOptions);
  }

  createQuestion(questionFull: QuestionFull): Observable<any> {
    return this.http.post(Global.QUESTION_API + 'create', questionFull, Global.httpOptions);
  }

}
