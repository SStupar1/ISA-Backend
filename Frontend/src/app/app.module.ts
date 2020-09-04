import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IconsProviderModule } from './icons-provider.module';
import { NgZorroAntdModule, NZ_I18N, en_US } from 'ng-zorro-antd';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { AuthComponent } from './pages/auth/auth.component';
import { IsaComponent } from './pages/isa/isa.component';
import { RegistrationComponent } from './pages/auth/registration/registration.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { MedicalComponent } from './pages/isa/profile/medical/medical.component';
import { AdminComponent } from './pages/isa/profile/admin/admin.component';
import { PatientComponent } from './pages/isa/profile/patient/patient.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { PendingRecipesComponent } from './pending-recipes/pending-recipes.component';
import { CreateAppointmentByDoctorComponent } from './pages/isa/create/create-appointment-by-doctor/create-appointment-by-doctor.component';
import { AppInterceptor } from './app-intercetor';
import { NoopInterceptor } from '@angular/common/http/src/interceptor';
import { CreateVacationComponent } from './pages/isa/create/create-vacation/create-vacation.component';
import { CreatePredefAppointmentComponent } from './pages/isa/create/create-predef-appointment/create-predef-appointment.component';
import { PredefAppointmentsListComponent } from './pages/isa/list/predef-appointments-list/predef-appointments-list.component';
import { AppointmentsByPatientsListComponent } from './pages/isa/list/appointments-by-patients-list/appointments-by-patients-list.component';
import { MyDoctorsListComponent } from './pages/isa/list/my-doctors-list/my-doctors-list.component';
import { MyClinicsListComponent } from './pages/isa/list/my-clinics-list/my-clinics-list.component';
import { CalendarComponent } from './pages/isa/list/calendar/calendar.component';
import { MapComponent } from './pages/isa/map/map.component';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    IsaComponent,
    RegistrationComponent,
    LoginComponent,
    PatientComponent,
    MedicalComponent,
    AdminComponent,
    FirstLoginComponent,
    MedicalListComponent,
    MyProfileComponent,
    ClinicListComponent,
    ClinicComponent,
    PatientListComponent,
    AdminListComponent,
    ChooseClinicComponent,
    RequestListComponent,
    AppointmentRequestListComponent,
    MyClinicProfileComponent,
    UpdatePasswordComponent,
    CreateAppointmentRequestComponent,
    CreateDoctorComponent,
    CreateNurseComponent,
    CreateAppointmentComponent,
    AppointmentRequestViewComponent,
    AppointmenttypeListComponent,
    AppointmenttypeViewComponent,
    CreateAppointmenttypeComponent,
    ErListComponent,
    ErComponent,
    CreateErComponent,
    CreateAdminComponent,
    CreateClinicComponent,
    PriceListComponent,
    AddMedicineAndDiagnosisComponent,
    MedicalRecordComponent,
    MedicalExaminationComponent,
    PendingRecipesComponent,
    CreateAppointmentByDoctorComponent,
    CreateVacationComponent,
    CreatePredefAppointmentComponent,
    PredefAppointmentsListComponent,
    AppointmentsByPatientsListComponent,
    MyDoctorsListComponent,
    MyClinicsListComponent,
    CalendarComponent,
    MapComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    IconsProviderModule,
    NgZorroAntdModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory })
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US },{ provide: HTTP_INTERCEPTORS, useClass: AppInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
