import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-first-login',
  templateUrl: './first-login.component.html',
  styleUrls: ['./first-login.component.scss']
})
export class FirstLoginComponent implements OnInit {

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
    if(this.user.userType === 'MEDICAL') {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
      this.authService.setPasswordMedical(this.id, this.validateForm.value).subscribe(data => {
        const userRaw = localStorage.getItem('user');
        const user = JSON.parse(userRaw);
        //this.router.navigateByUrl(`dashboard/clinic/${user.myClinic.id}/patients`);
        this.router.navigateByUrl("auth/login");
      });
    } else if (this.user.userType === 'ADMIN') {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
      this.authService.setPasswordAdmin(this.id, this.validateForm.value).subscribe(data => {
        const userRaw = localStorage.getItem('user');
        const user = JSON.parse(userRaw);
        //this.router.navigateByUrl(`dashboard/clinic/${user.myClinic.id}/medical`);
        this.router.navigateByUrl("auth/login");
      });
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
