import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AppointmentService } from 'src/app/services/appointment.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AppointmentTypeService } from 'src/app/services/appointment-type.service';

@Component({
  selector: 'app-appointmenttype-list',
  templateUrl: './appointmenttype-list.component.html',
  styleUrls: ['./appointmenttype-list.component.scss']
})
export class AppointmenttypeListComponent implements OnInit {

  public listOfData = [];
  private form: FormGroup;

  constructor(private fb: FormBuilder, private appointmentTypeService: AppointmentTypeService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.setupData();
    this.form = this.setupForm();
  }
  
  private setupData(): void {
    this.appointmentTypeService.getAllAppointmentType().subscribe(data => {
      this.listOfData = data;
      console.log(data)
    });
  }

  onEdit(id): void {
    this.router.navigateByUrl(`/dashboard/appointment-type/${id}`);
  }

  onDelete(id): void {
    this.appointmentTypeService.deleteAppointmentType(id).subscribe(() => {
      this.setupData();
    })
  }

  public submitForm(): void {
    this.appointmentTypeService.getAllAppointmentTypeWithFilter(this.form.value).subscribe(data => {
      this.listOfData = data;
    });
  }


   private setupForm(): FormGroup {
    return this.fb.group({
      name: ['']
    })
  }
}
