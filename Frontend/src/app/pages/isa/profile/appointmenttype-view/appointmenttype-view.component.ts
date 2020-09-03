import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AppointmentTypeService } from 'src/app/services/appointment-type.service';

@Component({
  selector: 'app-appointmenttype-view',
  templateUrl: './appointmenttype-view.component.html',
  styleUrls: ['./appointmenttype-view.component.scss']
})
export class AppointmenttypeViewComponent implements OnInit {

  validateForm: FormGroup;

  constructor(private router: Router, private fb: FormBuilder, private route: ActivatedRoute, private appointmentTypeService: AppointmentTypeService) { }

  ngOnInit() {
    this.setupForm();
    this.getDetails();
  }

  public setupForm(): void {
    this.validateForm = this.fb.group({
      name: [ {value:null, disabled: false}, [Validators.required, Validators.minLength(4)]],
      price: [ {value:null, disabled: false}, [Validators.required, Validators.minLength(4)]]
    })
  }

  public getDetails(): void {
    this.appointmentTypeService.getAppointmentType(this.route.snapshot.params.id).subscribe(data =>{
      const formValues = {
        name: data.name,
        price: data.price
      }
      this.validateForm.setValue(formValues);
    })
  }

  edit(): void {
    this.appointmentTypeService.editAppointmentType(this.route.snapshot.params.id, this.validateForm.value).subscribe(() => {
      this.router.navigateByUrl(`dashboard/appointment-types`);
    })
  }

}
