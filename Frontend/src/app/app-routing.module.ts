import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './pages/auth/auth.component';
import { IsaComponent } from './pages/isa/isa.component';
import { AdminComponent } from './pages/isa/profile/admin/admin.component';
import { PatientComponent } from './pages/isa/profile/patient/patient.component';
import { MedicalComponent } from './pages/isa/profile/medical/medical.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegistrationComponent } from './pages/auth/registration/registration.component';
import { FirstLoginComponent } from './pages/auth/first-login/first-login.component';
import { MedicalListComponent } from './pages/isa/list/medical-list/medical-list.component';
import { MyProfileComponent } from './pages/isa/profile/my-profile/my-profile.component';
import { ClinicListComponent } from './pages/isa/list/clinic-list/clinic-list.component';
import { ClinicComponent } from './pages/isa/profile/clinic/clinic.component';
import { PatientListComponent } from './pages/isa/list/patient-list/patient-list.component';
import { AdminListComponent } from './pages/isa/list/admin-list/admin-list.component';
import { ChooseClinicComponent } from './pages/isa/choose-clinic/choose-clinic.component';
import { RequestListComponent } from './pages/isa/list/request-list/request-list.component';
import { AppointmentRequestListComponent } from './pages/isa/list/appointment-request-list/appointment-request-list.component';
import { MyClinicProfileComponent } from './pages/isa/profile/my-clinic-profile/my-clinic-profile.component';
import { UpdatePasswordComponent } from './pages/auth/update-password/update-password.component';
import { CreateAppointmentRequestComponent } from './pages/isa/create/create-appointment-request/create-appointment-request.component';
import { CreateDoctorComponent } from './pages/isa/create/create-doctor/create-doctor.component';
import { CreateNurseComponent } from './pages/isa/create/create-nurse/create-nurse.component';
import { CreateAppointmentComponent } from './pages/isa/create/create-appointment/create-appointment.component';
import { AppointmentRequestViewComponent } from './pages/isa/profile/appointment-request-view/appointment-request-view.component';
import { AppointmenttypeListComponent } from './pages/isa/list/appointmenttype-list/appointmenttype-list.component';
import { AppointmenttypeViewComponent } from './pages/isa/profile/appointmenttype-view/appointmenttype-view.component';
import { CreateAppointmenttypeComponent } from './pages/isa/create/create-appointmenttype/create-appointmenttype.component';
import { ErListComponent } from './pages/isa/list/er-list/er-list.component';
import { ErComponent } from './pages/isa/profile/er/er.component';
import { CreateErComponent } from './pages/isa/create/create-er/create-er.component';
import { CreateAdminComponent } from './pages/isa/create/create-admin/create-admin.component';
import { CreateClinicComponent } from './pages/isa/create/create-clinic/create-clinic.component';
import { PriceListComponent } from './pages/isa/list/price-list/price-list.component';
import { AddMedicineAndDiagnosisComponent } from './add-medicine-and-diagnosis/add-medicine-and-diagnosis.component';
import { MedicalRecordComponent } from './medical-record/medical-record.component';
import { MedicalExaminationComponent } from './medical-examination/medical-examination.component';
import { PendingRecipesComponent } from './pending-recipes/pending-recipes.component';
import { CreateAppointmentByDoctorComponent } from './pages/isa/create/create-appointment-by-doctor/create-appointment-by-doctor.component';
import { CreateVacationComponent } from './pages/isa/create/create-vacation/create-vacation.component';
import { CreatePredefAppointmentComponent } from './pages/isa/create/create-predef-appointment/create-predef-appointment.component';
import { PredefAppointmentsListComponent } from './pages/isa/list/predef-appointments-list/predef-appointments-list.component';
import { AppointmentsByPatientsListComponent } from './pages/isa/list/appointments-by-patients-list/appointments-by-patients-list.component';
import { MyDoctorsListComponent } from './pages/isa/list/my-doctors-list/my-doctors-list.component';
import { MyClinicsListComponent } from './pages/isa/list/my-clinics-list/my-clinics-list.component';

const routes: Routes = [
  { path: 'dashboard/medicine-and-diagnosi',pathMatch: 'full',component : AddMedicineAndDiagnosisComponent},
  { path : 'dasboard/medical-record/:id',component : MedicalRecordComponent},
  { path: '', pathMatch: 'full', redirectTo: '/auth/login' },
  { path: 'dashboard', pathMatch: 'full', redirectTo: '/dashboard/clinics' },
  { path: 'dashboard/medical-examination/:id',component : MedicalExaminationComponent},
  { path: 'dashboard/pending-recipes',component : PendingRecipesComponent},
  {
    path: 'auth', component: AuthComponent, children: [
      { path: 'login', component: LoginComponent },
      { path: 'registartion', component: RegistrationComponent },
      { path: 'medical/:id/first-login', component: FirstLoginComponent },
      { path: 'admin/:id/first-login', component: FirstLoginComponent}
    ]
  },
  {
    path: 'dashboard', component: IsaComponent, children: [
      { path: 'admin-profile', component: AdminComponent},
      { path: 'patient-profile/:id', component: PatientComponent},
      { path: 'medical-profile/:id', component: MedicalComponent},
      { path: 'admin-profile/:id', component: AdminComponent}, 
      { path: 'clinic/:id/medical', component: MedicalListComponent},
      { path: 'my-profile', component: MyProfileComponent},
      { path: 'clinics', component: ClinicListComponent},
      { path: 'clinic-profile/:id', component: ClinicComponent},
      { path: 'clinic/:id/patients', component: PatientListComponent},
      { path: 'admins', component: AdminListComponent},
      { path: 'choose-clinic', component: ChooseClinicComponent},
      { path: 'registration-request', component: RequestListComponent},
      { path: 'clinic/:id/appointment-request', component: AppointmentRequestListComponent},
      { path: 'appointment-request/:id/patient', component: AppointmentRequestListComponent},
      { path: 'my-clinic-profile', component: MyClinicProfileComponent},
      { path: 'medical/:id/update-password', component: UpdatePasswordComponent},
      { path: 'admin/:id/update-password', component: UpdatePasswordComponent},
      { path: 'medicalstaff', component: MedicalListComponent},
      { path: 'clinic/:id/add-doctor', component: CreateDoctorComponent},
      { path: 'clinic/:id/add-nurse', component: CreateNurseComponent},
      { path: 'patients', component: PatientListComponent},
      { path: 'medical/:id/patients', component: PatientListComponent},
      { path: 'create-appointment-request', component: CreateAppointmentRequestComponent},
      { path: 'appointment-request/:id', component: CreateAppointmentComponent},
      { path: 'appointment-request-view', component: AppointmentRequestViewComponent},
      { path: 'appointment-types', component: AppointmenttypeListComponent},
      { path: 'appointment-type/:id', component: AppointmenttypeViewComponent},
      { path: 'create-appointment-type', component: CreateAppointmenttypeComponent},
      // { path: 'ers', component: ErListComponent},
      { path: 'clinic/:id/ers', component: ErListComponent},
      { path: 'er/:id', component: ErComponent},
      { path: 'create-admin', component: CreateAdminComponent},
      { path: 'clinic/:id/add-er', component: CreateErComponent},
      { path: 'create-clinic', component: CreateClinicComponent},
      { path: 'price-list', component: PriceListComponent},
      { path: 'doctor/add-appointment', component: CreateAppointmentByDoctorComponent},
      { path: 'vacation-request', component: CreateVacationComponent},
      { path: 'create-predef-appointment', component: CreatePredefAppointmentComponent},
      { path: 'predef-appointments', component: PredefAppointmentsListComponent},
      { path: 'old-appointments', component: AppointmentsByPatientsListComponent},
      { path: 'my-doctors', component: MyDoctorsListComponent},
      { path: 'my-clinics', component: MyClinicsListComponent}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
