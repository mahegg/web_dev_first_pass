import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HelloService {

  constructor(private http: HttpClient) {
  }

  public hello(): Observable<any> {
    return this.http.get<any>('/api/hello-world');
  }

  public findAll(): Observable<any> {
    return this.http.get<any>('/api/hello-world');
  }

}
