import { Component, OnInit } from '@angular/core';
import { ErService } from 'src/app/services/er.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-er',
  templateUrl: './er.component.html',
  styleUrls: ['./er.component.scss']
})
export class ErComponent implements OnInit {

  validateForm: FormGroup;
  private user: any;

  constructor(private router: Router, private fb: FormBuilder, private route: ActivatedRoute, private erService: ErService) { }

  ngOnInit() {
    this.setupUser();
    this.setupForm();
    this.getDetails();
  }

  public setupForm(): void {
    this.validateForm = this.fb.group({
      number: [ {value:null, disabled: false}, [Validators.required, Validators.pattern("^[0-9]*$")]],
      name: [ {value:null, disabled: false}, [Validators.required, Validators.minLength(4)]]
    })
  }

  public getDetails(): void {
    this.erService.getEr(this.route.snapshot.params.id).subscribe(data =>{
      const formValues = {
        number: data.number,
        name: data.name
      }
      this.validateForm.setValue(formValues);
    })
  }

  edit(): void {
    this.erService.updateEr(this.route.snapshot.params.id, this.validateForm.value).subscribe(() => {
      
    })
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

}
