import { Injectable } from '@angular/core';
import {Global} from "../global";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Topic} from "../_models/createTest/parameters/Topic";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {
  }

  getTopics(): Observable<any> {
    return this.http.get(Global.TOPIC_API, Global.httpOptions);
  }

  getTopic(topic:Topic): Observable<any> {
    return this.http.get(Global.TOPIC_API + topic.id, Global.httpOptions);
  }

  deleteTopic(topic:Topic): Observable<any> {
    return this.http.delete(Global.TOPIC_API + topic.id, Global.httpOptions);
  }

  editTopic(topic:Topic): Observable<any> {
    return this.http.put(Global.TOPIC_API, topic, Global.httpOptions);
  }

  createTopic(topic: Topic): Observable<any> {
    return this.http.post(Global.TOPIC_API + 'create', topic, Global.httpOptions);
  }
}
