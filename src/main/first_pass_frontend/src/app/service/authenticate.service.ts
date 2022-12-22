import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post('/api/auth/login', {username, password},
      {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), observe: 'response'});
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post('/api/auth/register', {username, email, password},
      {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), observe: 'response'});
  }
}
