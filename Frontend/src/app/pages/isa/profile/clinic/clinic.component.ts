import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClinicService } from 'src/app/services/clinic.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-clinic',
  templateUrl: './clinic.component.html',
  styleUrls: ['./clinic.component.scss']
})
export class ClinicComponent implements OnInit {
  
  validateForm: FormGroup

  @Input()
  public isReadOnly: boolean = true;

  private id: string;
  private user: any;
  private isSuperAdmin: any;

  constructor(private route: ActivatedRoute, private router: Router, private clinicService: ClinicService, private fb: FormBuilder, private authService: AuthService) { }

  ngOnInit(): void {
    this.setupUser();
    this.setupForm();
    this.extractId();
    this.getDetails();
    
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
    if(this.user.userType === 'ADMIN') {
      if(this.user.adminType === 'SUPER_ADMIN') {
        this.isReadOnly = false;
        this.isSuperAdmin = true;
      }
    }
  }

  public setupForm(): void {
    this.validateForm = this.fb.group({
      name: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]],
      address: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]],
      description: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]]
    });
  }

  private extractId(): void {
    if(this.isReadOnly)
    {
      this.id = this.route.snapshot.params.id;
    
    } else {
      if(this.isSuperAdmin) {
        this.id = this.route.snapshot.params.id;
        return;
      }
      this.id = this.user.id;
    }
  }

  public getDetails(): void {

    if(this.user.adminType === 'ADMIN') {
      this.clinicService.getClinicProfileById(this.user.myClinic.id).subscribe(data =>{
        const formValues = {
          name: data.name,
          address: data.address,
          description: data.description
        }
        this.validateForm.setValue(formValues);
      })
    } else {
      this.clinicService.getClinicProfileById(this.route.snapshot.params.id).subscribe(data =>{
        const formValues = {
          name: data.name,
          address: data.address,
          description: data.description
        }
        this.validateForm.setValue(formValues);
      })
    }
  }

  public update(): void {

    if(this.user.adminType === 'ADMIN') {
      this.clinicService.updateClinic(this.user.myClinic.id, this.validateForm.value).subscribe(data => {
        this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/medical`);
      })
    }  else {    
      this.clinicService.updateClinic(this.route.snapshot.params.id, this.validateForm.value).subscribe(data => {
        this.router.navigateByUrl(`dashboard/registration-request`);
        })  
    }
   
  }

  public checkRole(roles: string[]): boolean {
    return this.authService.showByRole(roles);
  }

}
