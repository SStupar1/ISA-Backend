import { Component, OnInit } from '@angular/core';
import { ErService } from 'src/app/services/er.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-er-list',
  templateUrl: './er-list.component.html',
  styleUrls: ['./er-list.component.scss']
})
export class ErListComponent implements OnInit {

  public listOfData = [];
  private user: any;
  public isSuperAdmin = false;
  private form: FormGroup;

  constructor(private fb: FormBuilder, private erService: ErService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.setupUser();
    this.setupData();
    this.form = this.setupForm();
  }

  private setupUser(): void {
    this.isSuperAdmin = false;
    this.user = JSON.parse(localStorage.getItem('user'));
    if(this.user.adminType === 'SUPER_ADMIN') {
      this.isSuperAdmin = true;
    }
  }
  
  private setupData(): void {
    if(this.user.adminType === 'ADMIN') {
      this.erService.getErsByClinic(this.user.myClinic.id).subscribe(data => {
        this.listOfData = data;
        console.log(data)
      });
    } else {
      this.erService.getAllErs().subscribe(data => {
        this.listOfData = data;
        console.log(data)
      });
    }
  }

  onEdit(id): void {
    this.router.navigateByUrl(`/dashboard/er/${id}`);
  }

  onDelete(id): void {
    this.erService.deleteEr(id).subscribe(() => {
      this.setupData();
    })
  }

  public submitForm(): void {
    this.erService.getErsByClinicWithFilter(this.user.myClinic.id, this.form.value).subscribe(data => {
      this.listOfData = data;
    });
  }

   private setupForm(): FormGroup {
    return this.fb.group({
      name: [''],
      price: ['']
    })
  }

}
