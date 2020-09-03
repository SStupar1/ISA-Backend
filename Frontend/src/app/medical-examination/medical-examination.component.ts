import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MedicineService } from '../medicine.service';
import { DiagnosisService } from '../diagnosis.service';
import { MedicalExaminationService } from '../medical-examination.service';
import { AppointmentTypeService } from '../services/appointment-type.service';

@Component({
  selector: 'app-medical-examination',
  templateUrl: './medical-examination.component.html',
  styleUrls: ['./medical-examination.component.scss']
})
export class MedicalExaminationComponent implements OnInit {

  patientId: any;
  medicines: object;
  diagnosises: object;
  medicine: any;
  diagnosis: any;
  helper: any;
  user: any;
  description: any;
  appointmentTypes = [];
  model: any;
  selectedType: any;

  constructor(private router: Router,
    private medicineService: MedicineService,
    private diagnosisService: DiagnosisService,
    private medicalExaminationService: MedicalExaminationService,
    private appTypeService: AppointmentTypeService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.patientId = this.activatedRoute.snapshot.url[2].path;
    this.user = JSON.parse(localStorage.getItem('user'));
    this.init();
    this.getTypes();
  }

  getTypes() {
    this.appTypeService.getAllAppointmentType().subscribe(
      (data: any) => {
        this.appointmentTypes = data;
      }, (error) => alert(error)
    );
  }

  init() {
    this.medicineService.getAll().subscribe(
      (data: any) => {
        this.medicines = data;
      }, (error) => alert(error)
    );
    this.diagnosisService.getAll().subscribe(
      (data: any) => {
        this.diagnosises = data;
      }, (error) => alert(error)
    );
  }
  initialized() {
    if (this.medicines != undefined && this.diagnosises != undefined) {
      return true;
    }
    else
      return false;
  }
  changeSelectedMedicine(filterVal: any) {
    this.medicine = filterVal;
  }

  changeSelectedDiagnosis(filterVal: any) {
    this.diagnosis = filterVal;
  }

  promeni() {
    this.medicalExaminationService.newExamination(this.patientId, this.user.id, this.description, this.medicine, this.diagnosis).subscribe(
      (data: any) => {
        console.log(data)
      }, (error) => {
        alert(error.text);
      }
    )
  }
}
