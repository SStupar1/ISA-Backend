import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ErService } from 'src/app/services/er.service';

@Component({
  selector: 'app-create-er',
  templateUrl: './create-er.component.html',
  styleUrls: ['./create-er.component.scss']
})
export class CreateErComponent implements OnInit {

  validateForm: FormGroup;

  private user: any;

  constructor(private fb: FormBuilder, private erService: ErService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.setupUser();
    this.validateForm = this.fb.group({
      number: [null, [Validators.required, Validators.pattern("^[0-9]*$")]],
      name: [null, [Validators.required, Validators.minLength(4)]]
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
      this.erService.createEr(this.user.myClinic.id, this.validateForm.value).subscribe(data => {
        
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
