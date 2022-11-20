import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DayService } from "./day.service";
import { Event } from "./Event";
import { TimeRange } from "./TimeRange";
import { Day } from "./Day";
import { AvatarComponent } from './avatar/avatar.component';
import { ModalViewComponent } from './modal-view/modal-view.component';
import { UserService } from "./user.service";
import { LandingComponent } from './landing/landing.component';
import { ProfileComponent } from './profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AvatarComponent,
    ModalViewComponent,
    LandingComponent,
    ProfileComponent
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
  constructor(private userService : UserService) {
  }
}
