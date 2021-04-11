import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Global} from "../global";
import {Role} from "../_models/users/Role";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) {
  }

  getRoles(): Observable<any> {
    return this.http.get(Global.ROLE_API, Global.httpOptions);
  }

  getRole(id: string): Observable<any> {
    return this.http.get(Global.ROLE_API + id, Global.httpOptions);
  }

}
