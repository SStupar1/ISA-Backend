import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalRecordService } from '../medical-record.service';
import { MedicalExaminationService } from '../medical-examination.service';

@Component({
  selector: 'app-medical-record',
  templateUrl: './medical-record.component.html',
  styleUrls: ['./medical-record.component.scss']
})
export class MedicalRecordComponent implements OnInit {

  id: any;
  medicalRecordId: any;
  firstName: string;
  lastName: string;
  jmbg: string;
  height: number;
  weight: number;
  alergies: string;
  bloodType: string;
  diopter: number;
  disabledp = true;
  medicalExaminations: any;
  helper: any;

  constructor(private activatedRoute: ActivatedRoute,
    private router: Router,
    private medicalRecordService: MedicalRecordService,
    private medicalExaminationService: MedicalExaminationService) { }

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.url[2].path;
    this.getUser();
    this.getMedicalExaminations();
  }
  getUser() {
    console.log(this.id)
    this.medicalRecordService.getMedicalRecordInfo(this.id)
      .subscribe(
        (data) => {
          console.log(data);
          this.firstName = data.firstName;
          this.lastName = data.lastName;
          this.jmbg = data.jmbg;
          this.height = data.height;
          this.weight = data.weight;
          this.bloodType = data.bloodType;
          this.diopter = data.diopter;
          this.alergies = data.alergies;
          this.medicalRecordId = data.id;
        }
      )
  }
  getMedicalExaminations() {
    this.medicalExaminationService.getAllForOnePatient(this.id).subscribe(
      (data) => {
        this.medicalExaminations = data;
      }
    )
  }

  izmeni() {
    this.disabledp = false;
  }

  sacuvaj() {
    this.medicalRecordService.updateMedicalRecord(this.medicalRecordId, this.height, this.weight, this.bloodType, this.diopter, this.alergies).subscribe(
      (data) => {
        alert(`Zdrastveni karton je uspesno azuriran!`);
      }
    )
  }


}
