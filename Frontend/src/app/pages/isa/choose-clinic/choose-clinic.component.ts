import { Component, OnInit } from '@angular/core';
import { ClinicService } from 'src/app/services/clinic.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-choose-clinic',
  templateUrl: './choose-clinic.component.html',
  styleUrls: ['./choose-clinic.component.scss']
})
export class ChooseClinicComponent implements OnInit {

  public listOfData = [];
  private form: FormGroup;

  constructor(private fb: FormBuilder, private clinicService: ClinicService, private router: Router, private message: NzMessageService) { }

  ngOnInit() {
    this.setupData();
    this.form = this.setupForm();
  }

  private setupData(): void {
    this.clinicService.getAllClinics().subscribe(data => {
      this.listOfData = data;
    });
  }

  public submitForm(): void {
    this.clinicService.getAllClinicsFilter(this.form.value).subscribe(data => {
      this.listOfData = data;
    });
  }

   onPodaci(id) {
     this.router.navigateByUrl(`dashboard/clinic-profile/${id}`);
   }

   avgGrade(id): void{
    this.clinicService.getClinicsAvgGrade(id).subscribe(data => {
      this.message.info('Prosecna ocena je: ' + data.avgGrade);
    });
  }

   private setupForm(): FormGroup {
    return this.fb.group({
      name: [''],
      address: ['']
    })
  }
}
