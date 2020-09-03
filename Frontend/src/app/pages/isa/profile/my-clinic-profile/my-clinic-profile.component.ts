import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-clinic-profile',
  templateUrl: './my-clinic-profile.component.html',
  styleUrls: ['./my-clinic-profile.component.scss']
})
export class MyClinicProfileComponent implements OnInit {

  public type: String;

  constructor() { }

  ngOnInit() {
    this.setType();
  }

  private setType(): void { //getType
    const user = JSON.parse(localStorage.getItem('user'));
    this.type = user.userType;
  }

}
