import {Injectable} from '@angular/core';
import {HttpHeaders} from "@angular/common/http";

@Injectable()
export class Global {
  static PROD: string = 'http://devstend.ru:8080';
  // static PROD: string = 'http://localhost:8080';

  static httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  static AUTH_API = Global.PROD + '/api/auth/';
  static TOPIC_API = Global.PROD + '/api/topics/';
  static TEST_API = Global.PROD + '/api/tests/';
  static CHAPTER_API = Global.PROD + '/api/chapters/';
  static QUESTION_TYPE_API = Global.PROD + '/api/type_questions/';
  static QUESTION_API = Global.PROD + '/api/questions/';
  static ANSWER_API = Global.PROD + '/api/answers/';
  static USER_API = Global.PROD + '/api/users/';
  static ROLE_API = Global.PROD + '/api/roles/';
  static USER_TEST_API = Global.PROD + '/api/user-test/';
}
