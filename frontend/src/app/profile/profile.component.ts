import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {EventService} from "../event.service";
import {Event} from "../Event";
import {User} from "../User";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  public username: string = "user"
  public myEvents: Event[] = []

  constructor(private userService: UserService, private eventService: EventService) { }

  ngOnInit(): void {
    this.username = this.userService.getStoredUser().username
    this.userService.getUserEvents(this.userService.getStoredUser().userID).subscribe(info => {
      this.myEvents = info
      console.log(this.myEvents)
    })
  }

  createEvent(eventTitle: string, eventDescription: string): void {
    var event: Event = {title: eventTitle, description: eventDescription, event_id: ""}
    this.eventService.createEvent(event).subscribe(info => {
      this.eventService.getEvent(info[1] as string).subscribe(eventInfo => {
        this.userService.createUserParticipatesEvent(this.userService.getStoredUser().userID, eventInfo.event_id).subscribe()
        this.ngOnInit()
      })
    })
  }
}
