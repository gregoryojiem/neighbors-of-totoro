import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DayService } from "./day.service";
import { Event } from "./Event";
import { TimeRange } from "./TimeRange";
import {Day} from "./Day";
import { AvatarComponent } from './avatar/avatar.component';
import { ModalViewComponent } from './modal-view/modal-view.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AvatarComponent,
    ModalViewComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private dayService : DayService) {
    //var event: Event = {title:"a", description:"swobab", event_id:"1"}
    //var timeRange: TimeRange = {startTime: new Date(3), endTime: new Date(3000000)}
    //var day: Day = {startTime: new Date(30000), endTime: new Date(30000000), date: new Date(12392193), timezone: "000",
    //dayID:""}
    //this.dayService.deleteDay("79430975-6e81-4c36-9afe-8d63cfaa0ae7").subscribe(info => {
    //  console.log(info)
    //})
    //this.eventService.deleteEventHasDay("b88f8551-5d6e-452a-a20b-85f26521dc4e", "79430975-6e81-4c36-9afe-8d63cfaa0ae7").subscribe(info => {
    //  console.log(info)
    //})
  }
}
