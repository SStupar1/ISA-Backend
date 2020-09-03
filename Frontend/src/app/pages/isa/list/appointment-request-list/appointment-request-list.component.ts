import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AppointmentRequestService } from 'src/app/services/appointment-request.service';
import * as moment from 'moment';

@Component({
  selector: 'app-appointment-request-list',
  templateUrl: './appointment-request-list.component.html',
  styleUrls: ['./appointment-request-list.component.scss']
})
export class AppointmentRequestListComponent implements OnInit {

  public listOfData = [];
  public user: any;
  public isPatient = false;

  constructor(private requestService: AppointmentRequestService , private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.setupUser();
    this.setupData();
  }

  private setupData(): void {
    if(!this.isPatient) {
      this.requestService.getAllRequestsByClinic(this.user.myClinic.id).subscribe(data => {
        this.listOfData = data;
        console.log(data);
      });
    } else {
      this.requestService.getAllRequestsByPatient(this.user.id).subscribe(data => {
        this.listOfData = data;
        console.log(data);
      })
    }
  }

  private setupUser(): void {
    this.isPatient = false;
    this.user = JSON.parse(localStorage.getItem('user'));
    if(this.user.userType == 'PATIENT') {
      this.isPatient = true;
    }
  }

  public onApproveRequest(id): void {
    const body = {
      patientId: this.user.id
    }
    this.requestService.approveRequestByPatientAndCreateAnAppointment(id, body).subscribe(data => {
      this.setupData();
    });
  }

  public onDenyRequest(id): void {

  }

  public formatDate(date) {
    return moment(date).format("L");
  }

  onAssign(id) {
    this.router.navigateByUrl(`dashboard/appointment-request/${id}`);
  }

}
