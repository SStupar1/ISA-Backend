import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-isa',
  templateUrl: './isa.component.html',
  styleUrls: ['./isa.component.scss']
})
export class IsaComponent implements OnInit {

  public patientRole = environment.patientRole;
  public medicalStaffRole = environment.medicalStaffRole;
  public adminRole = environment.adminRole;

  private user: any;
  public isSuperAdmin: boolean;
  

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.setupUser();
  }

  public clearStorage(): void {
    localStorage.clear();
  }

  public checkRole(roles: string[]): boolean {
    return this.authService.showByRole(roles);
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
    this.isSuperAdmin = false;
    //console.log(this.user.myClinic.id);
    if(this.user.userType === 'ADMIN') {
      if(this.user.adminType === 'SUPER_ADMIN') {
        this.isSuperAdmin = true;
      }
    }
  }

  public onMedical(): void {
    this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/medical`);
  }

  public onPatients(): void {
    this.router.navigateByUrl(`dashboard/patients`);
  }

  public onUpdatePasswordMedical(): void {
    this.router.navigateByUrl(`dashboard/medical/${this.user.id}/update-password`);
  }

  public onUpdatePasswordAdmin(): void {
    this.router.navigateByUrl(`dashboard/admin/${this.user.id}/update-password`);
  }

  public onAddDoctor(): void {
    this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/add-doctor`);
  }

  public onAddNurse(): void {
    this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/add-nurse`);
  }

  public addEr(): void {
    this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/add-er`);
  }

  public onViewRequests(): void {
    this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/appointment-request`);
  }

  public onViewRequestsByPatient(): void {
    this.router.navigateByUrl(`dashboard/appointment-request/${this.user.id}/patient`);
  }
  public onLekoviDijagnoze() : void {
    this.router.navigateByUrl(`dashboard/medicine-and-diagnosi`);
  }
  public medicalRecord() {
    this.router.navigateByUrl(`dasboard/medical-record/${this.user.id}`);
  }
  public onRecipes() {
    this.router.navigateByUrl(`dashboard/pending-recipes`);
  }

  public onClick(): void {
    this.router.navigateByUrl(`dashboard/clinic/${this.user.myClinic.id}/ers`);
  }

  public onPatientsByMedical(): void {
    this.router.navigateByUrl(`dashboard/medical/${this.user.id}/patients`);
  }

}
