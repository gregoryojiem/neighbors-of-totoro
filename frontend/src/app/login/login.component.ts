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
    this.userService.createUser(user).subscribe(info => {
      this.userService.getUser(info[1] as string).subscribe(newUser => {
        this.userService.setStoredUser(newUser)
        this.activeModal.close()
      })
    })
  }

  loginToProfile(username: string, password: string) {
    this.userService.getUserByUsername(username).subscribe(storedUser => {
      this.userService.verifyPassword(storedUser.userID, storedUser.password, password).subscribe(correctPass => {
        if (correctPass) {
          this.userService.setStoredUser(storedUser)
          this.router.navigate(['/profile'])
          this.activeModal.close()
        }
      })
    })
  }
}
