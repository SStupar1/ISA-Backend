import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AppointmentTypeService } from 'src/app/services/appointment-type.service';

@Component({
  selector: 'app-create-appointmenttype',
  templateUrl: './create-appointmenttype.component.html',
  styleUrls: ['./create-appointmenttype.component.scss']
})
export class CreateAppointmenttypeComponent implements OnInit {

  validateForm: FormGroup;

  private user: any;

  constructor(private fb: FormBuilder, private appointmentTypeService: AppointmentTypeService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.setupUser();
    this.validateForm = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(2)]],
      price: [null, [Validators.required, Validators.pattern("^[0-9]*$")]]
    });
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  } 

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    {
      this.appointmentTypeService.createAppointmentType(this.validateForm.value).subscribe(data => {
        this.router.navigateByUrl(`dashboard/appointment-types`);
      }) 
    }
  }

  updateConfirmValidator(): void {
    /** wait for refresh value */
    Promise.resolve().then(() => this.validateForm.controls.rePassword.updateValueAndValidity());
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.validateForm.controls.password.value) {
      return { confirm: true, error: true };
    }
    return {};
  };

  getCaptcha(e: MouseEvent): void {
    e.preventDefault();
  }

}
