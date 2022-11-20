import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router'
import { LandingComponent } from "./landing/landing.component";
import { ProfileComponent } from "./profile/profile.component";

const routes: Routes = [
  { path: 'home', component: LandingComponent },
  { path: 'profile', component: ProfileComponent },

  { path: '',   redirectTo: 'home', pathMatch: 'full' }
]

@NgModule({
  declarations: [],
  imports: [
    [RouterModule.forRoot(routes)],
    CommonModule,

  ],
  exports: [RouterModule]
})

export class AppRoutingModule { }
