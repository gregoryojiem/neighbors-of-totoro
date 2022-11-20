import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { EventService } from "./event.service";
import { Event } from "./Event";
import { TimeRange } from "./TimeRange";=
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private eventService : EventService) {
    //var event: Event = {title:"a", description:"swobab", event_id:"1"}
    //var timeRange: TimeRange = {startTime: new Date(3), endTime: new Date(3000000)}
    //this.eventService.deleteEventHasDay("b88f8551-5d6e-452a-a20b-85f26521dc4e", "79430975-6e81-4c36-9afe-8d63cfaa0ae7").subscribe(info => {
    //  console.log(info)
    //})
  }
}
