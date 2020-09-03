import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ClinicService } from 'src/app/services/clinic.service';

@Component({
  selector: 'app-my-clinics-list',
  templateUrl: './my-clinics-list.component.html',
  styleUrls: ['./my-clinics-list.component.scss']
})
export class MyClinicsListComponent implements OnInit {

  public listOfData = [];
  private user : any;
  public clinicId = null;
  isVisible = false;
  validateForm: FormGroup;

  constructor(private clinicService: ClinicService, private router: Router, private fb: FormBuilder) { }

  ngOnInit() {
    this.setupUser();
    this.setupData();
    this.validateForm = this.fb.group({
      grade: [null, [Validators.required, Validators.pattern("^[1-5]*$"), Validators.maxLength(1), Validators.minLength(1)]]
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    const body = {
      clinicId: this.clinicId,
      ...this.validateForm.value,
      patientId: this.user.id
    }

    this.clinicService.gradeAClinic(body).subscribe(() => {
      
    })
  }


  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }
  
  private setupData(): void {
    this.clinicService.getMyClinics(this.user.id).subscribe(data => {
        this.listOfData = data;
    });
  }

  grade(id): void {
    this.clinicId = id;
    this.isVisible = true;
    // const body = {
    //   doctorId: id,
    //   grade:
    // }
    // this.medicalService.gradeMyDoctor(body).subscribe(() => {

    // })
  }

}
