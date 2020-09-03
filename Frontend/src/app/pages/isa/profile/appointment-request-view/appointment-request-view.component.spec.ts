import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentRequestViewComponent } from './appointment-request-view.component';

describe('AppointmentRequestViewComponent', () => {
  let component: AppointmentRequestViewComponent;
  let fixture: ComponentFixture<AppointmentRequestViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentRequestViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentRequestViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
