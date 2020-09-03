import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentRequestService } from 'src/app/services/appointment-request.service';
import * as moment from "moment";
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-create-appointment',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.scss']
})
export class CreateAppointmentComponent implements OnInit {
  validateForm: any;
  user: any;
  id: any;
  listOfData = [];
  showSuggestion = false;

  constructor(private router: Router, private adminService: AdminService, private fb: FormBuilder, private route: ActivatedRoute, private appointmentRequestService: AppointmentRequestService) { }

  ngOnInit() {
    this.setupUser();
    this.setupForm();
    this.extractId();
    this.getDetails();
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  public setupForm(): void {
    
    this.validateForm = this.fb.group({
      appointmentDate: [ {value: null, disabled: true }],
      localTime: [ {value: null, disabled: true }],
      appointmentTypeName: [ {value: null, disabled: true }],
      clinicName: [ {value: null, disabled: true }],
    });
  }

  private extractId(): void {
    this.id = this.route.snapshot.params.id;
  }

  public getDetails(): void {
    this.appointmentRequestService.getRequest(this.id).subscribe(data =>{
      const formValues = {
        appointmentDate: moment(data.appointmentDate).format('L'),
        localTime: data.localTime,
        appointmentTypeName: data.appointmentTypeName,
        clinicName: data.clinicName
      }
      
      this.validateForm.setValue(formValues);
      const filterEr = {
        date: moment(data.appointmentDate).format('YYYY/MM/DD'),
        clinicId: data.clinicId,
        startAt: data.localTime.toString().replace(/,/g, ':')
      }
      console.log(filterEr)
      this.appointmentRequestService.getErs(filterEr).subscribe(data => {
        this.listOfData = data;
        if (this.listOfData.length === 0) {
          this.showSuggestion = true;
        }
      })
    })
  }

  onChoose(id): void {
    const body = {
      erId: id
    }
    this.adminService.sendThatRequestIsConfirmed(this.route.snapshot.params.id, {erId: id}).subscribe(() => {
      this.router.navigateByUrl(`/dashboard/clinic/${this.user.myClinic.id}/medical`);
    })
  }

  useAuto() {
    const body = {
      id: this.id
    }
    this.adminService.autoSchedule(body).subscribe(() => {
      this.router.navigateByUrl(`/dashboard/clinic/${this.user.myClinic.id}/medical`);
    })
  }

  cancel(): void {
    this.adminService.sendThatRequestIsDenied(this.route.snapshot.params.id).subscribe(() => {
      this.router.navigateByUrl(`/dashboard/clinic/${this.user.myClinic.id}/medical`);
    });
  }
}