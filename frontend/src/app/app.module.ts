import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { DayService } from "./day.service";
import { Event } from "./Event";
import { TimeRange } from "./TimeRange";
import {Day} from "./Day";
import {User} from "./User";
import {UserService} from "./user.service";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private userService : UserService) {
  }
}
