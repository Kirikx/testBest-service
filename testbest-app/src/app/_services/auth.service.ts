import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Global} from "../global";
import {Auth} from "../_models/auth";
import {User} from "../_models/user";

const AUTH_API = Global.PROD + '/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  login(auth: Auth): Observable<any> {
    return this.http.post(AUTH_API + 'signin', auth, httpOptions);
  }

  register(user: User): Observable<any> {
    return this.http.post(AUTH_API + 'signup', user, httpOptions);
  }
}
