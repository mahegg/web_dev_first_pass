import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserRecordService {

  constructor(private http: HttpClient) { }

  getUserRecords(): Observable<any> {
    return this.http.get<any>('/api/user/records');
  }

  saveRecord(result: any) {
    return this.http.post('/api/user/create', result,
      {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), observe: 'response'});
  }

  deleteRecord(id: any) {
    return this.http.delete(`/api/user/delete/${id}`,
      {headers: new HttpHeaders({ 'Content-Type': 'application/json' }), observe: 'response'});
  }
}
