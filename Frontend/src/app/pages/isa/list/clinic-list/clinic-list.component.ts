import { Component, OnInit } from '@angular/core';
import { ClinicService } from 'src/app/services/clinic.service';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-clinic-list',
  templateUrl: './clinic-list.component.html',
  styleUrls: ['./clinic-list.component.scss']
})
export class ClinicListComponent implements OnInit {

  public listOfData = [];

  constructor(private clinicService: ClinicService, private router: Router, private message: NzMessageService) { }

  ngOnInit() {
    this.setupData();
  }

  private setupData(): void {
    this.clinicService.getAllClinics().subscribe(data => {
      this.listOfData = data;
    });
  }

   onPodaci(id) {
     this.router.navigateByUrl(`dashboard/clinic-profile/${id}`);
   }

   onDelete(id): void {
    this.clinicService.deleteClinic(id).subscribe(() => {
      this.setupData();
    })
  }

  avgGrade(id): void{
    this.clinicService.getClinicsAvgGrade(id).subscribe(data => {
      this.message.info('Prosecna ocena je: ' + data.avgGrade);
    });
  }
}
