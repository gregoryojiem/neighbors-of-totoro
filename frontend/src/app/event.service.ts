import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Event } from './Event';
import { Day } from "./Day";
import { TimeRange} from "./TimeRange";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private eventURL = 'http://localhost:8080/api/events';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(private http: HttpClient) { }

  public createEvent(event: Event): Observable<Object[]> {
    return this.http.post<Object[]>(this.eventURL, JSON.stringify(event), this.httpOptions)
  }

  public getEvent(eventID: string): Observable<Event> {
    return this.http.get<Event>(this.eventURL + "/" + eventID)
  }

  public updateEvent(eventID: string, event: Event): Observable<number> {
    return this.http.put<number>(this.eventURL + "/" + eventID, JSON.stringify(event), this.httpOptions)
  }

  public deleteEvent(eventID: string): Observable<number> {
    return this.http.delete<number>(this.eventURL + "/" + eventID)
  }

  public createEventHasDay(timeRange: TimeRange, eventID: string, dayID: string): Observable<number> {
    return this.http.post<number>(this.eventURL + "/" + eventID + "/days/" + dayID, JSON.stringify(timeRange),
      this.httpOptions)
  }

  public getDayInEvent(eventID: string, dayID: string): Observable<Day> {
    return this.http.get<Day>(this.eventURL + "/" + eventID + "/days/" + dayID)
  }

  public getTimeRangeByDayInEvent(eventID: string, dayID: string): Observable<TimeRange> {
    return this.http.get<TimeRange>(this.eventURL + "/" + eventID + "/days/" + dayID + "/time")
  }

  public getAllDaysInEvent(eventID: string): Observable<Day[]> {
    return this.http.get<Day[]>(this.eventURL + "/" + eventID + "/days")
  }

  public deleteEventHasDay(eventID: string, dayID: string) {
    return this.http.delete<Day>(this.eventURL + "/" + eventID + "/days/" + dayID)
  }
}
