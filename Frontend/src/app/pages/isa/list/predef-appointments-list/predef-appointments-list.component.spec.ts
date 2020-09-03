import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PredefAppointmentsListComponent } from './predef-appointments-list.component';

describe('PredefAppointmentsListComponent', () => {
  let component: PredefAppointmentsListComponent;
  let fixture: ComponentFixture<PredefAppointmentsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PredefAppointmentsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PredefAppointmentsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
