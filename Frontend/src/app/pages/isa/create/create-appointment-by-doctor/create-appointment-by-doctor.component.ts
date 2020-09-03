import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { AppointmentService } from 'src/app/services/appointment.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import * as moment from "moment";

@Component({
  selector: 'app-create-appointment-by-doctor',
  templateUrl: './create-appointment-by-doctor.component.html',
  styleUrls: ['./create-appointment-by-doctor.component.scss']
})
export class CreateAppointmentByDoctorComponent implements OnInit {

public time: Date | null = null;
public date: Date = null;
public user: any;
public validateForm: FormGroup;

  constructor(private router: Router, private appointmentService: AppointmentService, private fb: FormBuilder) { }

  ngOnInit() {
    this.setupUser();
    this.setupForm();
  }

  public setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  private setupForm(): void {
    this.validateForm = this.fb.group({
      email: [null, [Validators.required]]
    })
  }

  public onSchedule(): void {
    const email = this.validateForm.value.email;
    const body = {
      date: moment(this.date).format('L'),
      startAt: moment(this.time).format('HH:mm:ss'),
      patientEmail: email,
      doctorId: this.user.id,
      currentTime: moment().format('HH:mm:ss')
    }
    console.log(body)
    this.appointmentService.createAppointmentRequestAsDoctor(body).subscribe(() => {

    })
  }

}
