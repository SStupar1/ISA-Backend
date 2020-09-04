import { Component, OnInit } from '@angular/core';
import { MedicalService } from 'src/app/services/medical.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

  listOfData = [];
  private user: any;

  constructor(private medicalService: MedicalService) { }

  ngOnInit() {
    this.setupUser();
    this.setupData();
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  private setupData(): void{
    this.medicalService.getWorkCalendar(this.user.id).subscribe(data => {
      console.log(data);
      this.listOfData = data;
    })
  }
}
