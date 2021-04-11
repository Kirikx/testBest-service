import {Injectable} from '@angular/core';
import {Global} from "../global";
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from "../_models/users/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<any> {
    return this.http.get(Global.USER_API, Global.httpOptions);
  }

  getUser(id: string): Observable<any> {
    return this.http.get(Global.USER_API + id, Global.httpOptions);
  }

  deleteUser(user: User): Observable<any> {
    return this.http.delete(Global.USER_API + user.id, Global.httpOptions);
  }

  editUser(user: User): Observable<any> {
    return this.http.put(Global.USER_API, user, Global.httpOptions);
  }

  createUser(user: User): Observable<any> {
    return this.http.post(Global.USER_API + 'create', user, Global.httpOptions);
  }

  resetPassUser(user: User): Observable<any> {
    return this.http.put(Global.USER_API + 'reset', user, Global.httpOptions);
  }
}
