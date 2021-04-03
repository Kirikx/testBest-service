import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Global} from "../global";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  //Todo Тут будет код про работе юзера

  // getPublicContent(): Observable<any> {
  //   return this.http.get(API_URL + 'all', { responseType: 'text' });
  // }
  //
  // // getUserBoard(): Observable<any> {
  // //   return this.http.get(API_URL + 'user', { responseType: 'text' });
  // // }

  // getManagerBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'mod', { responseType: 'text' });
  // }

  // getAdminBoard(): Observable<any> {
  //   return this.http.get(API_URL + 'admin', { responseType: 'text' });
  // }
}
