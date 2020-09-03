import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import * as moment from 'moment';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-predef-appointments-list',
  templateUrl: './predef-appointments-list.component.html',
  styleUrls: ['./predef-appointments-list.component.scss']
})
export class PredefAppointmentsListComponent implements OnInit {

  listOfData = [];
  private user: any;
  
  

  constructor(private appointmentService: AppointmentService) { }

  ngOnInit() {
    this.setupUser();
    this.setupData();
  }

  private setupUser() {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  public setupData(): void {
    this.appointmentService.getAllPotentialAppointmentsByPatient().subscribe(data => {
      this.listOfData = data;
      console.log(data)
    })
  }

  onSchedule(id): void {
    const body = {
      patientId: this.user.id,
      potentialAppointmentId: id
    }
    this.appointmentService.schedulePotentialAppointment(body).subscribe(() => {
      this.setupData();
    })
  }

  formatDate(date): String {
    return moment(date).format("L");
  }

}
