import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentRequestService } from 'src/app/services/appointment-request.service';
import * as moment from 'moment';

@Component({
  selector: 'app-appointment-request-view',
  templateUrl: './appointment-request-view.component.html',
  styleUrls: ['./appointment-request-view.component.scss']
})
export class AppointmentRequestViewComponent implements OnInit {
  
  validateForm: FormGroup;
  user: any;
  data: any;
  formValues: any;

  constructor(private router: Router, private fb: FormBuilder, private route: ActivatedRoute, private appointmentRequestService: AppointmentRequestService) { }

  ngOnInit() {
    this.setupForm();
    this.setupUser();
    this.setupData();
  }

  public setupForm(): void {
    
    this.validateForm = this.fb.group({
      appointmentDate: [ {value: null, disabled: true }],
      localTime: [ {value: null, disabled: true }],
      appointmentTypeName: [ {value: null, disabled: true }],
      clinicName: [ {value: null, disabled: true }],
      doctorName: [ {value: null, disabled: true }]
    });
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  setupData(): void {
    this.data = JSON.parse(localStorage.getItem('key'));
   ;
     this.formValues = {
      appointmentDate: this.data.appointmentDate,
      localTime: this.data.startAt,
      appointmentTypeName: this.data.appointmentType.name,
      clinicName: this.data.clinic.name,
      doctorName: this.data.doctorName
    }
    console.log(this.formValues)

    this.validateForm.setValue(this.formValues)
  }

  confirm(): void{
    const body = {
      appointmentDate: this.data.appointmentDate,
      appointmentTypeId: this.data.appointmentType.id,
      clinicId: this.data.clinic.id,
      doctorId: this.data.doctor.id,
      startAt: this.data.startAt,
      patientId: this.user.id
    }
    console.log(body)
    this.appointmentRequestService.createRequest(body).subscribe(() => {
      this.router.navigateByUrl('dashboard/create-appointment-request');
    })
  }

  cancel() {
    localStorage.removeItem('key');
    this.router.navigateByUrl('dashboard/create-appointment-request');
  }

}
