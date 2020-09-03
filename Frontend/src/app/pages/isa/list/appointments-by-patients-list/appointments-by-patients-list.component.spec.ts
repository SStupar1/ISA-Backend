import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentsByPatientsListComponent } from './appointments-by-patients-list.component';

describe('AppointmentsByPatientsListComponent', () => {
  let component: AppointmentsByPatientsListComponent;
  let fixture: ComponentFixture<AppointmentsByPatientsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentsByPatientsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentsByPatientsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
