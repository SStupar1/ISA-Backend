import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalService } from 'src/app/services/medical.service';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-medical',
  templateUrl: './medical.component.html',
  styleUrls: ['./medical.component.scss']
})
export class MedicalComponent implements OnInit {

  validateForm: FormGroup

  @Input()
  public isReadOnly: boolean = true;

  private id: string;
  private user: any;

  constructor(private route: ActivatedRoute, private medicalService: MedicalService, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.setupUser();
    this.setupForm();
    this.extractId();
    this.getDetails();
    
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  public setupForm(): void {
    if(this.user.userType == 'MEDICAL')
    {
      this.isReadOnly = false;
    }
    this.validateForm = this.fb.group({
      email: [ {value: null, disabled: true }, [Validators.email, Validators.required]],
      firstName: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]],
      lastName: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]],
      phone: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]],
      address: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(8), Validators.pattern("^[0-9]*$")]],
      city: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]],
      country: [ {value: null, disabled: this.isReadOnly }, [Validators.required, Validators.minLength(4)]],
      ssn: [ {value: null, disabled: true }, [Validators.required, Validators.minLength(13), Validators.maxLength(13), Validators.pattern("^[0-9]*$")]],
    });
  }

  private extractId(): void {
    if(this.user.userType == 'MEDICAL')
    {
      this.isReadOnly = false;
    }
    if(this.isReadOnly)
    {
      this.id = this.route.snapshot.params.id;
    
    } else {
      if(this.user.userType == 'ADMIN') {
        this.id = this.route.snapshot.params.id;
        this.isReadOnly = true;
        return;
      }
      this.id = this.user.id;
    }
  }

  public getDetails(): void {
    this.medicalService.getMedicalProfileById(this.id).subscribe(data =>{
      const formValues = {
        email: data.email,
        firstName: data.firstName,
        lastName: data.lastName,
        ssn: data.ssn,
        address: data.address,
        city: data.city,
        country: data.country,
        phone: data.phone
      }
      this.validateForm.setValue(formValues);
    })
  }

  public update(): void {
    this.medicalService.updateMedical(this.id, this.validateForm.value).subscribe(data => {
      if(this.user.userType == 'MEDICAL')
      {
        this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/patients`);  
      } else 
      {
        this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/medical`);
      }
    })
  }
}
