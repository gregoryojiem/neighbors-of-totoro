import {Component, OnInit, TemplateRef} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {UserService} from "../user.service";
import {User} from "../User";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private activeModal: NgbActiveModal, private router: Router, private userService: UserService) { }

  ngOnInit(): void {
  }

  close() {
    this.activeModal.close()
  }

  createAccount(email: string, username: string, password: string) {
    var user: User = {email: email, username: username, password: password, avatar: 0, userID: ""}
    this.userService.createUser(user).subscribe()
  }

  loginToProfile(username: string, password: string) {
    this.userService.getUserByUsername(username).subscribe(info => {
      this.userService.verifyPassword(info.userID, info.password, password).subscribe(info => {
        if (info) {
          this.activeModal.close()
          this.router.navigate(['/profile'])
        }
      })
    })
  }
}
