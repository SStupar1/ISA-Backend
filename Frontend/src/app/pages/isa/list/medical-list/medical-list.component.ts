import { Component, OnInit } from '@angular/core';
import { MedicalService } from 'src/app/services/medical.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-medical-list',
  templateUrl: './medical-list.component.html',
  styleUrls: ['./medical-list.component.scss']
})
export class MedicalListComponent implements OnInit {

  public listOfData = [];
  private id;
  private user : any;
  isAdmin = false;
  private form: FormGroup;
  

  constructor(private fb: FormBuilder, private medicalService: MedicalService, private router: Router, private route: ActivatedRoute, private message: NzMessageService) { }

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
      this.medicalService.getAllMedical().subscribe(data => {
        this.listOfData = data;
        console.log(data)
      });
    } else {
      this.medicalService.getAllMedicalByClinic(this.id).subscribe(data => {
          this.listOfData = data;
      });
    }
  }

  onProfil(id) {
    this.router.navigateByUrl(`dashboard/medical-profile/${id}`);
  }

  private extractId(): void {
    this.id = this.route.snapshot.params.id;
  }

  onDelete(id): void {
    this.medicalService.deleteMedical(id).subscribe(() => {
      this.setupData();
    })
  }

  public submitForm(): void {
    this.medicalService.getA(this.user.myClinic.id, this.form.value).subscribe(data => {
      this.listOfData = data;
    });
  }

  avgGrade(id): void{
    this.medicalService.getDoctorsAvgGrade(id).subscribe(data => {
      this.message.info('Prosecna ocena je: ' + data.avgGrade);
    });
  }

   private setupForm(): FormGroup {
    return this.fb.group({
      firstName: [''],
      lastName: ['']
    })
  }
}
