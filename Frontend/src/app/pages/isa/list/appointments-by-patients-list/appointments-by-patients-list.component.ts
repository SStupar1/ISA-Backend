import { Component, OnInit } from '@angular/core';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-appointments-by-patients-list',
  templateUrl: './appointments-by-patients-list.component.html',
  styleUrls: ['./appointments-by-patients-list.component.scss']
})
export class AppointmentsByPatientsListComponent implements OnInit {

  listOfData = [];
  private user: any;

  constructor(private patientService: PatientService) { }

  ngOnInit() {
    this.setupUser();
    this.setupData();
  }

  public setupData(): void {
    this.patientService.getAllPatientsAppointments(this.user.id).subscribe(data => {
      this.listOfData = data;
      console.log(data)
    })
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }
}
