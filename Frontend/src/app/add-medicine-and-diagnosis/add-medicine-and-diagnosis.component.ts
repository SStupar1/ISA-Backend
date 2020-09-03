import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MedicineService } from '../medicine.service';
import { DiagnosisService } from '../diagnosis.service';

@Component({
  selector: 'app-add-medicine-and-diagnosis',
  templateUrl: './add-medicine-and-diagnosis.component.html',
  styleUrls: ['./add-medicine-and-diagnosis.component.scss']
})
export class AddMedicineAndDiagnosisComponent implements OnInit {

  medicine: string;
  diagnosis: string;
  helper: any;

  constructor(private router: Router,
    private medicneService: MedicineService,
    private diagnosisService: DiagnosisService) { }

  ngOnInit() {
    
  }
  addMedicine() {
    if (this.medicine.trim() != '') {
      this.medicneService.addMedicine(this.medicine).subscribe(
        (medicine: any) => {
          alert('Lek dodat');
        }, (error) => alert(error.text)
      );
    } else {
      alert('Morate uneti naziv leka');
    }
  }
  addDiagnosis() {
    if (this.diagnosis.trim() != '') {
      this.diagnosisService.addDiagnosis(this.diagnosis).subscribe(
        (diagnosis: any) => {
          alert('Dijagnoza dodata');
        }, (error) => alert(error.text)
      );
    } else {
      alert('Morate uneti naziv dijagnoze');
    }
  }

}
