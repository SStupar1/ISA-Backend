import { Component, OnInit } from '@angular/core';
import { MedicalService } from 'src/app/services/medical.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-my-doctors-list',
  templateUrl: './my-doctors-list.component.html',
  styleUrls: ['./my-doctors-list.component.scss']
})
export class MyDoctorsListComponent implements OnInit {

  public listOfData = [];
  private user : any;
  public doctorId = null;
  isVisible = false;
  validateForm: FormGroup;

  constructor(private medicalService: MedicalService, private router: Router, private fb: FormBuilder, private message: NzMessageService) { }

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
      doctorId: this.doctorId,
      ...this.validateForm.value,
      patientId: this.user.id
    }

    this.medicalService.gradeMyDoctor(body).subscribe(() => {
      this.message.info("Uspesno ste ocenili doktora.");
    },
    error => {
      this.message.info("Vec ste ocenili ovog doktora.");
    });
  }


  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }
  
  private setupData(): void {
    this.medicalService.getMyDoctors(this.user.id).subscribe(data => {
        this.listOfData = data;
    });
  }

  grade(id): void {
    this.isVisible = true;
    this.doctorId = id;
  }

}
