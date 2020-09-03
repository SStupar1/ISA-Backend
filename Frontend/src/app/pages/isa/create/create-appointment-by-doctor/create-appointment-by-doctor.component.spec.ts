import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAppointmentByDoctorComponent } from './create-appointment-by-doctor.component';

describe('CreateAppointmentByDoctorComponent', () => {
  let component: CreateAppointmentByDoctorComponent;
  let fixture: ComponentFixture<CreateAppointmentByDoctorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAppointmentByDoctorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAppointmentByDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
