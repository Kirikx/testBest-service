import { Injectable } from '@angular/core';
import {Global} from "../global";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Topic} from "../_models/createTest/parameters/Topic";

const TOPIC_API = Global.PROD + '/api/topics/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {
  }

  getTopics(): Observable<any> {
    return this.http.get(TOPIC_API, httpOptions);
  }

  getTopic(topic:Topic): Observable<any> {
    return this.http.get(TOPIC_API + topic.id, httpOptions);
  }

  deleteTopic(topic:Topic): Observable<any> {
    return this.http.delete(TOPIC_API + topic.id, httpOptions);
  }

  editTopic(topic:Topic): Observable<any> {
    return this.http.put(TOPIC_API, topic, httpOptions);
  }

  createTopic(topic: Topic): Observable<any> {
    return this.http.post(TOPIC_API + 'create', topic, httpOptions);
  }
}
