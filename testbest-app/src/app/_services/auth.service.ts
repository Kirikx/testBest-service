import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Global} from "../global";
import {Auth} from "../_models/users/Auth";
import {User} from "../_models/users/User";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  login(auth: Auth): Observable<any> {
    return this.http.post(Global.AUTH_API + 'signin', auth, Global.httpOptions);
  }

  register(user: User): Observable<any> {
    return this.http.post(Global.AUTH_API + 'signup', user, Global.httpOptions);
  }
}
