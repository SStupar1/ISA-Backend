import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppointmentService } from 'src/app/services/appointment.service';
import { MedicalService } from 'src/app/services/medical.service';
import { ErService } from 'src/app/services/er.service';
import * as moment from 'moment';

@Component({
  selector: 'app-create-predef-appointment',
  templateUrl: './create-predef-appointment.component.html',
  styleUrls: ['./create-predef-appointment.component.scss']
})
export class CreatePredefAppointmentComponent implements OnInit {

  public selectedDoctor = null;
  public selectedEr = null;
  public listOfData = [];
  public listOfData2 = [];
  public time = null;
  public date = null; // new Date();
  public user: any;

  constructor(private router: Router, private appointmentService: AppointmentService, private medicalService: MedicalService, private erService: ErService) { }

  ngOnInit() {
    this.setupUser();
    this.setupData();
  }

  private setupData(): void {
    this.medicalService.getAllMedicalByClinic(this.user.myClinic.id).subscribe(data => {
      this.listOfData = data;
    });
    this.erService.getErsByClinic(this.user.myClinic.id).subscribe(data => {
      this.listOfData2 = data;
    });
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  onSearch(): void {
    const body = {
      erId: this.selectedEr,
      doctorId: this.selectedDoctor,
      date: moment(this.date).format('L'),
      startAt: moment(this.time).format('HH:mm:ss')
    }
    this.appointmentService.createPotentialAppointmentByAdmin(body).subscribe(() => {
      this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/medical`);
    });
  }

}
