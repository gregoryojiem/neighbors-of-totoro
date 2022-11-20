import {Component, OnInit, TemplateRef} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private activeModal: NgbActiveModal, private router: Router) { }

  ngOnInit(): void {
  }

  close() {
    this.activeModal.close()
  }

  loginToProfile() {
    this.activeModal.close()
    this.router.navigate(['/profile'])
  }
}
