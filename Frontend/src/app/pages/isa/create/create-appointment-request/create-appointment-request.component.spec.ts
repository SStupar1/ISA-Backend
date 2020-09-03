import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAppointmentRequestComponent } from './create-appointment-request.component';

describe('CreateAppointmentRequestComponent', () => {
  let component: CreateAppointmentRequestComponent;
  let fixture: ComponentFixture<CreateAppointmentRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAppointmentRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAppointmentRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
