import { Component, OnInit } from '@angular/core';
import { PatientService } from 'src/app/services/patient.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {

  public listOfData = [];
  private id;
  private user: any;
  private form: FormGroup;
  public isAdmin = false;

  constructor(private patientService: PatientService, private router: Router, private route: ActivatedRoute, private fb: FormBuilder) { }

  ngOnInit() {
    this.setupUser();
    this.extractId();
    this.setupData();
    this.form = this.setupForm();
  }

  private setupUser(): void {
    this.isAdmin = false;
    this.user = JSON.parse(localStorage.getItem('user'));
    if(this.user.userType === 'ADMIN') {
      this.isAdmin = true;
    }
  } 

  private setupData(): void {
    if(this.user.userType === 'ADMIN' && this.user.adminType === 'SUPER_ADMIN') {
      this.patientService.getAllPatients().subscribe(data => {
        this.listOfData = data;
      });
    } else {
      // this.patientService.getAllPatientsByClinic(this.id).subscribe(data => {
      //   this.listOfData = data;
      // });
      this.patientService.getAllPatientsByMedicalaAppointments(this.user.id).subscribe(data => {
        this.listOfData = data;
      });
    }
  }

  onProfil(id) {
    this.router.navigateByUrl(`dashboard/patient-profile/${id}`);
  }

  private extractId(): void {
    this.id = this.route.snapshot.params.id;
  }

  private setupForm(): FormGroup {
    return this.fb.group({
      firstName: [''],
      lastName: [''],
      ssn: ['']
    })
  }

  public submitForm(): void {
    if(this.user.userType === 'ADMIN' && this.user.adminType === 'SUPER_ADMIN') {
      this.patientService.getAllPatients(this.form.value).subscribe(data => {
        this.listOfData = data;
      });
    } else {
      this.patientService.getAllPatientsByMedicalaAppointments(this.user.id).subscribe(data => {
        this.listOfData = data;
      });
    }
  }

  onDelete(id): void {
    this.patientService.deletePatient(id).subscribe(() => {
      this.setupData();
    })
  }
  onMedicalRecord(id): void {
    this.router.navigate([`dasboard/medical-record/${id}`]);
  }
  onStartExamination(id) : void {
    this.router.navigateByUrl(`dashboard/medical-examination/${id}`);
  }
  
}
