import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { AdminService } from 'src/app/services/admin.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-nurse',
  templateUrl: './create-nurse.component.html',
  styleUrls: ['./create-nurse.component.scss']
})
export class CreateNurseComponent implements OnInit {

  validateForm: FormGroup;

  private user: any;

  constructor(private fb: FormBuilder, private adminService: AdminService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.setupUser();
    this.validateForm = this.fb.group({
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required, Validators.minLength(4)]],
      rePassword: [null, [Validators.required, this.confirmationValidator]],
      firstName: [null, [Validators.required, Validators.minLength(4)]],
      lastName: [null, [Validators.required, Validators.minLength(4)]],
      phoneNumberPrefix: ['+381'],
      phone: [null, [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(8)]],
      address: [null, [Validators.required, Validators.minLength(4)]],
      city: [null, [Validators.required, Validators.minLength(4)]],
      country: [null, [Validators.required, Validators.minLength(4)]],
      ssn: [null, [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(13), Validators.maxLength(13)]],
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
      this.adminService.createNurse(this.validateForm.value, this.user.myClinic.id).subscribe(data => {
        this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/medical`);
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
