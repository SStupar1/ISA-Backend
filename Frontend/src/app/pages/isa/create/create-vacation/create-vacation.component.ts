import { Component, OnInit } from '@angular/core';
import { MedicalService } from 'src/app/services/medical.service';
import * as moment from 'moment';

@Component({
  selector: 'app-create-vacation',
  templateUrl: './create-vacation.component.html',
  styleUrls: ['./create-vacation.component.scss']
})
export class CreateVacationComponent implements OnInit {

  startAt: Date = null;
  endAt: Date = null;
  plainFooter = 'plain extra footer';
  footerRender = () => 'extra footer';
  public listOfDates = [];

  constructor(private medicalService: MedicalService) { }

  ngOnInit() {
  }

  onChange(result: Date): void {
    console.log('onChange: ', result);

  }

  scheduleVacation() {
    console.log(this.startAt.getDate());
    const month = this.startAt.getMonth();
    const year = this.startAt.getFullYear();
    console.log(month)
    console.log(year)
    for (let i = this.startAt.getDate(); i < this.endAt.getDate(); i++) {
      this.listOfDates.push(moment(new Date(`${year}-${month + 1}-${i}`)).format('YYYY/MM/DD'));
    }
    // console.log(this.endAt.getDay());
    const user = JSON.parse(localStorage.getItem('user'));
    const objectRequest = {
      // startAt: moment(this.startAt).format('L'),
      // endAt: moment(this.endAt).format('L')
      doctorId: user.id,
      dates: this.listOfDates
    }
    console.log(objectRequest);
    this.medicalService.createVacation(objectRequest).subscribe(data => {
      console.log(data);
    })
  }

}
