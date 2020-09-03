import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentRequestListComponent } from './appointment-request-list.component';

describe('AppointmentRequestListComponent', () => {
  let component: AppointmentRequestListComponent;
  let fixture: ComponentFixture<AppointmentRequestListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentRequestListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentRequestListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
