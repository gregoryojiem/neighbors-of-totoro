import { Injectable } from '@angular/core';
import {User} from "./User";
import {Event} from "./Event";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {TimeRange} from "./TimeRange";
import {Time} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userURL = 'http://localhost:8080/api/users';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(private http: HttpClient) { }

  public createUser(user: User): Observable<number> {
    return this.http.post<number>(this.userURL, JSON.stringify(user), this.httpOptions)
  }

  public getUser(userID: string): Observable<User> {
    return this.http.get<User>(this.userURL + "/" + userID)
  }

  public getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(this.userURL + "/username/" + username)
  }

  public updateUser(userID: string, user: User): Observable<number> {
    return this.http.put<number>(this.userURL + "/" + userID, JSON.stringify(user), this.httpOptions)
  }

  public deleteUser(userID: string): Observable<number> {
    return this.http.delete<number>(this.userURL + "/" + userID)
  }

  public getUserEvent(userID: string, eventID: string): Observable<Event> {
    return this.http.get<Event>(this.userURL + "/" + userID + "/events/" + eventID)
  }

  public getUserEvents(userID: string): Observable<Event[]> {
    return this.http.get<Event[]>(this.userURL + "/" + userID + "/events")
  }

  public createUserParticipatesEvent(userID: string, eventID: string): Observable<number> {
    console.log(this.userURL + "/" + userID + "/events/" + eventID)
    return this.http.post<number>(this.userURL + "/" + userID + "/events/" + eventID, this.httpOptions)
  }

  public deleteUserParticipatesEvent(userID: string, eventID: string): Observable<number> {
    return this.http.delete<number>(this.userURL + "/" + userID + "/events/" + eventID)
  }

  public createInvitation(eventID: string, inviterID: string, inviteeID: string): Observable<any> {
    return this.http.post<any>("http://localhost:8080/api/events/" + eventID + "/invites/" + inviterID + "/-/" + inviteeID,
      this.httpOptions)
  }

  public getAllEventByInvitationsReceived(inviteeID: string): Observable<Event[]> {
    return this.http.get<Event[]>("http://localhost:8080/api/events/" + inviteeID + "/invites")
  }

  public deleteInvitation(eventID: string, inviterID: string, inviteeID: string): Observable<number> {
    return this.http.delete<number>("http://localhost:8080/api/events/" + eventID + "/invites/" + inviterID + "/-/" + inviteeID)
  }

  public createUserAvailabilityDay(timeRange: TimeRange, userID: string, dayID: string): Observable<number> {
    return this.http.post<number>(this.userURL + "/" + userID + "/days/" + dayID, JSON.stringify(timeRange), this.httpOptions)
  }

  public getAllAvailabilityRangesForDay(userID: string, dayID: string): Observable<TimeRange> {
    return this.http.get<TimeRange>(this.userURL + "/" + userID + "/days/" + dayID)
  }

  public deleteUserAvailabilityDay(timeRange: TimeRange, userID: string, dayID: string): Observable<any> {
    return this.http.request<any>("delete", this.userURL + "/" + userID + "/days/" + dayID,
      {body: JSON.stringify(timeRange), headers: new HttpHeaders({ 'Content-Type': 'application/json' })})
  }

  public verifyPassword(userID: string, hashedPass: string, pass: string): Observable<boolean> {
    return this.http.get<boolean>("http://localhost:8080/api/verify/" + userID + "/" + hashedPass + "/" + pass)
  }
}
