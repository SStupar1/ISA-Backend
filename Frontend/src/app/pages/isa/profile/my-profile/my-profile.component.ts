import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {

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