import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  getUsers(): Observable<any> {
    return this.http.get<any>('/api/admin/users');
  }

  deleteUser(id: any)  {
    return this.http.delete(`/api/admin/delete/${id}`,
      {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), observe: 'response'});

  }
}
