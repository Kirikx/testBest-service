import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Global} from "../global";
import {AnswerFull} from "../_models/createTest/AnswerFull";
import {Answer} from "../_models/Answer";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient) {
  }

  getAnswers(): Observable<any> {
    return this.http.get(Global.ANSWER_API, Global.httpOptions);
  }

  getAnswer(answer: Answer): Observable<any> {
    return this.http.get(Global.ANSWER_API + answer.id, Global.httpOptions);
  }

  getFullAnswers(): Observable<any> {
    return this.http.get(Global.ANSWER_API + 'full', Global.httpOptions);
  }

  getFullAnswer(answerFull: AnswerFull): Observable<any> {
    return this.http.get(Global.ANSWER_API + 'full/' + answerFull.id, Global.httpOptions);
  }

  editAnswer(answerFull: AnswerFull): Observable<any> {
    return this.http.put(Global.ANSWER_API, answerFull, Global.httpOptions);
  }

  createAnswer(answerFull: AnswerFull): Observable<any> {
    return this.http.post(Global.ANSWER_API + 'create', answerFull, Global.httpOptions);
  }
}
