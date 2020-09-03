import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.scss']
})
export class UpdatePasswordComponent implements OnInit {

  validateForm: FormGroup;

  private id: string;

  private user: any;

  
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.setupUser();
    this.extractId();
    this.setupForm();
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  submitForm(): void {
    if(this.user.userType === 'ADMIN') {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
      this.authService.updatePasswordAdmin(this.id, this.validateForm.value).subscribe(data => {
        const userRaw = localStorage.getItem('user');
        const user = JSON.parse(userRaw);
        const clinicId = user.myClinic.id;
        this.router.navigateByUrl(`dashboard/clinic/${clinicId}/medical`);
      })
    } else if(this.user.userType === 'MEDICAL') {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
      this.authService.updatePasswordMedical(this.id, this.validateForm.value).subscribe(data => {
        const userRaw = localStorage.getItem('user');
        const user = JSON.parse(userRaw);
        const clinicId = user.myClinic.id;
        this.router.navigateByUrl(`dashboard/clinic/${clinicId}/patients`);
      })
    }
  }

   private extractId(): void {
    this.id = this.route.snapshot.params.id;
  }

  private setupForm(): void {
    this.validateForm = this.fb.group({
      password: [null, [Validators.required, Validators.minLength(4)]],
      rePassword: [null, [Validators.required]]
    })
  }

}
