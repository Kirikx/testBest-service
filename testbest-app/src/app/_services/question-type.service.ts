import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Global} from "../global";
import {QuestionType} from "../_models/createTest/parameters/QuestionType";

@Injectable({
  providedIn: 'root'
})
export class QuestionTypeService {

  constructor(private http: HttpClient) {
  }

  getQuestionTypes(): Observable<any> {
    return this.http.get(Global.QUESTION_TYPE_API, Global.httpOptions);
  }

  getQuestionType(questionType: QuestionType): Observable<any> {
    return this.http.get(Global.QUESTION_TYPE_API + questionType.id, Global.httpOptions);
  }

}
