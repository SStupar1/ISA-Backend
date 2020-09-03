import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePredefAppointmentComponent } from './create-predef-appointment.component';

describe('CreatePredefAppointmentComponent', () => {
  let component: CreatePredefAppointmentComponent;
  let fixture: ComponentFixture<CreatePredefAppointmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatePredefAppointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePredefAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
