import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Day} from "./Day";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DayService {
  private dayURL = 'http://localhost:8080/api/days';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(private http: HttpClient) { }

  public createDay(day: Day): Observable<number> {
    return this.http.post<number>(this.dayURL, JSON.stringify(day), this.httpOptions)
  }

  public getDay(dayID: string): Observable<Day> {
    return this.http.get<Day>(this.dayURL + "/" + dayID)
  }

  public updateDay(dayID: string, day: Day): Observable<number> {
    return this.http.put<number>(this.dayURL + "/" + dayID, JSON.stringify(day), this.httpOptions)
  }

  public deleteDay(dayID: string): Observable<number> {
    return this.http.delete<number>(this.dayURL + "/" + dayID)
  }
}
