import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ClinicService } from 'src/app/services/clinic.service';

@Component({
  selector: 'app-create-clinic',
  templateUrl: './create-clinic.component.html',
  styleUrls: ['./create-clinic.component.scss']
})
export class CreateClinicComponent implements OnInit {

  validateForm: FormGroup;

  private user: any;
  public listOfData = [];

  constructor(private fb: FormBuilder, private clinicService: ClinicService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.setupUser();
    this.validateForm = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(4)]],
      address: [null, [Validators.required, Validators.minLength(4)]],
      description: [null, [Validators.required, Validators.minLength(4)]]
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
      this.clinicService.createClinic(this.validateForm.value).subscribe(data => {
        this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/medical`);
        console.log(this.validateForm.value);
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
