import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  validateForm: FormGroup;

  
  constructor(private fb: FormBuilder, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      email: [null, [Validators.required, Validators.minLength(3)]],
      password: [null, [Validators.required]]
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    this.authService.login(this.validateForm.value).subscribe(data => {
      const user = data.user;
      const token = data.token;
      console.log(token)
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      if(user.userType === 'MEDICAL') {
        if(user.setNewPassword) {
          //strana za promeni lozinke
          const id = user.id;
          //this.router.navigateByUrl('auth/first-login/' + id)
          this.router.navigateByUrl(`auth/medical/${id}/first-login`);
        } else {
          const clinicId = user.myClinic.id;
          this.router.navigateByUrl(`dashboard/clinic/${clinicId}/patients`);
        }
      } else if(user.userType === 'PATIENT') {
        this.router.navigateByUrl(`dashboard/choose-clinic`);
      } else if(user.userType === 'ADMIN') {
        if(user.setNewPassword) {
          //strana za promeni lozinke
          const id = user.id;
          //this.router.navigateByUrl('auth/first-login/' + id)
          this.router.navigateByUrl(`auth/admin/${id}/first-login`);
        } else {
          const adminType = user.adminType;
          if(adminType === 'SUPER_ADMIN') {
            this.router.navigateByUrl(`dashboard/registration-request`);
          } else if(adminType === 'ADMIN') {
            const clinicId = user.myClinic.id;
            this.router.navigateByUrl(`dashboard/clinic/${clinicId}/medical`);
          }
        }
      }
      
    },
    error => {
      const message = error.error.message;
      console.log(message)
    })
  }

  onRegistartion(): void {
    this.router.navigateByUrl('auth/registartion');
  }

}
