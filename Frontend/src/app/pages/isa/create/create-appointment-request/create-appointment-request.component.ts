import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { NzI18nService } from 'ng-zorro-antd';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as moment from 'moment';
import { AppointmentRequestService } from 'src/app/services/appointment-request.service';
import { Router } from '@angular/router';
import { routerNgProbeToken } from '@angular/router/src/router_module';
import { MedicalService } from 'src/app/services/medical.service';

@Component({
  selector: 'app-create-appointment-request',
  templateUrl: './create-appointment-request.component.html',
  styleUrls: ['./create-appointment-request.component.scss']
})
export class CreateAppointmentRequestComponent implements OnInit {

  public selectedType = null;
  public listOfData = [];
  public listOfData2 = [];
  public listOfData3 = [];
  public time = null;
  public date = null; // new Date();
  public dateRange = []; // [ new Date(), addDays(new Date(), 3) ];
  public isEnglish = false;
  public isVisible = false;
  public selectedDoctor = null;
  public id = null;
  public clinic = null;
  public doctorName = null;

  private form: FormGroup;


  constructor(private fb: FormBuilder, private medicalService: MedicalService, private router: Router, private appointmentService: AppointmentService, private i18n: NzI18nService, private aRS: AppointmentRequestService) { }

  ngOnInit() {
    this.setupData();
    this.form = this.setupForm();
  }

  private setupData(): void {
    this.isVisible = false;
    this.appointmentService.getAllAppointemntTypes().subscribe(data => {
      this.listOfData = data;
    });
  }
 
  onSearch() {
    const filterObject = {
      date: moment(this.date).format('L'),
      startAt: moment(this.time).format('HH:mm:ss'),
      appointmentTypeId: this.selectedType
    }
    this.appointmentService.getPotencialClinics(filterObject).subscribe(data => {
      this.listOfData2 = data;
    })
  }

  onChoose(object) {
    this.isVisible = true;
    this.id = object.id;
    this.listDoctors();
    this.clinic = object;
  }

  listDoctors() {
    const filterObject = {
      date: moment(this.date).format('YYYY/MM/DD'),
      startAt: moment(this.time).format('HH:mm:ss'),
      appointmentTypeId: this.selectedType,
      clinicId: this.id
    }
    this.appointmentService.getPotencialDoctors(filterObject).subscribe(data => {
      this.listOfData3 = data;
      console.log(data)
    })
  }

  onSchedule(object) {
     const body = {
      appointmentDate: moment(this.date).format('YYYY/MM/DD'),
      startAt: moment(this.time).format('HH:mm:ss'),
      appointmentType: this.listOfData.filter(appointmentType => appointmentType.id === this.selectedType)[0],
      doctorName: object.firstName,
      doctor: object,
      clinicId: this.id,
      clinic: this.clinic
    }
    localStorage.setItem('key', JSON.stringify(body));
    this.router.navigateByUrl(`dashboard/appointment-request-view`);
  }

  public submitForm(): void {
    const body = {
      clinicId: this.id
    }
    console.log(body.clinicId)
    this.medicalService.getAllMedicalWithFilter(body, this.form.value).subscribe(data => {
      this.listOfData3 = data;
      console.log(body)
    });
  }

   private setupForm(): FormGroup {
    return this.fb.group({
      firstName: [''],
      lastName: ['']
    })
  }
  
}
